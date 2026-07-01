package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.JWT;
import io.github.yingzhuo.claude.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @see JwtCreator
 */
@RequiredArgsConstructor
public class SimpleJwtCreator implements JwtCreator {

	private final AlgorithmProvider algorithmProvider;

	@Override
	public String create(User user) {
		Assert.notNull(user, "User must not be null");

		var algorithm = algorithmProvider.get();

		return JWT.create()
			.withIssuer(JwtConstants.ISSUER)
			.withClaim("id", user.getId())
			.withClaim("username", user.getUsername())
			.withExpiresAt(Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))) // 有效期一天
			.sign(algorithm);
	}

}
