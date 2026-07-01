package io.github.yingzhuo.claude.security.token;

import org.jspecify.annotations.Nullable;
import org.springframework.web.context.request.WebRequest;

@FunctionalInterface
public interface TokenResolver {

	@Nullable
	public String resolve(WebRequest request);

}
