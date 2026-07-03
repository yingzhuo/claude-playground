package io.github.yingzhuo.claude.autoconfig.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "claude-playground.logback")
@Validated
public class LogbackProperties {

	@NotBlank
	private String logHome;

	@NotBlank
	private String logName;

}
