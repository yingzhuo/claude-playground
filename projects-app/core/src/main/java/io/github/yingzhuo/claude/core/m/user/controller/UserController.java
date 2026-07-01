package io.github.yingzhuo.claude.core.m.user.controller;

import io.github.yingzhuo.claude.core.m.user.controller.dto.ChangePasswordRequestDto;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.model.webmvc.R;
import io.github.yingzhuo.claude.security.annotation.CurrentUserId;
import io.github.yingzhuo.claude.security.annotation.IsAuthenticated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 *
 * @author 应卓
 */
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
}
