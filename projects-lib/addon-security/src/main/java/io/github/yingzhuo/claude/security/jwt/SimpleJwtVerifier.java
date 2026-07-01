package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.List;

public class SimpleJwtVerifier implements JwtVerifier {

	private final JWTVerifier innerVerifier;

	public SimpleJwtVerifier(AlgorithmProvider algorithmProvider) {
		this.innerVerifier = JWT.require(algorithmProvider.get())
			.withIssuer(JwtConstants.ISSUER)
			.build();
	}

	@Override
	public JwtInfo verify(String token) throws BadTokenException {
		try {
			final var decoded = innerVerifier.verify(token);

			return new JwtInfo() {
				@Override
				public String getUserId() {
					return decoded.getClaim("id").asString();
				}

				@Override
				public String getUsername() {
					return decoded.getClaim("username").asString();
				}

				@Override
				public List<String> getRoles() {
					return List.of(); // TODO
				}
			};

		} catch (JWTVerificationException e) {
			throw new BadTokenException();
		}
	}

}
