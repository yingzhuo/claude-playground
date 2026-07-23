package io.github.yingzhuo.claude.security.util;

import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public final class RequestMatcherFactories {

    public static RequestMatcher createDefault() {
        return new AndRequestMatcher(
                PathPatternRequestMatcher.pathPattern("/**"),
                // 放掉Swagger相关的东西
                new NegatedRequestMatcher(PathPatternRequestMatcher.pathPattern("/v3/api-docs/**")),
                new NegatedRequestMatcher(PathPatternRequestMatcher.pathPattern("/swagger-ui.html")),
                new NegatedRequestMatcher(PathPatternRequestMatcher.pathPattern("/swagger-ui/**")),
                // 放掉favicon.ico
                new NegatedRequestMatcher(PathPatternRequestMatcher.pathPattern("/**/favicon.ico"))
        );
    }

}
