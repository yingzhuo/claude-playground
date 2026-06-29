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

package io.github.yingzhuo.claude.core.m.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登录请求
 *
 * @author 应卓
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录请求")
public class LoginRequestDto implements Serializable {

	@NotBlank
	@Schema(description = "用户名", required = true, requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 32)
	private String username;

	@NotBlank
	@Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 64)
	private String password;

}
