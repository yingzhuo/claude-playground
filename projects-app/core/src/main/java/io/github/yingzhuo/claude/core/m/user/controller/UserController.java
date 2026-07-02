package io.github.yingzhuo.claude.core.m.user.controller;

import io.github.yingzhuo.claude.core.m.user.controller.dto.ChangePasswordRequestDto;
import io.github.yingzhuo.claude.core.m.user.controller.dto.RegisterRequestDto;
import io.github.yingzhuo.claude.core.m.user.controller.dto.UpdateProfileRequestDto;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.model.webmvc.R;
import io.github.yingzhuo.claude.security.annotation.CurrentUserId;
import io.github.yingzhuo.claude.security.annotation.IsAuthenticated;
import io.github.yingzhuo.claude.security.annotation.PermitAll;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "用户信息", description = "用户信息相关接口")
public class UserController {

	private final UserService userService;

	@PostMapping("/password")
	@IsAuthenticated
	@Operation(summary = "修改密码", description = "用户修改自己的密码，需要提供旧密码进行验证")
	public R<?> changePassword(@RequestBody @Valid ChangePasswordRequestDto request, @CurrentUserId String userId) {
		userService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
		return R.ok();
	}

	@PostMapping("/profile")
	@IsAuthenticated
	@Operation(summary = "修改个人信息", description = "修改当前登录用户的性别和出生日期，仅更新传入的字段")
	public R<?> updateProfile(@RequestBody @Valid UpdateProfileRequestDto request, @CurrentUserId String userId) {
		userService.updateProfile(userId, request.getNickname(), request.getGender(), request.getDob());
		return R.ok();
	}

	@PostMapping("/register")
	@PermitAll
	@Operation(summary = "用户注册", description = "注册新用户，用户名、密码、性别为必填，出生日期可选")
	public R<String> register(@RequestBody @Valid RegisterRequestDto request) {
		var userId = userService.register(request.getUsername(), request.getPassword(), request.getGender(), request.getDob());
		return R.ok(userId);
	}
}
