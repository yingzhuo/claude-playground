package io.github.yingzhuo.claude.security.autoconfig;

import io.github.yingzhuo.claude.security.jwt.*;
import io.github.yingzhuo.claude.security.pwd.PasswordEncoderFactories;
import io.github.yingzhuo.claude.security.token.TokenResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@AutoConfiguration
public class JwtAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDefaults();
	}

	@Bean
	@ConditionalOnMissingBean
	public AlgorithmProvider algorithmProvider() {
		return AlgorithmProvider.RSA256AlgorithmProvider.builder()
			.pemLocation("classpath:/jwt-key/RSA256.pem")
			.keyPassword(null)
			.build();
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtCreator jwtCreator(AlgorithmProvider provider) {
		return new SimpleJwtCreator(provider);
	}

	@Bean
	@ConditionalOnMissingBean
	public JwtVerifier jwtVerifier(AlgorithmProvider provider) {
		return new SimpleJwtVerifier(provider);
	}

	@Bean
	@ConditionalOnMissingBean
	public TokenResolver tokenResolver() {
		return TokenResolver.getDefault();
	}

}
