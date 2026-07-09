package io.github.yingzhuo.claude.security.jwt;

import org.jspecify.annotations.Nullable;
import org.springframework.web.context.request.WebRequest;

public interface TokenResolver {

	@Nullable String resolve(WebRequest request);

}
