package io.github.yingzhuo.claude.core.m.user.task;

import io.github.yingzhuo.claude.core.m.user.service.JwtBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * JWT 黑名单清理任务
 * <p>
 * 每小时清理一次已过期的黑名单记录，防止表无限膨胀。
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtBlacklistCleanupTask {

    private final JwtBlacklistService jwtBlacklistService;

    @Scheduled(cron = "0 7 * * * ?")  // 每小时的第 7 分钟
    public void purgeExpired() {
        var count = jwtBlacklistService.purgeExpired();
        log.debug("JWT黑名单清理完成，已清理 {} 条过期记录", count);
    }
}
