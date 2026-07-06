package io.github.yingzhuo.claude.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class ApplicationBootRedis {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		var template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);

		// key 序列化
		template.setKeySerializer(StringRedisSerializer.UTF_8);
		template.setHashKeySerializer(StringRedisSerializer.UTF_8);

		// value 序列化（Jackson 3.x，带类型信息，支持多态反序列化）
		var mapper = JsonMapper.builder().build();
		var valueSerializer = new GenericJacksonJsonRedisSerializer(mapper);
		template.setValueSerializer(valueSerializer);
		template.setHashValueSerializer(valueSerializer);

		return template;
	}

}
