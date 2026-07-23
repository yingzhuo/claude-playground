package io.github.yingzhuo.claude.redis.lock;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * 分布式锁工厂
 * <p>
 * 通过工厂方法创建指定名称的分布式锁实例。
 * </p>
 */
public class DistributedLockFactory {

    private final StringRedisTemplate redisTemplate;
    private final Duration defaultExpireAfter;

    public DistributedLockFactory(StringRedisTemplate redisTemplate, Duration defaultExpireAfter) {
        this.redisTemplate = redisTemplate;
        this.defaultExpireAfter = defaultExpireAfter;
    }

    /**
     * 创建分布式锁（使用默认过期时间）
     *
     * @param name 锁名称
     * @return 分布式锁实例
     */
    public RedisDistributedLock create(String name) {
        return new RedisDistributedLock(redisTemplate, name, defaultExpireAfter);
    }

    /**
     * 创建分布式锁（指定过期时间）
     *
     * @param name        锁名称
     * @param expireAfter 锁自动过期时间
     * @return 分布式锁实例
     */
    public RedisDistributedLock create(String name, Duration expireAfter) {
        return new RedisDistributedLock(redisTemplate, name, expireAfter);
    }
}
