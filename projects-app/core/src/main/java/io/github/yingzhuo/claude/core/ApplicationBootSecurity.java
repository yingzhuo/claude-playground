package io.github.yingzhuo.claude.core;

import io.github.yingzhuo.claude.security.ex.SecurityExceptionHandler;
import io.github.yingzhuo.claude.security.util.RequestMatcherFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import tools.jackson.databind.ObjectMapper;

import static org.springframework.http.HttpMethod.GET;

@EnableMethodSecurity(securedEnabled = true)
@Configuration
public class ApplicationBootSecurity {

	@Bean
	public HttpFirewall httpFirewall() {
		var bean = new StrictHttpFirewall();
		bean.setAllowSemicolon(true);
		return bean;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return customizer -> customizer.debug(false);
	}

	@Bean
	public SecurityFilterChain securityFilterChainDefault(HttpSecurity http, ObjectMapper objectMapper) {
		var exceptionHandler = new SecurityExceptionHandler(objectMapper);

		return http
			.securityMatcher(RequestMatcherFactories.createDefault())
			.anonymous(Customizer.withDefaults())
			.sessionManagement(c ->
				c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.cors(Customizer.withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.jee(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.passwordManagement(AbstractHttpConfigurer::disable)
			.rememberMe(AbstractHttpConfigurer::disable)
			.requestCache(RequestCacheConfigurer::disable)
			.headers(Customizer.withDefaults())
			.cors(Customizer.withDefaults())
			.exceptionHandling(c ->
				c.authenticationEntryPoint(exceptionHandler)
					.accessDeniedHandler(exceptionHandler)
			)
			.authorizeHttpRequests(c ->
				c.requestMatchers("/error").permitAll()
					.requestMatchers(GET, "/actuator", "/actuator/info", "/actuator/health",
						"/actuator/beans", "/actuator/env", "/actuator/prometheus").permitAll()
					.requestMatchers("/actuator/shutdown", "/actuator/restart").denyAll()
					.requestMatchers("/user/login").permitAll()
					.requestMatchers("/user/register").permitAll()
					.requestMatchers("/user/logout").permitAll()
					.requestMatchers("/user/**").hasRole("USER")
					.requestMatchers("/admin/login").permitAll()
					.requestMatchers("/admin/user/delete").hasRole("SUPER")
					.requestMatchers("/admin/logout").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().permitAll()
			)
			.build();
	}

}
