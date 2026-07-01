package io.github.yingzhuo.claude.security.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * JWT 相关常量
 *
 * @author 应卓
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtConstants {

	/**
	 * JWT 签发方（issuer）
	 */
	public static final String ISSUER = "claude-playground";

}
