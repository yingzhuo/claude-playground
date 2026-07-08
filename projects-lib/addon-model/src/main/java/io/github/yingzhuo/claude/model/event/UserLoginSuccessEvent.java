package io.github.yingzhuo.claude.model.event;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 用户登录成功事件
 */
@Schema(description = "用户登录成功事件")
public record UserLoginSuccessEvent(@Schema(description = "用户ID") String userId) {
}
