package io.github.yingzhuo.claude.security.filter;

import io.github.yingzhuo.claude.security.jwt.JwtVerifier;
import io.github.yingzhuo.claude.security.token.TokenResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends AbstractJwtAuthFilter {

	private final TokenResolver tokenResolver;
	private final JwtVerifier jwtVerifier;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		if (!super.authenticationIsRequired()) {
			chain.doFilter(request, response);
			return;
		}

		try {
			var token = tokenResolver.resolve(new ServletWebRequest(request, response));
			if (!StringUtils.hasText(token)) {
				chain.doFilter(request, response);
				return;
			}

			log.trace("解析出令牌: {}", token);

			var auth = jwtVerifier.verify(token);
			auth.setToken(token);
			auth.setDetails(new WebAuthenticationDetails(request));
			super.setAuthentication(auth);

			if (log.isTraceEnabled()) {
				log.trace("认证成功 id: {}", auth.getUserId());
				log.trace("认证成功 username: {}", auth.getUsername());
				log.trace("认证成功 authorities: {}",
					auth.getAuthorities()
						.stream()
						.map(Object::toString)
						.collect(Collectors.joining(",")));
			}

		} catch (JwtVerifier.BadTokenException | AuthenticationException e) {
			log.debug(e.getMessage(), e);
			super.clearSecurityContext();
			chain.doFilter(request, response);
		} catch (ServletException | IOException e) {
			log.debug(e.getMessage(), e);
			throw e;
		}
	}

}
