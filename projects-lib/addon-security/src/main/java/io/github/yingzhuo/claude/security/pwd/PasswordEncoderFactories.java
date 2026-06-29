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

package io.github.yingzhuo.claude.security.pwd;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;

import java.util.HashMap;

/**
 * @author 应卓
 */
@SuppressWarnings("deprecation")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordEncoderFactories {

	public static PasswordEncoder createDefaults() {
		final var encodingId = "bcrypt";
		final var encoders = new HashMap<String, PasswordEncoder>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("MD4", new Md4PasswordEncoder());
		encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
		encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));

		var encoder = new DelegatingPasswordEncoder(encodingId, encoders);
		encoder.setDefaultPasswordEncoderForMatches(NoOpPasswordEncoder.getInstance());
		return encoder;
	}

}
