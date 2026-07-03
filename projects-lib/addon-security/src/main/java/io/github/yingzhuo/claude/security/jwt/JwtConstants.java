package io.github.yingzhuo.claude.security.jwt;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtConstants {

	/**
	 * JWT 签发方（issuer）
	 */
	public static final String ISSUER = "claude-playground";

}
