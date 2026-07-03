package io.github.yingzhuo.claude.security.jwt;

import io.github.yingzhuo.claude.security.Auth;

@FunctionalInterface
public interface JwtVerifier {

	Auth verify(String token) throws BadTokenException;

	class BadTokenException extends Exception {
	}

}
