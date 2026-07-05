package io.github.yingzhuo.claude.core.m.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(description = "令牌刷新响应")
public class RefreshTokenVO {

	@Schema(description = "新的JWT token", requiredMode = Schema.RequiredMode.REQUIRED)
	private String token;

	@Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
	private String userId;

	@Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
	private String username;
}
