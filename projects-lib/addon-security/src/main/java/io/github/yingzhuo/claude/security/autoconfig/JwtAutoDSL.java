package io.github.yingzhuo.claude.security.autoconfig;

import io.github.yingzhuo.claude.security.filter.JwtAuthFilter;
import io.github.yingzhuo.claude.security.filter.RequestLoggingFilter;
import io.github.yingzhuo.claude.security.jwt.JwtVerifier;
import io.github.yingzhuo.claude.security.jwt.TokenResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;

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
