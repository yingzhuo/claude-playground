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
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, JsonMapper mapper) {
		var template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);

		// key 序列化
		template.setKeySerializer(StringRedisSerializer.UTF_8);
		template.setHashKeySerializer(StringRedisSerializer.UTF_8);

		// value 序列化
		var valueSerializer = new GenericJacksonJsonRedisSerializer(mapper);
		template.setValueSerializer(valueSerializer);
		template.setHashValueSerializer(valueSerializer);

		return template;
	}

}
