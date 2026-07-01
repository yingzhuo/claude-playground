package io.github.yingzhuo.claude.security.jwt;

import io.github.yingzhuo.claude.model.user.entity.User;

import java.util.function.Function;

@FunctionalInterface
public interface JwtCreator extends Function<User, String> {

	public String create(User user);

	@Override
	public default String apply(User user) {
		return create(user);
	}

}
