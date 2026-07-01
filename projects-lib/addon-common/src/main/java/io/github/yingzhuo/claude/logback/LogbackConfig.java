package io.github.yingzhuo.claude.logback;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@ConfigurationProperties(prefix = "logback")
@Validated
public class LogbackConfig {

	@NotBlank(message = "'logback.log-home' 没有配置")
	private String logHome;

	@NotBlank(message = "'logback.log-home' 没有配置")
	private String logName;

}
