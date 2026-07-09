package io.github.yingzhuo.claude.model.event;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户启用/禁用事件
 */
@Schema(description = "用户启用/禁用事件")
public record UserEnabledEvent(
	@Schema(description = "用户ID") String userId,
	@Schema(description = "true=启用，false=禁用") boolean enabled
) {
}
