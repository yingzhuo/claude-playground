package io.github.yingzhuo.claude.misc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SwaggerConstants {

	public static final String AUTH_HEADER = "AuthHeader";

	/**
	 * JWT 令牌请求头名称
	 */
	public static final String X_AUTH_TOKEN = "X-Auth-Token";

}
