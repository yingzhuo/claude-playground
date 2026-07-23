package io.github.yingzhuo.claude.security.jwt;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

/**
 * 从请求头中解析 JWT 令牌的 TokenResolver 实现
 */
public class HeaderTokenResolver implements TokenResolver {

    private final String headerName;
    private final String prefix;

    public HeaderTokenResolver(String headerName, @Nullable String prefix) {
        Assert.hasText(headerName, "header name must not be empty");
        this.prefix = prefix != null ? prefix : "";
        this.headerName = headerName;
    }

    public HeaderTokenResolver(String headerName) {
        this(headerName, null);
    }

    @Override
    public @Nullable String resolve(WebRequest request) {
        var headValue = request.getHeader(headerName);
        if (StringUtils.hasText(headValue)) {
            var trimmed = headValue.trim();
            if (prefix.isEmpty() || trimmed.startsWith(prefix)) {
                return prefix.isEmpty() ? trimmed : trimmed.substring(prefix.length());
            }
        }

        return null;
    }

}
