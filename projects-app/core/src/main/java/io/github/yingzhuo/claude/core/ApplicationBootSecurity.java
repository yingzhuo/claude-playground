package io.github.yingzhuo.claude.core;

import io.github.yingzhuo.claude.security.ex.SecurityExceptionHandler;
import io.github.yingzhuo.claude.security.util.RequestMatcherFactories;
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
import tools.jackson.databind.ObjectMapper;

import static org.springframework.http.HttpMethod.GET;

@EnableMethodSecurity(
	prePostEnabled = true,
	securedEnabled = true
)
@Configuration
public class ApplicationBootSecurity {

	/**
	 * 配置 {@link HttpFirewall}，允许 URL 中包含分号。
	 *
	 * @return HttpFirewall 实例
	 */
	@Bean
	public HttpFirewall httpFirewall() {
		var bean = new StrictHttpFirewall();
		bean.setAllowSemicolon(true);
		return bean;
	}

	/**
	 * 配置 {@link WebSecurityCustomizer}，关闭 当dev模式下开启 Debug 模式。
	 *
	 * @param environment Spring 环境
	 * @return WebSecurityCustomizer 实例
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer(Environment environment) {
		var dev = environment.matchesProfiles("dev");
		return web -> web.debug(dev);
	}

	/**
	 * 配置默认的 {@link SecurityFilterChain}。
	 * <p>
	 * 弃用所有默认过滤器，仅保留匿名认证和 CORS 配置，开启无状态会话管理。
	 * 自定义异常处理由 {@link SecurityExceptionHandler} 处理。
	 * </p>
	 *
	 * @param http         HttpSecurity
	 * @param objectMapper ObjectMapper
	 * @return SecurityFilterChain 实例
	 */
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
					.requestMatchers(GET, "/favicon.ico").permitAll()
					.requestMatchers(GET, "/actuator", "/actuator/info", "/actuator/health", "/actuator/beans", "/actuator/env").permitAll()
					.requestMatchers("/actuator/shutdown").denyAll()
					.anyRequest().permitAll()
			)
			.build();
	}

}
