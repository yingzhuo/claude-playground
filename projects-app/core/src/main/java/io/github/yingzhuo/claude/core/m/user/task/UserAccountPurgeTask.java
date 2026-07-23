package io.github.yingzhuo.claude.core.m.user.task;

import io.github.yingzhuo.claude.core.m.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 用户注销账户清理定时任务
 * <p>
 * 每 2 小时执行一次，清理 {@code cancelledAt} 超过 7 天的用户记录（即永久删除）。
 * 首次执行延迟 1 分钟，以便应用完全启动后再执行。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserAccountPurgeTask {

    private final UserService userService;

    /**
     * 清理已过注销等待期的用户账户。
     * <p>
     * 查询条件：{@code cancelledAt IS NOT NULL AND cancelledAt &lt; now() - 7 days}
     * </p>
     */
    @Scheduled(initialDelay = 60_000, fixedRate = 7_200_000)
    public void purgeCancelledAccounts() {
        var count = userService.purgeCancelledAccounts();
        if (count > 0) {
            log.debug("清理已注销用户 {} 个", count);
        }
    }
}
