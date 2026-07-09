package io.github.yingzhuo.claude.security.autoconfig;

import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.claude.security.jwt.*;
import io.github.yingzhuo.claude.utility.UUIDUtils;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@AutoConfiguration
public class JwtBeanAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		var encoder = (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
		encoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());
		return encoder;
	}

	@Bean
	@ConditionalOnMissingBean(Algorithm.class)
	public AlgorithmFactoryBean algorithmFactoryBean() {
		return AlgorithmFactoryBean.builder()
			.storeLocation("classpath:META-INF/claude-playground.pfx")
			.storepass("123456")
			.alias("ecdsa384")
			.keypass("123456")
			.build();
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtIdGenerator jwtIdGenerator() {
		return UUIDUtils::randomUUIDv7;
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtCreator jwtCreator(Algorithm alg, JwtIdGenerator idGen) {
		return new SimpleJwtCreator(alg, idGen, 4);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtVerifier jwtVerifier(Algorithm alg) {
		return new SimpleJwtVerifier(alg);
	}

	@Bean
	@ConditionalOnMissingBean
	public TokenResolver tokenResolver() {
		return new HeaderTokenResolver("X-Auth-Token");
	}

}
