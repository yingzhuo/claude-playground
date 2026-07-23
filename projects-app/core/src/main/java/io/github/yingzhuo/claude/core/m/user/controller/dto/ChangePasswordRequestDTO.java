package io.github.yingzhuo.claude.core.m.user.controller.dto;

import io.github.yingzhuo.claude.jsr380.PasswordStrength;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "修改密码请求")
public class ChangePasswordRequestDTO {

    @NotBlank(message = "旧密码不可为空")
    @Size(max = 128, message = "旧密码长度不能超过 {max} 位")
    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 128)
    private String oldPassword;

    @NotBlank(message = "新密码不可为空")
    @Size(min = 8, max = 32, message = "新密码长度必须在 {min}-{max} 位之间")
    @PasswordStrength
    @Schema(description = "新密码 (8-32位，必须包含字母、数字和特殊字符)", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
    private String newPassword;
}
