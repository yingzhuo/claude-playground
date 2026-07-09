package io.github.yingzhuo.claude.security.jwt;

import io.github.yingzhuo.claude.security.Auth;

public interface JwtVerifier {

	Auth verify(String token) throws BadTokenException;

	class BadTokenException extends Exception {
	}

}
