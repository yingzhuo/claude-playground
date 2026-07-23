package io.github.yingzhuo.claude.model.event;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户删除事件
 */
@Schema(description = "用户删除事件")
public record UserDeletedEvent(
        @Schema(description = "用户ID") String userId
) {
}
