/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

	/**
	 * 配置 {@link OpenAPI} 实例，设置 API 标题、描述和版本信息。
	 * <p>
	 * 标题和版本分别从 {@link ApplicationName} 和 {@link ApplicationVersion} 注入。
	 * </p>
	 *
	 * @return OpenAPI 实例
	 */
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
