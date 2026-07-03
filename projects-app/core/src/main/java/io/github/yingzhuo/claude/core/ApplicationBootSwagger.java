package io.github.yingzhuo.claude.core;

import io.github.yingzhuo.claude.misc.ApplicationName;
import io.github.yingzhuo.claude.misc.ApplicationVersion;
import io.github.yingzhuo.claude.misc.SwaggerConstants;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
	name = SwaggerConstants.AUTH_HEADER,
	type = SecuritySchemeType.APIKEY,
	in = SecuritySchemeIn.HEADER,
	paramName = SwaggerConstants.X_AUTH_TOKEN,
	description = "自定义请求头鉴权"
)
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
