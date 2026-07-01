package io.github.yingzhuo.claude.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求日志过滤器。
 * <p>
 * 记录每个入站请求的 method, path, query params, timestamp, headers。
 * 放在 Spring Security 过滤器链的靠前位置，确保在认证/授权之前记录。
 * </p>
 *
 * @author 应卓
 */
@Slf4j
public class RequestLoggingFilter implements Filter {

	private static Map<String, String> extractParams(HttpServletRequest request) {
		var map = new LinkedHashMap<String, String>();
		var paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			var name = paramNames.nextElement();
			map.put(name, String.join(",", request.getParameterValues(name)));
		}
		return Collections.unmodifiableMap(map);
	}

	private static Map<String, String> extractHeaders(HttpServletRequest request) {
		var map = new LinkedHashMap<String, String>();
		var headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			var name = headerNames.nextElement();
			map.put(name, String.join(",", Collections.list(request.getHeaders(name))));
		}
		return Collections.unmodifiableMap(map);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		var httpRequest = (HttpServletRequest) request;
		var timestamp = Instant.now();

		var method = httpRequest.getMethod();
		var path = httpRequest.getRequestURI();
		var queryString = httpRequest.getQueryString();
		var params = extractParams(httpRequest);
		var headers = extractHeaders(httpRequest);

		log.info("request method={} path={}{} params={} headers={}",
			method,
			path,
			queryString != null ? "?" + queryString : "",
			params,
			headers);

		chain.doFilter(request, response);
	}

}
