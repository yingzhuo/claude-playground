package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.yingzhuo.claude.security.Auth;

import java.util.List;

public class SimpleJwtVerifier implements JwtVerifier {

	private final JWTVerifier innerVerifier;

	public SimpleJwtVerifier(AlgorithmProvider algorithmProvider) {
		this.innerVerifier = JWT.require(algorithmProvider.get())
			.withIssuer(JwtConstants.ISSUER)
			.build();
	}

	@Override
	public Auth verify(String token) throws BadTokenException {
		try {
			final var decoded = innerVerifier.verify(token);

			return Auth.builder()
				.authenticated(true)
				.userId(decoded.getClaim("id").asString())
				.username(decoded.getClaim("username").asString())
				.authorities(List.of()) // TODO
				.build();

		} catch (JWTVerificationException e) {
			throw new BadTokenException();
		}
	}

}
