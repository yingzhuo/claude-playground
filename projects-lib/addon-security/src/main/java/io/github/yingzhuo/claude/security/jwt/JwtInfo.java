package io.github.yingzhuo.claude.security.jwt;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * @author 应卓
 */
public interface JwtInfo extends Authentication {

	public String getUserId();

	public String getUsername();

	public List<String> getRoles();

	@Override
	default Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles().stream()
			.map(SimpleGrantedAuthority::new)
			.toList();
	}

	@Override
	@Nullable
	public default Object getCredentials() {
		return null;
	}

	@Override
	@Nullable
	public default Object getDetails() {
		return null;
	}

	@Override
	@Nullable
	public default Object getPrincipal() {
		return null;
	}

	@Override
	public default boolean isAuthenticated() {
		return true;
	}

	@Override
	public default void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// noop
	}

	@Override
	public default String getName() {
		return getUsername();
	}
}
