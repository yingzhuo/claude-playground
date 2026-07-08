package io.github.yingzhuo.claude.core.m.user.eventlistener;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * JWT 黑名单事件
 * <p>
 * 当需要将 JWT 加入黑名单时发布此事件。
 * </p>
 */
@Getter
@RequiredArgsConstructor
@Schema(description = "JWT 黑名单事件")
public class TokenBlacklistEvent {

	@Schema(description = "JWT jti 声明值")
	private final String jti;

	@Schema(description = "JWT 过期时间")
	private final LocalDateTime expiredAt;
}
