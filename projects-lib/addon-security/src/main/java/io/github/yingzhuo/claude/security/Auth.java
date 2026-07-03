package io.github.yingzhuo.claude.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Builder
@Getter
@Setter
public class Auth implements Authentication {

	private String userId;
	private String username;
	private @Nullable List<String> authorities;
	private @Nullable String token;
	private @Nullable Object details;
	private boolean authenticated;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (authorities == null) {
			return List.of();
		} else {
			return authorities.stream()
				.map(SimpleGrantedAuthority::new)
				.toList();
		}
	}

	@Override
	public @Nullable Object getCredentials() {
		return getToken();
	}

	@Override
	public @Nullable Object getDetails() {
		return details;
	}

	@Override
	public @Nullable Object getPrincipal() {
		return getUserId();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) {
		this.authenticated = isAuthenticated;
	}

	@Override
	public String getName() {
		return getUsername();
	}

}
