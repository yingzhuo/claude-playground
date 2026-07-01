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
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;

/**
 * @author 应卓
 */
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

			log.trace("接卸出令牌: {}", token);

			var jwtInfo = jwtVerifier.verify(token);
			super.setAuthentication(jwtInfo);

			log.trace("认证 id: {}", jwtInfo.getUserId());
			log.trace("认证 username: {}", jwtInfo.getUsername());
			log.trace("认证 roles: {}", String.join(",", jwtInfo.getRoles()));

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
