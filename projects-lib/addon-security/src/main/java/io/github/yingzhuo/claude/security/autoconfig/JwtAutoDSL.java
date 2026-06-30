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

import io.github.yingzhuo.claude.security.filter.JwtAuthFilter;
import io.github.yingzhuo.claude.security.filter.RequestLoggingFilter;
import io.github.yingzhuo.claude.security.jwt.JwtVerifier;
import io.github.yingzhuo.claude.security.token.TokenResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;

/**
 * @author 应卓
 */
public class JwtAutoDSL extends AbstractHttpConfigurer<JwtAutoDSL, HttpSecurity> {


	@Override
	public void configure(HttpSecurity http) {
		var applicationContext = http.getSharedObject(ApplicationContext.class);

		var tokenResolver = applicationContext.getBean(TokenResolver.class);
		var jwtVerifier = applicationContext.getBean(JwtVerifier.class);
		var jwtAuthFilter = new JwtAuthFilter(tokenResolver, jwtVerifier);

		http
			.addFilterBefore(new RequestLoggingFilter(), SecurityContextHolderFilter.class) // 日志记录
			.addFilterAfter(jwtAuthFilter, BasicAuthenticationFilter.class); // 认证
	}

}
