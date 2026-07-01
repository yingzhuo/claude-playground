package io.github.yingzhuo.claude.security.filter;

import lombok.Setter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

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
