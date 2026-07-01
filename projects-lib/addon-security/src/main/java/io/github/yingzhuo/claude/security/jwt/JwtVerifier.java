package io.github.yingzhuo.claude.security.jwt;

import io.github.yingzhuo.claude.security.auth.Auth;

@FunctionalInterface
public interface JwtVerifier {

	public Auth verify(String token) throws BadTokenException;

	public static class BadTokenException extends Exception {
	}

}
