package io.github.yingzhuo.claude.security.jwt;

/**
 * JWT 黑名单检查器
 */
@FunctionalInterface
public interface JwtBlacklistChecker {

    /**
     * 判断指定 jti 是否在黑名单中
     *
     * @param jti JWT 的 jti 声明值
     * @return true 表示已拉黑
     */
    boolean isBlacklisted(String jti);
}
