package io.github.yingzhuo.claude.logback;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "logback")
@Validated
public class LogbackConfig {

	@NotBlank
	private String logHome;

	@NotBlank
	private String logName;

}
