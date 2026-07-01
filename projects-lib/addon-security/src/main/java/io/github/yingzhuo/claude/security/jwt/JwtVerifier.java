package io.github.yingzhuo.claude.security.jwt;

@FunctionalInterface
public interface JwtVerifier {

	public JwtInfo verify(String token) throws BadTokenException;

	public static class BadTokenException extends Exception {
	}

}
