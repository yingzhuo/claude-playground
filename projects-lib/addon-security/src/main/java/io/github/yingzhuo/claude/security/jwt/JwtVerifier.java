package io.github.yingzhuo.claude.security.jwt;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface JwtVerifier {

	public JwtInfo verify(String token) throws BadTokenException;

	public static class BadTokenException extends Exception {
	}

}
