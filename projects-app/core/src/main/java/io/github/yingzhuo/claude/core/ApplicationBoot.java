package io.github.yingzhuo.claude.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.core.ApplicationModules;

/**
 * 启动类
 */
@Slf4j
@Modulith(
	systemName = "claude-playground",
	useFullyQualifiedModuleNames = false
)
@SpringBootApplication
@RequiredArgsConstructor
public class ApplicationBoot implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		var modules = ApplicationModules.of(ApplicationBoot.class)
			.verify();

		modules.forEach(am -> {
			log.trace("{}", am);
		});
	}

}
