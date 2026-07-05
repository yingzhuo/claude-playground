package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.claude.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class SimpleJwtCreator implements JwtCreator {

	private final Algorithm algorithm;
	private final long expirationInHours;

	@Override
	public String create(User user) {
		Assert.notNull(user, "User must not be null");

		return JWT.create()
			.withIssuer(JwtConstants.ISSUER)
				.withJWTId(UUID.randomUUID().toString())
			.withClaim("id", user.getId())
			.withClaim("username", user.getUsername())
			.withClaim("roles", user.getRoles() != null ? user.getRoles() : List.of())
			.withExpiresAt(Date.from(LocalDateTime.now().plusHours(expirationInHours).toInstant(ZoneOffset.UTC)))
			.sign(this.algorithm);
	}

}
