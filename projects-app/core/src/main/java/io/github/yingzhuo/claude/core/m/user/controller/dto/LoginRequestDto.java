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
	@Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 32)
	private String username;

	@NotBlank
	@Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 64)
	private String password;

}
