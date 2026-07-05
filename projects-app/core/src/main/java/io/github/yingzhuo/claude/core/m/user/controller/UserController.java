package io.github.yingzhuo.claude.core.m.user.controller;

import io.github.yingzhuo.claude.core.m.user.controller.dto.ChangePasswordRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.RegisterRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.UpdateProfileRequestDTO;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.model.user.entity.User;
import io.github.yingzhuo.claude.model.webmvc.R;
import io.github.yingzhuo.claude.security.annotation.CurrentUserId;
import io.github.yingzhuo.claude.security.swagger.HiddenParam;
import io.github.yingzhuo.claude.security.swagger.MySecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "用户信息", description = "用户信息相关接口")
public class UserController {

	private final UserService userService;

	@PostMapping("/password")
	@Operation(summary = "修改密码", description = "用户修改自己的密码，需要提供旧密码进行验证")
	@MySecurityRequirement
	public R<?> changePassword(@RequestBody @Valid ChangePasswordRequestDTO dto, @HiddenParam @CurrentUserId String userId) {
		userService.changePassword(userId, dto);
		return R.ok();
	}

	@PostMapping("/profile")
	@Operation(summary = "修改个人信息", description = "修改当前登录用户的性别和出生日期，仅更新传入的字段")
	@MySecurityRequirement
	public R<?> updateProfile(@RequestBody @Valid UpdateProfileRequestDTO dto, @HiddenParam @CurrentUserId String userId) {
		userService.updateProfile(userId, dto);
		return R.ok();
	}

	@GetMapping("/profile")
	@Operation(summary = "获取个人信息", description = "获取当前登录用户的个人信息")
	@MySecurityRequirement
	public R<User> getProfile(@HiddenParam @CurrentUserId String userId) {
		var profile = userService.getProfile(userId);
		return R.ok(profile);
	}

	@PostMapping("/register")
	@Operation(summary = "用户注册", description = "注册新用户，用户名、密码、性别为必填，出生日期可选")
	public R<String> register(@RequestBody @Valid RegisterRequestDTO dto) {
		var userId = userService.register(dto);
		return R.ok(userId);
	}

	@PostMapping("/cancel")
	@Operation(summary = "注销账户", description = "将当前登录用户标记为已注销状态。账户不会立即删除，系统将在注销满 7 天后自动清理。")
	@MySecurityRequirement
	public R<?> cancelAccount(@HiddenParam @CurrentUserId String userId) {
		userService.cancelAccount(userId);
		return R.ok();
	}
}
