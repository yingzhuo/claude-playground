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

	/**
	 * 配置默认的 {@link PasswordEncoder}。
	 * <p>
	 * 使用 {@link PasswordEncoderFactories#createDefaults()} 创建委托编码器，
	 * 默认使用 bcrypt 算法（由属性 {@code spring.security.password.encoding-id} 控制）。
	 * </p>
	 *
	 * @return PasswordEncoder 实例
	 */
	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDefaults();
	}

	/**
	 * 配置默认的 {@link AlgorithmProvider}。
	 * <p>
	 * 使用 RSA256 算法，密钥从 classpath:{@code /jwt-key/RSA256.pem} 加载，
	 * 密钥口令由属性 {@code jwt.key.password} 控制（默认无口令）。
	 * </p>
	 *
	 * @return AlgorithmProvider 实例
	 */
	@Bean
	@ConditionalOnMissingBean
	public AlgorithmProvider algorithmProvider() {
		return RSA256AlgorithmProvider.builder()
			.pemLocation("classpath:/jwt-key/RSA256.pem")
			.keyPassword(null)
			.build();
	}

	/**
	 * 配置默认的 {@link JwtCreator}。
	 * <p>
	 * 使用给定的 {@link AlgorithmProvider} 创建 JWT 令牌。
	 * </p>
	 *
	 * @param provider AlgorithmProvider
	 * @return JwtCreator 实例
	 */
	@Bean
	@ConditionalOnMissingBean
	public JwtCreator jwtCreator(AlgorithmProvider provider) {
		return new SimpleJwtCreator(provider);
	}

	/**
	 * 配置默认的 {@link JwtVerifier}。
	 * <p>
	 * 使用给定的 {@link AlgorithmProvider} 验证 JWT 令牌。
	 * </p>
	 *
	 * @param provider AlgorithmProvider
	 * @return JwtVerifier 实例
	 */
	@Bean
	@ConditionalOnMissingBean
	public JwtVerifier jwtVerifier(AlgorithmProvider provider) {
		return new SimpleJwtVerifier(provider);
	}

	/**
	 * 配置默认的 {@link TokenResolver}。
	 * <p>
	 * 从 HTTP 请求头中解析 JWT 令牌，默认从 {@code X-Auth-Token} 或 {@code X-Token} 请求头中提取。
	 * </p>
	 *
	 * @return TokenResolver 实例
	 */
	@Bean
	@ConditionalOnMissingBean
	public TokenResolver tokenResolver() {
		return new DefaultTokenResolver();
	}

}
