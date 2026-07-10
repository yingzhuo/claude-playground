package io.github.yingzhuo.claude.core.m.user.eventlistener;

import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.model.event.UserLoginSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户登录成功事件监听器
 * <p>
 * 监听 {@link UserLoginSuccessEvent}，在用户登录成功后取消注销标记。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserLoginEventListener {

	private final UserService userService;

	/**
	 * 处理用户登录成功事件
	 *
	 * @param event 用户登录成功事件
	 */
	@Async
	@EventListener
	@Transactional
	public void handleUserLoginSuccess(UserLoginSuccessEvent event) {
		userService.reactivateAccount(event.userId());
		log.debug("用户登录后已清除注销标记: userId={}", event.userId());
	}
}
