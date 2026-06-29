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

package io.github.yingzhuo.claude.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import static org.springframework.http.HttpMethod.GET;

@EnableMethodSecurity(
	prePostEnabled = true,
	securedEnabled = true
)
@Configuration
public class ApplicationBootSecurity {

	@Bean
	public HttpFirewall httpFirewall() {
		var bean = new StrictHttpFirewall();
		bean.setAllowSemicolon(true);
		return bean;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(Environment environment) {
		return web -> web.debug(false);
	}

	@Bean
	public SecurityFilterChain securityFilterChainDefault(HttpSecurity http) {
		var applicationContext = http.getSharedObject(ApplicationContext.class);


		// 错误处理器
//		var exceptionHandler = new SecurityExceptionHandler();

		return http
			.securityMatcher("/**")
			.anonymous(Customizer.withDefaults())
			.sessionManagement(c ->
				c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable) // 禁用
			.httpBasic(AbstractHttpConfigurer::disable) // 禁用
			.jee(AbstractHttpConfigurer::disable) // 禁用
			.formLogin(AbstractHttpConfigurer::disable) // 禁用
			.logout(AbstractHttpConfigurer::disable) // 禁用
			.passwordManagement(AbstractHttpConfigurer::disable) // 禁用
			.rememberMe(AbstractHttpConfigurer::disable) // 禁用
			.requestCache(RequestCacheConfigurer::disable) // 禁用
			.headers(Customizer.withDefaults())
			.cors(Customizer.withDefaults())
//			.exceptionHandling(c ->
//				c.authenticationEntryPoint(exceptionHandler)
//					.accessDeniedHandler(exceptionHandler)
//			)
			.authorizeHttpRequests(c ->
				c.requestMatchers("/error").permitAll()
					.requestMatchers(GET, "/favicon.ico").permitAll()
					.requestMatchers(GET, "/actuator", "/actuator/info", "/actuator/health", "/actuator/beans", "/actuator/env").permitAll()
					.requestMatchers("/actuator/shutdown").denyAll()
					.anyRequest().permitAll()    // 用元注释控制
			)
			.build();
	}
}
