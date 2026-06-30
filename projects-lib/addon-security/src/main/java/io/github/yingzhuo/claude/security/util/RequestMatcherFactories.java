/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yingzhuo.claude.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * (内部工具)
 *
 * @author 应卓
 */
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
