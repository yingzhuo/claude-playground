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
public class RegisterRequestDto {

    @NotBlank
    @Size(max = 32)
    @Schema(description = "用户名", requiredMode = RequiredMode.REQUIRED, maxLength = 32)
    private String username;

    @NotBlank
    @Size(min = 8, max = 32)
    @PasswordStrength
    @Schema(description = "密码（8-32位，必须包含字母、数字和特殊字符）", requiredMode = RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
    private String password;

    @NotBlank
    @Schema(description = "确认密码", requiredMode = RequiredMode.REQUIRED)
    private String confirmPassword;

    @NotNull
    @Schema(description = "性别", requiredMode = RequiredMode.REQUIRED)
    private Gender gender;

    @PastOrPresent
    @Schema(description = "出生日期")
    private LocalDate dob;
}
