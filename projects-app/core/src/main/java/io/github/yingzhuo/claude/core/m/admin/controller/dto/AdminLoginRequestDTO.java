package io.github.yingzhuo.claude.core.m.admin.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "管理员登录请求")
public class AdminLoginRequestDTO {

    @NotBlank(message = "用户名不可为空")
    @Size(max = 20, message = "用户名长度不能超过 {max} 位")
    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED, minLength = 1, maxLength = 20)
    private String username;

    @NotBlank(message = "密码不可为空")
    @Size(max = 100, message = "密码长度不能超过 {max} 位")
    @Schema(description = "密码", requiredMode = RequiredMode.REQUIRED, minLength = 1, maxLength = 100)
    private String password;
}
