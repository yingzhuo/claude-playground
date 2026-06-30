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
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.List;

/**
 * @author 应卓
 */
public class SimpleJwtVerifier implements JwtVerifier {

	private final JWTVerifier innerVerifier;

	public SimpleJwtVerifier(AlgorithmProvider algorithmProvider) {
		this.innerVerifier = JWT.require(algorithmProvider.get())
			.withIssuer(JwtConstants.ISSUER)
			.build();
	}

	@Override
	public JwtInfo verify(String token) throws BadTokenException {
		try {
			final var decoded = innerVerifier.verify(token);

			return new JwtInfo() {
				@Override
				public String getUserId() {
					return decoded.getClaim("id").asString();
				}

				@Override
				public String getUsername() {
					return decoded.getClaim("username").asString();
				}

				@Override
				public List<String> getRoles() {
					return List.of(); // TODO
				}
			};

		} catch (JWTVerificationException e) {
			throw new BadTokenException();
		}
	}

}
