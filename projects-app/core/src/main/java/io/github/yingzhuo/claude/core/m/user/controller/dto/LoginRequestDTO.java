package io.github.yingzhuo.claude.core.m.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "登录请求")
public class LoginRequestDTO {

    @NotBlank(message = "用户名不可为空")
    @Size(max = 32, message = "用户名长度不能超过 {max} 位")
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 32)
    private String username;

    @NotBlank(message = "密码不可为空")
    @Size(max = 64, message = "密码长度不能超过 {max} 位")
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 1, maxLength = 64)
    private String password;

}
