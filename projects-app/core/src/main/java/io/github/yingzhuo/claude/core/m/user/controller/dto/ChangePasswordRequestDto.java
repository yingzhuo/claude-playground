package io.github.yingzhuo.claude.core.m.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "修改密码请求")
public class ChangePasswordRequestDto implements Serializable {

    @NotBlank
    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;

    @NotBlank
    @Size(min = 8, max = 32)
    @Schema(description = "新密码 (8-32位，必须包含字母、数字和特殊字符)", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
    private String newPassword;
}
