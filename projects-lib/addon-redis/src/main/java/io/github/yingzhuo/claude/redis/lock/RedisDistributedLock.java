package io.github.yingzhuo.claude.redis.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于 Redis 的分布式锁（非重入）
 */
@Slf4j
@RequiredArgsConstructor
public class RedisDistributedLock implements Lock {

    private static final String LOCK_PREFIX = "distributed-lock:";

    private static final String LOCK_SCRIPT =
            "return redis.call('set', KEYS[1], ARGV[1], 'NX', 'PX', ARGV[2])";

    private static final String UNLOCK_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                    "return redis.call('del', KEYS[1]) " +
                    "else " +
                    "return 0 " +
                    "end";

    private static final RedisScript<String> LOCK_SCRIPT_OBJ = new DefaultRedisScript<>(LOCK_SCRIPT, String.class);
    private static final RedisScript<Long> UNLOCK_SCRIPT_OBJ = new DefaultRedisScript<>(UNLOCK_SCRIPT, Long.class);

    private final StringRedisTemplate redisTemplate;
    private final String name;
    private final Duration expireAfter;

    private final ThreadLocal<String> valueHolder = new ThreadLocal<>();

    private String redisKey() {
        return LOCK_PREFIX + name;
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        var value = UUID.randomUUID().toString();
        var deadline = System.currentTimeMillis() + unit.toMillis(timeout);
        var expireMs = this.expireAfter.toMillis();

        do {
            try {
                var result = redisTemplate.execute(LOCK_SCRIPT_OBJ,
                        Collections.singletonList(redisKey()), value, String.valueOf(expireMs));

                if ("OK".equals(result)) {
                    valueHolder.set(value);
                    log.trace("分布式锁已获取: key={}", redisKey());
                    return true;
                }
            } catch (DataAccessException e) {
                log.warn("Redis 操作失败，将重试: key={}", redisKey(), e);
            }

            if (System.currentTimeMillis() >= deadline) {
                return false;
            }

            var remaining = deadline - System.currentTimeMillis();
            Thread.sleep(Math.max(0, Math.min(50, remaining)));

        } while (true);
    }

    @Override
    public boolean tryLock() {
        var value = UUID.randomUUID().toString();
        var expireMs = this.expireAfter.toMillis();

        try {
            var result = redisTemplate.execute(LOCK_SCRIPT_OBJ,
                    Collections.singletonList(redisKey()), value, String.valueOf(expireMs));

            if ("OK".equals(result)) {
                valueHolder.set(value);
                return true;
            }
        } catch (DataAccessException e) {
            log.warn("Redis 操作失败: key={}", redisKey(), e);
        }
        return false;
    }

    @Override
    public void unlock() {
        var value = valueHolder.get();
        if (value == null) {
            throw new IllegalMonitorStateException("当前线程未持有锁: key=" + redisKey());
        }
        try {
            var result = redisTemplate.execute(UNLOCK_SCRIPT_OBJ,
                    Collections.singletonList(redisKey()), value);

            if (Long.valueOf(1).equals(result)) {
                log.trace("分布式锁已释放: key={}", redisKey());
            } else {
                log.warn("分布式锁释放失败（可能已过期）: key={}", redisKey());
            }
        } catch (DataAccessException e) {
            log.error("Redis 操作失败，锁可能未释放（等待自动过期）: key={}", redisKey(), e);
        } finally {
            valueHolder.remove();
        }
    }

    // --- 不支持的 Lock 方法 ---

    @Override
    public void lock() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lockInterruptibly() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }
}
