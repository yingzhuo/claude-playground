/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yingzhuo.claude.security.ex;

import io.github.yingzhuo.claude.model.webmvc.R;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

/**
 * Spring Security 认证/授权异常处理器。
 * <p>
 * 同时实现 {@link AuthenticationEntryPoint} 和 {@link AccessDeniedHandler}，
 * 统一返回 {@link R} 格式的 JSON 响应。
 * </p>
 *
 * <ul>
 *   <li>未认证（无 token 或 token 无效）→ 401 + {@link R#error401(String)}</li>
 *   <li>已认证但权限不足 → 403 + {@link R#error403(String)}</li>
 * </ul>
 *
 * @author 应卓
 */
@Slf4j
public class SecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	public SecurityExceptionHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
	                     AuthenticationException authException) throws IOException, ServletException {
		log.warn("认证失败: {}", authException.getMessage());
		writeJsonResponse(response, HttpStatus.UNAUTHORIZED, R.error401("未认证"));
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
	                   AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.warn("授权失败: {}", accessDeniedException.getMessage());
		writeJsonResponse(response, HttpStatus.FORBIDDEN, R.error403("权限不足"));
	}

	private void writeJsonResponse(HttpServletResponse response, HttpStatus status, R<Void> body)
		throws IOException {
		response.setStatus(status.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		try {
			objectMapper.writeValue(response.getOutputStream(), body);
			response.flushBuffer();
		} catch (Exception e) {
			log.error("写入认证/授权错误响应失败", e);
		}
	}

}
