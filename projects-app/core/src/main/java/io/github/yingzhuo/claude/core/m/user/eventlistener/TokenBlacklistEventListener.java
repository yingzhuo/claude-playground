package io.github.yingzhuo.claude.core.m.user.eventlistener;

import io.github.yingzhuo.claude.core.m.user.service.JwtBlacklistService;
import io.github.yingzhuo.claude.model.event.TokenBlacklistEvent;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * JWT 黑名单事件监听器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenBlacklistEventListener {

	private final JwtBlacklistService jwtBlacklistService;
	private final MeterRegistry meterRegistry;

	/**
	 * 处理 JWT 黑名单事件
	 *
	 * @param event JWT 黑名单事件
	 */
	@Async
	@EventListener
	public void handleTokenBlacklist(TokenBlacklistEvent event) {
		jwtBlacklistService.add(event.jti(), event.expiredAt());
		meterRegistry.counter("user.logout.total", "kind", "user_logout").increment();
		log.debug("用户登出JWT已加入黑名单: jti={}", event.jti());
	}
}
