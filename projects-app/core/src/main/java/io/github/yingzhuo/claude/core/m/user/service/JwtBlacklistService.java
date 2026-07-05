package io.github.yingzhuo.claude.core.m.user.service;

import io.github.yingzhuo.claude.security.jwt.JwtBlacklistChecker;

import java.time.LocalDateTime;

/**
 * JWT 黑名单服务
 */
public interface JwtBlacklistService extends JwtBlacklistChecker {

    /**
     * 将指定 jti 加入黑名单
     *
     * @param jti       JWT 的 jti 声明值
     * @param expiredAt 该 JWT 的过期时间
     */
    void add(String jti, LocalDateTime expiredAt);

    /**
     * 清理已过期的黑名单记录
     *
     * @return 删除的记录数
     */
    int purgeExpired();
}
