package io.github.yingzhuo.claude.security.autoconfig;

import io.github.yingzhuo.claude.security.mvc.AuthHandlerMethodArgumentResolver;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AutoConfigureAfter(JwtAutoConfiguration.class)
public class JwtMvcAutoConfig implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new AuthHandlerMethodArgumentResolver());
	}

}
