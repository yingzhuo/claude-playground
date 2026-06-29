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

package io.github.yingzhuo.claude.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Base64Utils {

	public static String encodeToString(String text) {
		Assert.hasText(text, "text must not be blank");
		return Base64.getUrlEncoder()
			.withoutPadding()
			.encodeToString(text.getBytes(UTF_8));
	}

	public static String decodeToString(String text) {
		Assert.hasText(text, "text must not be blank");
		return new String(Base64.getUrlDecoder().decode(text.getBytes(UTF_8)), UTF_8);
	}

	public static byte[] decodeBase64(String text) {
		Assert.hasText(text, "text must not be blank");
		return Base64.getUrlDecoder().decode(text.getBytes(UTF_8));
	}

}
