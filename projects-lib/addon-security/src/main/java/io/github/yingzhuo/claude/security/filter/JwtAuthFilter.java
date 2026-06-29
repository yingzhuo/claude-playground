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

package io.github.yingzhuo.claude.security.filter;

import io.github.yingzhuo.claude.security.jwt.JwtVerifier;
import io.github.yingzhuo.claude.security.token.TokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * @author 应卓
 */
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends AbstractJwtAuthFilter {

	private final TokenResolver tokenResolver;
	private final JwtVerifier jwtVerifier;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		if (!super.authenticationIsRequired()) {
			chain.doFilter(request, response);
			return;
		}

		try {
			var token = tokenResolver.resolve(new ServletWebRequest(request, response));
			if (!StringUtils.hasText(token)) {
				chain.doFilter(request, response);
				return;
			}

			log.trace("接卸出令牌: {}", token);

			var jwtInfo = jwtVerifier.verify(token);
			super.setAuthentication(jwtInfo);

			log.trace("认证 id: {}", jwtInfo.getUserId());
			log.trace("认证 username: {}", jwtInfo.getUsername());
			log.trace("认证 roles: {}", String.join(",", jwtInfo.getRoles()));

		} catch (JwtVerifier.BadTokenException | AuthenticationException e) {
			log.debug(e.getMessage(), e);
			super.clearSecurityContext();
			chain.doFilter(request, response);
		} catch (ServletException | IOException e) {
			log.debug(e.getMessage(), e);
			throw e;
		}
	}

}
