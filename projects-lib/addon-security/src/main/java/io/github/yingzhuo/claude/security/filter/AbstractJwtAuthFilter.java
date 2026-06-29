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

import lombok.Setter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author 应卓
 */
public abstract class AbstractJwtAuthFilter extends OncePerRequestFilter {

	@Setter
	private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

	protected final void clearSecurityContext() {
		securityContextHolderStrategy.clearContext();
	}

	protected final SecurityContext getSecurityContext() {
		return securityContextHolderStrategy.getContext();
	}

	protected final void setAuthentication(Authentication authentication) {
		Assert.notNull(authentication, "Authentication must not be null");
		authentication.setAuthenticated(true);
		getSecurityContext().setAuthentication(authentication);
	}

	protected final boolean authenticationIsRequired() {
		var existingAuth = securityContextHolderStrategy.getContext().getAuthentication();
		if (existingAuth == null || !existingAuth.isAuthenticated()) {
			return true;
		}
		return (existingAuth instanceof AnonymousAuthenticationToken);
	}
}
