package io.github.yingzhuo.claude.core.m.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    private static final String KEY_PREFIX = "jwt:blacklist:";

    private final RedisTemplate<String, Object> redisTemplate;

    private String buildKey(String jti) {
        return KEY_PREFIX + jti;
    }

    @Override
    public void add(String jti, LocalDateTime expiredAt) {
        var now = LocalDateTime.now();
        if (expiredAt.isBefore(now) || expiredAt.isEqual(now)) {
            log.debug("JWT jti={} 已过期，无需加入黑名单", jti);
            return;
        }

        var key = buildKey(jti);
        var duration = Duration.between(now, expiredAt);
        redisTemplate.opsForValue()
                .set(key, expiredAt.toString(), duration);
        log.debug("JWT 已加入 Redis 黑名单: jti={}, ttl={}", jti, duration);
    }

    @Override
    public boolean isBlacklisted(String jti) {
        var key = buildKey(jti);
        try {
            var exists = Boolean.TRUE.equals(redisTemplate.hasKey(key));
            if (exists) {
                log.trace("Redis 黑名单命中: jti={}", jti);
            }
            return exists;
        } catch (DataAccessException e) {
            log.warn("Redis 不可用，跳过黑名单校验: jti={}", jti, e);
            return false;
        }
    }
}
