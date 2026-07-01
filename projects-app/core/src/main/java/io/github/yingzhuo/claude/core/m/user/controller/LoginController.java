package io.github.yingzhuo.claude.core.m.user.controller;

import io.github.yingzhuo.claude.core.m.user.controller.dto.LoginRequestDto;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.model.webmvc.R;
import io.github.yingzhuo.claude.security.annotation.PermitAll;
import io.github.yingzhuo.claude.security.jwt.JwtCreator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@Tag(name = "用户认证", description = "登录相关接口")
public class LoginController {

	private static final String INVALID_CREDENTIALS = "用户名或密码错误";

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtCreator jwtCreator;

	@PostMapping("/login")
	@PermitAll
	@Operation(summary = "用户登录", description = "使用用户名和密码进行登录，返回 JWT token")
	public R<?> login(@RequestBody @Valid LoginRequestDto request) {
		var user = userService.findByUsername(request.getUsername());

		if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			return R.error401(INVALID_CREDENTIALS);
		}

		var token = jwtCreator.apply(user);
		return R.ok(token);
	}

}
