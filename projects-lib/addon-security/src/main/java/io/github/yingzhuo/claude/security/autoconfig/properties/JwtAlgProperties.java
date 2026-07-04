package io.github.yingzhuo.claude.security.autoconfig.properties;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
	private String pfxLocation = "keystore:classpath:META-INF/jwt.pfx?storepass=changeme&type=pkcs12";

	@NotBlank
	private String alias = "ecdsa";

	@Nullable
	private String keyPassword = "changeme";

	@Positive
	private long expirationInHours = 4;

	@PostConstruct
	private void init() {
		if (!StringUtils.hasText(keyPassword)) {
			this.keyPassword = "";
		}
	}

}
