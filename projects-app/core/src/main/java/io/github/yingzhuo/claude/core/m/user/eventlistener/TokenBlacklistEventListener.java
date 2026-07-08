package io.github.yingzhuo.claude.core.m.user.eventlistener;

import io.github.yingzhuo.claude.core.m.user.service.JwtBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * JWT 黑名单事件监听器
 * <p>
 * 监听 {@link TokenBlacklistEvent}，将 JWT 加入黑名单。
 * </p>
 */
@Component
@RequiredArgsConstructor
public class TokenBlacklistEventListener {

	private final JwtBlacklistService jwtBlacklistService;

	/**
	 * 处理 JWT 黑名单事件
	 *
	 * @param event JWT 黑名单事件
	 */
	@EventListener
	@Transactional
	public void handleTokenBlacklist(TokenBlacklistEvent event) {
		jwtBlacklistService.add(event.getJti(), event.getExpiredAt());
	}
}
