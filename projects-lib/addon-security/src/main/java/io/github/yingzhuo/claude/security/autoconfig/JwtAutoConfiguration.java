/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yingzhuo.claude.security.autoconfig;

import io.github.yingzhuo.claude.security.jwt.*;
import io.github.yingzhuo.claude.security.pwd.PasswordEncoderFactories;
import io.github.yingzhuo.claude.security.token.DefaultTokenResolver;
import io.github.yingzhuo.claude.security.token.TokenResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 应卓
 */
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
		var provider = new RSA256AlgorithmProvider();
		provider.setCertPemLocation("classpath:jwt-key/RSA256.pem");
		provider.setKeyPemLocation("classpath:jwt-key/RSA256.pem");
		return provider;
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
		return new DefaultTokenResolver();
	}

}
