package io.github.yingzhuo.claude.security.ex;

import io.github.yingzhuo.claude.model.webmvc.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class SecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

	private static final String UNAUTHORIZED_MESSAGE = "未认证";
	private static final String FORBIDDEN_MESSAGE = "权限不足";

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
	                     AuthenticationException authException) {
		log.warn("认证失败: {}", authException.getMessage());
		writeJsonResponse(response, HttpStatus.UNAUTHORIZED, R.error401(UNAUTHORIZED_MESSAGE));
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
	                   AccessDeniedException accessDeniedException) {
		log.warn("授权失败: {}", accessDeniedException.getMessage());
		writeJsonResponse(response, HttpStatus.FORBIDDEN, R.error403(FORBIDDEN_MESSAGE));
	}

	private void writeJsonResponse(HttpServletResponse response, HttpStatus status, R<Void> body) {
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		try {
			objectMapper.writeValue(response.getOutputStream(), body);
			response.flushBuffer();
		} catch (IOException e) {
			log.error("写入认证/授权错误响应失败", e);
		}
	}

}
