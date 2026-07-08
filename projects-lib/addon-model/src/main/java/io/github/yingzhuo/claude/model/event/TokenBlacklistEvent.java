package io.github.yingzhuo.claude.model.event;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * JWT 黑名单事件
 */
@Schema(description = "JWT 黑名单事件")
public record TokenBlacklistEvent(
	@Schema(description = "JWT jti 声明值") String jti,
	@Schema(description = "JWT 过期时间") LocalDateTime expiredAt
) {
}
