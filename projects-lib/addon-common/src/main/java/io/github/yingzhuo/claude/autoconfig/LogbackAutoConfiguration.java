package io.github.yingzhuo.claude.autoconfig;

import io.github.yingzhuo.claude.logback.LogbackConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(LogbackConfig.class)
public class LogbackAutoConfiguration {
}
