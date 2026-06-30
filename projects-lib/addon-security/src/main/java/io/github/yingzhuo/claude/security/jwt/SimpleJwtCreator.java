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

package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.JWT;
import io.github.yingzhuo.claude.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author 应卓
 */
@RequiredArgsConstructor
public class SimpleJwtCreator implements JwtCreator {

	private final AlgorithmProvider algorithmProvider;

	@Override
	public String apply(User user) {
		Assert.notNull(user, "User must not be null");

		var algorithm = algorithmProvider.get();

		return JWT.create()
			.withIssuer(JwtConstants.ISSUER)
			.withClaim("id", user.getId())
			.withClaim("username", user.getUsername())
			.withExpiresAt(Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))) // 有效期一天
			.sign(algorithm);
	}

}
