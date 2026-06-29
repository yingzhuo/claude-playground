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

package io.github.yingzhuo.claude.core.m.user.controller;

import io.github.yingzhuo.claude.core.m.user.controller.dto.LoginRequestDto;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.model.webmvc.R;
import io.github.yingzhuo.claude.security.jwt.JwtCreator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author 应卓
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class LoginController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtCreator jwtCreator;

	@PostMapping("/login")
	@PreAuthorize("permitAll()")
	public R<?> login(@RequestBody @Valid LoginRequestDto request) {
		var user = userService.findByUsername(request.getUsername());

		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return R.error401("用户名或密码错误");
		}

		var token = jwtCreator.apply(user);
		return R.ok(token);
	}

}
