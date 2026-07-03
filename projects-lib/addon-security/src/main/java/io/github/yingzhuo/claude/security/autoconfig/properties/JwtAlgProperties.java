package io.github.yingzhuo.claude.security.autoconfig.properties;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "claude-playground.jwt")
@Validated
public class JwtAlgProperties {

	@NotBlank
	private String pfxLocation = "keystore:classpath:META-INF/jwt.pfx?pass=changeme&type=pkcs12";

	@NotBlank
	private String alias = "jwt";

	@Nullable
	private String keyPassword = "changeme";

	@PostConstruct
	private void init() {
		if (!StringUtils.hasText(keyPassword)) {
			this.keyPassword = "";
		}
	}

}
