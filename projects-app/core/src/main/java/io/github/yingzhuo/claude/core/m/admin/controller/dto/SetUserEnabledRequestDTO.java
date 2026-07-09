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
@Schema(description = "设置用户启用/禁用状态请求")
public class SetUserEnabledRequestDTO {

	@NotBlank(message = "用户ID不可为空")
	@Size(min = 32, max = 32, message = "用户ID长度必须是{max}位")
	@Schema(description = "用户ID", requiredMode = RequiredMode.REQUIRED, minLength = 32, maxLength = 32)
	private String userId;

	@Schema(description = "true=启用，false=禁用", requiredMode = RequiredMode.REQUIRED)
	private boolean enabled;

}
