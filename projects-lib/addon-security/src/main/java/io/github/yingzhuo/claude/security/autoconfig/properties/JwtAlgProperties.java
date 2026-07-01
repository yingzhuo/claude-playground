package io.github.yingzhuo.claude.security.autoconfig.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "claude-playground.jwt.alg")
@Validated
public class JwtAlgProperties {

	@NotBlank
	private String pemLocation = "classpath:META-INF/RSA256.pem";

	@NotBlank
	private String keyPwd = "l@LBu8FmvHNxer8[E@l^";

}
