/*
 * Copyright 2026-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
