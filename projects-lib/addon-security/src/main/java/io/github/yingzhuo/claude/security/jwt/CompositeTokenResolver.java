package io.github.yingzhuo.claude.security.jwt;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

/**
 * 复合 TokenResolver，按顺序尝试多个解析器，返回第一个非空结果
 */
public class CompositeTokenResolver implements TokenResolver {

    private final List<TokenResolver> delegates;

    public CompositeTokenResolver(TokenResolver... delegates) {
        this(List.of(delegates));
    }

    public CompositeTokenResolver(List<TokenResolver> delegates) {
        Assert.notEmpty(delegates, "delegates must not be empty");
        Assert.noNullElements(delegates, "delegates must not contain null elements");
        this.delegates = List.copyOf(delegates);
    }

    public static TokenResolver of(TokenResolver... resolvers) {
        if (resolvers.length == 0) {
            return webRequest -> null;
        }
        if (resolvers.length == 1) {
            Assert.notNull(resolvers[0], "resolver must not be null");
            return resolvers[0];
        }
        return new CompositeTokenResolver(List.of(resolvers));
    }

    @Override
    public @Nullable String resolve(WebRequest request) {
        for (var delegate : delegates) {
            var token = delegate.resolve(request);
            if (token != null) {
                return token;
            }
        }
        return null;
    }

}
