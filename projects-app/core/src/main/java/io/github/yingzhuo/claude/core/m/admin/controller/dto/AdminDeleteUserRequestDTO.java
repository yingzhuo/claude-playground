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
@Schema(description = "管理员删除用户请求")
public class AdminDeleteUserRequestDTO {

    @NotBlank(message = "用户ID不可为空")
    @Size(min = 32, max = 32, message = "用户ID长度必须是{max}位")
    @Schema(description = "用户ID", requiredMode = RequiredMode.REQUIRED, minLength = 32, maxLength = 32)
    private String userId;

    @NotBlank(message = "密码不可为空")
    @Size(min = 8, max = 32, message = "密码长度不能超过 {max} 位")
    @Schema(description = "当前管理员密码（验证身份）", requiredMode = RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
    private String password;
}
