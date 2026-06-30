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

import io.github.yingzhuo.claude.jsr380.PasswordStrength;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "修改密码请求")
public class ChangePasswordRequestDto implements Serializable {

    @NotBlank
    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 128)
    private String oldPassword;

    @NotBlank
    @Size(min = 8, max = 32)
    @PasswordStrength
    @Schema(description = "新密码 (8-32位，必须包含字母、数字和特殊字符)", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
    private String newPassword;
}
