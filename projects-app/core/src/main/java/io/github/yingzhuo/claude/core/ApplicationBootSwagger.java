package io.github.yingzhuo.claude.core;

import io.github.yingzhuo.claude.misc.ApplicationName;
import io.github.yingzhuo.claude.misc.ApplicationVersion;
import io.github.yingzhuo.claude.security.swagger.MySecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MySecurityScheme
public class ApplicationBootSwagger {

	@ApplicationName
	private String applicationName;

	@ApplicationVersion
	private String applicationVersion;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title(applicationName)
				.description("Claude Playground")
				.version("v" + applicationVersion)
			);
	}

}
