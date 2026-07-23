package io.github.yingzhuo.claude.core.m.user.eventlistener;

import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.model.event.UserLoginSuccessEvent;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 用户登录成功事件监听器
 * <p>
 * 监听 {@link UserLoginSuccessEvent}，在用户登录成功后取消注销标记并记录指标。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserLoginEventListener {

    private final UserService userService;
    private final MeterRegistry meterRegistry;

    /**
     * 处理用户登录成功事件
     *
     * @param event 用户登录成功事件
     */
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUserLoginSuccess(UserLoginSuccessEvent event) {
        meterRegistry.counter("user.login.total", "kind", "user_login").increment();
        userService.reactivateAccount(event.userId());
        log.debug("用户登录后已清除注销标记: userId={}", event.userId());
    }

}
