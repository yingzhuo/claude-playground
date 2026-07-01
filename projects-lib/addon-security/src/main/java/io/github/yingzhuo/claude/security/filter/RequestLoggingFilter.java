package io.github.yingzhuo.claude.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		var method = request.getMethod();
		var path = request.getRequestURI();
		var queryString = request.getQueryString();
		var params = extractParams(request);
		var headers = extractHeaders(request);

		log.info("request method={} path={}{} params={} headers={}",
			method,
			path,
			queryString != null ? "?" + queryString : "",
			params,
			headers);

		chain.doFilter(request, response);
	}

	private Map<String, String> extractParams(HttpServletRequest request) {
		var map = new LinkedHashMap<String, String>();
		var paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			var name = paramNames.nextElement();
			map.put(name, String.join(",", request.getParameterValues(name)));
		}
		return Collections.unmodifiableMap(map);
	}

	private Map<String, String> extractHeaders(HttpServletRequest request) {
		var map = new LinkedHashMap<String, String>();
		var headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			var name = headerNames.nextElement();
			map.put(name, String.join(",", Collections.list(request.getHeaders(name))));
		}
		return Collections.unmodifiableMap(map);
	}
}
