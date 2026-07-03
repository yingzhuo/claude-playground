package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.JWT;
import io.github.yingzhuo.claude.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@RequiredArgsConstructor
public class SimpleJwtCreator implements JwtCreator {

	private final AlgorithmProvider algorithmProvider;
	private final long expirationInHours;

	@Override
	public String create(User user) {
		Assert.notNull(user, "User must not be null");

		return JWT.create()
			.withIssuer(JwtConstants.ISSUER)
			.withClaim("id", user.getId())
			.withClaim("username", user.getUsername())
			.withExpiresAt(Date.from(LocalDateTime.now().plusHours(expirationInHours).toInstant(ZoneOffset.UTC)))
			.sign(algorithmProvider.get());
	}

}
