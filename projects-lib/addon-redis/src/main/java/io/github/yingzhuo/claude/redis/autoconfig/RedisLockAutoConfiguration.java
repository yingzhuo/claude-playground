package io.github.yingzhuo.claude.redis.autoconfig;

import io.github.yingzhuo.claude.redis.lock.DistributedLockFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

/**
 * Redis 分布式锁自动配置
 */
@AutoConfiguration
@ConditionalOnClass(StringRedisTemplate.class)
@ConditionalOnBean(StringRedisTemplate.class)
public class RedisLockAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DistributedLockFactory distributedLockFactory(StringRedisTemplate redisTemplate) {
		return new DistributedLockFactory(redisTemplate, Duration.ofSeconds(30));
	}
}
