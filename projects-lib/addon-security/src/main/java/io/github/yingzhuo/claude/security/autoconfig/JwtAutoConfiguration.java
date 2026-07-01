package io.github.yingzhuo.claude.security.autoconfig;

import io.github.yingzhuo.claude.security.autoconfig.properties.JwtAlgProperties;
import io.github.yingzhuo.claude.security.jwt.*;
import io.github.yingzhuo.claude.security.pwd.PasswordEncoderFactories;
import io.github.yingzhuo.claude.security.token.TokenResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(JwtAlgProperties.class)
@AutoConfiguration
public class JwtAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDefaults();
	}

	@Bean
	@ConditionalOnMissingBean
	public AlgorithmProvider algorithmProvider(JwtAlgProperties props) {
		return AlgorithmProvider.RSA256.builder()
			.pemLocation(props.getPemLocation())
			.keyPassword(props.getKeyPwd())
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
