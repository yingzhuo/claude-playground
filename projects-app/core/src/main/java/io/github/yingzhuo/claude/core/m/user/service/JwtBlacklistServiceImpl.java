package io.github.yingzhuo.claude.core.m.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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
		var ttlSeconds = Duration.between(now, expiredAt).toSeconds();
		redisTemplate.opsForValue().set(key, expiredAt.toString(), ttlSeconds, TimeUnit.SECONDS);
		log.debug("JWT 已加入 Redis 黑名单: jti={}, ttl={}s", jti, ttlSeconds);
	}

	@Override
	public boolean isBlacklisted(String jti) {
		var key = buildKey(jti);
		var exists = Boolean.TRUE.equals(redisTemplate.hasKey(key));
		if (exists) {
			log.trace("Redis 黑名单命中: jti={}", jti);
		}
		return exists;
	}
}
