package io.github.yingzhuo.claude.autoconfig;

import io.github.yingzhuo.claude.autoconfig.properties.LogbackProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(LogbackProperties.class)
public class LogbackAutoConfiguration {
}
