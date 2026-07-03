package io.github.yingzhuo.claude.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.core.ApplicationModules;

@Modulith(systemName = "claude-playground")
@SpringBootApplication
@RequiredArgsConstructor
public class ApplicationBoot implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationBoot.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		ApplicationModules.of(ApplicationBoot.class).verify();
	}

}
