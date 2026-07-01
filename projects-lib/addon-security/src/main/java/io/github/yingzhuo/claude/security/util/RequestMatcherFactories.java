package io.github.yingzhuo.claude.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestMatcherFactories {

	public static RequestMatcher createDefault() {
		// 放掉Swagger相关的东西
		return new AndRequestMatcher(
			PathPatternRequestMatcher.pathPattern("/**"),
			new NegatedRequestMatcher(PathPatternRequestMatcher.pathPattern("/v3/api-docs/**")),
			new NegatedRequestMatcher(PathPatternRequestMatcher.pathPattern("/swagger-ui.html")),
			new NegatedRequestMatcher(PathPatternRequestMatcher.pathPattern("/swagger-ui/**"))
		);
	}

}
