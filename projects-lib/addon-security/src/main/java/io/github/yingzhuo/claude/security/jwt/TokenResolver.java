package io.github.yingzhuo.claude.security.jwt;

import org.jspecify.annotations.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

@FunctionalInterface
public interface TokenResolver {

	static TokenResolver getDefault() {
		return new DefaultTokenResolver();
	}

	@Nullable
	String resolve(WebRequest request);

	class DefaultTokenResolver implements TokenResolver {

		private static final String[] HEADERS = {
			"X-Auth-Token",
			"X-Token"
		};

		@Override
		public @Nullable String resolve(WebRequest request) {
			for (var header : HEADERS) {
				var value = request.getHeader(header);
				if (StringUtils.hasText(value)) {
					return value.trim();
				}
			}
			return null;
		}
	}
}
