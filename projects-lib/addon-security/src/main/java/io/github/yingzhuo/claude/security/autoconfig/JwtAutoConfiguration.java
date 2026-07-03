package io.github.yingzhuo.claude.security.autoconfig;

import io.github.yingzhuo.claude.security.autoconfig.properties.JwtAlgProperties;
import io.github.yingzhuo.claude.security.jwt.*;
import io.github.yingzhuo.claude.security.util.PasswordEncoderFactories;
import io.github.yingzhuo.claude.utility.KeyStoreResource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
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
	public AlgorithmProvider algorithmProvider(JwtAlgProperties props, ResourceLoader resourceLoader) {
		return AlgorithmProvider.RSA256.builder()
			.keyStoreResource((KeyStoreResource) resourceLoader.getResource(props.getPfxLocation()))
			.alias(props.getAlias())
			.keyPassword(props.getKeyPassword())
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
