package io.github.yingzhuo.claude.core.m.admin.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理员修改密码请求")
public class AdminChangePasswordRequestDTO {

	@NotBlank(message = "新密码不可为空")
	@Size(min = 8, max = 32, message = "新密码长度必须在 {min}-{max} 位之间")
	@Schema(description = "新密码（8-32位）", requiredMode = RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
	private String newPassword;

	@NotBlank(message = "确认密码不可为空")
	@Size(min = 8, max = 32, message = "确认密码长度不能超过 {max} 位")
	@Schema(description = "确认密码", requiredMode = RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
	private String confirmPassword;

	@Nullable
	@Schema(description = "目标管理员ID（为空时表示修改自身的密码）")
	private String adminId;
}
