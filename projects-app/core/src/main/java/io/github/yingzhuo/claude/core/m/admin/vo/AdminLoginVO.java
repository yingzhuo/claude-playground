package io.github.yingzhuo.claude.core.m.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "管理员登录响应")
public class AdminLoginVO {

	@Schema(description = "JWT token", requiredMode = Schema.RequiredMode.REQUIRED)
	private String token;

	@Schema(description = "管理员ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private String adminId;

	@Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
	private String username;
}
