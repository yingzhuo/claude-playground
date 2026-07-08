package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.github.yingzhuo.claude.model.admin.Admin;
import io.github.yingzhuo.claude.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.LinkedHashSet;

@RequiredArgsConstructor
public class SimpleJwtCreator implements JwtCreator {

	private final Algorithm algorithm;
	private final JwtIdGenerator idGenerator;
	private final long expirationInHours;

	@Override
	public String create(User user) {
		Assert.notNull(user, "User must not be null");

		var roles = new LinkedHashSet<String>();
		if (user.getRoles() != null) {
			roles.addAll(user.getRoles());
		}
		roles.add("ROLE_USER");

		return JWT.create()
			.withJWTId(idGenerator.get())
			.withClaim("id", user.getId())
			.withClaim("username", user.getUsername())
			.withClaim("roles", roles.stream().toList())
			.withClaim("loginKind", "USER")
			.withExpiresAt(Date.from(LocalDateTime.now().plusHours(expirationInHours).toInstant(ZoneOffset.UTC)))
			.sign(algorithm);
	}

	@Override
	public String create(Admin admin) {
		Assert.notNull(admin, "Admin must not be null");

		var roles = new LinkedHashSet<String>();
		roles.add("ROLE_" + admin.getRole().name());
		roles.add("ROLE_ADMIN");

		return JWT.create()
			.withJWTId(idGenerator.get())
			.withClaim("id", admin.getId())
			.withClaim("username", admin.getUsername())
			.withClaim("roles", roles.stream().toList())
			.withClaim("loginKind", "ADMIN")
			.withExpiresAt(Date.from(LocalDateTime.now().plusHours(expirationInHours).toInstant(ZoneOffset.UTC)))
			.sign(algorithm);
	}

}
