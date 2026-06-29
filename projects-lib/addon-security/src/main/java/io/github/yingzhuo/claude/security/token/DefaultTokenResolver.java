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

package io.github.yingzhuo.claude.security.token;

import org.jspecify.annotations.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

/**
 * @author 应卓
 */
public class DefaultTokenResolver implements TokenResolver {

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
