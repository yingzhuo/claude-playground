package io.github.yingzhuo.claude.core.m.user.eventlistener;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 用户登录成功事件
 * <p>
 * 当用户登录成功后发布此事件，监听器可对已注销用户执行恢复操作。
 * </p>
 */
@Getter
@RequiredArgsConstructor
@Schema(description = "用户登录成功事件")
public class UserLoginSuccessEvent {

	@Schema(description = "用户ID")
	private final String userId;
}
