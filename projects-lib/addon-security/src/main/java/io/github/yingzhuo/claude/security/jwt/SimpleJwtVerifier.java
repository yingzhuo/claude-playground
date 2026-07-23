package io.github.yingzhuo.claude.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.yingzhuo.claude.security.Auth;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class SimpleJwtVerifier implements JwtVerifier {

    private final JWTVerifier innerVerifier;

    public SimpleJwtVerifier(Algorithm algorithm) {
        Assert.notNull(algorithm, "algorithm must not be null");
        this.innerVerifier = JWT.require(algorithm)
                .build();
    }

    @Override
    public Auth verify(String token) throws BadTokenException {
        try {
            var decoded = innerVerifier.verify(token);

            var roles = decoded.getClaim("roles").asList(String.class);

            return Auth.builder()
                    .authenticated(true)
                    .userId(decoded.getClaim("id").asString())
                    .username(decoded.getClaim("username").asString())
                    .tokenJti(decoded.getId())
                    .tokenExpiresAt(LocalDateTime.ofInstant(decoded.getExpiresAt().toInstant(), ZoneOffset.UTC))
                    .authorities(roles != null ? roles : List.of())
                    .loginKind(decoded.getClaim("loginKind").asString())
                    .build();

        } catch (JWTVerificationException e) {
            throw new BadTokenException();
        }
    }

}
