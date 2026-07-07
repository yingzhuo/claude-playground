package io.github.yingzhuo.claude.core.m.user.controller.dto;

import io.github.yingzhuo.claude.jsr380.PasswordMatch;
import io.github.yingzhuo.claude.jsr380.PasswordStrength;
import io.github.yingzhuo.claude.model.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@PasswordMatch
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户注册请求")
public class RegisterRequestDTO {

	@NotBlank(message = "用户名不可为空")
	@Size(max = 32, message = "用户名长度不能超过 {max} 位")
	@Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED, maxLength = 32)
	private String username;

	@NotBlank(message = "密码不可为空")
	@Size(min = 8, max = 32, message = "密码长度必须在 {min}-{max} 位之间")
	@PasswordStrength
	@Schema(description = "密码(8-32位，必须包含字母、数字和特殊字符)", requiredMode = RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
	private String password;

	@NotBlank(message = "确认密码不可为空")
	@Schema(description = "确认密码", requiredMode = RequiredMode.REQUIRED)
	private String confirmPassword;

	@NotBlank(message = "昵称不可为空")
	@Size(min = 2, max = 10, message = "昵称长度必须在 {min}-{max} 位之间")
	@Schema(description = "昵称", requiredMode = RequiredMode.REQUIRED, minLength = 2, maxLength = 10)
	private String nickname;

	@NotNull(message = "性别不可为空")
	@Schema(description = "性别", requiredMode = RequiredMode.REQUIRED)
	private Gender gender;

	@PastOrPresent(message = "出生日期不可晚于今天")
	@Schema(description = "出生日期")
	private LocalDate dob;
}
