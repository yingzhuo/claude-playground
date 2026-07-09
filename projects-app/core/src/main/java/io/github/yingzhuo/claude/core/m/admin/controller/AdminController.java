package io.github.yingzhuo.claude.core.m.admin.controller;

import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminChangePasswordRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminLoginRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.SetUserEnabledRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.service.AdminService;
import io.github.yingzhuo.claude.core.m.admin.vo.AdminLoginVO;
import io.github.yingzhuo.claude.model.webmvc.R;
import io.github.yingzhuo.claude.security.Auth;
import io.github.yingzhuo.claude.security.annotation.CurrentUserId;
import io.github.yingzhuo.claude.security.swagger.HiddenParam;
import io.github.yingzhuo.claude.security.swagger.MySecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Tag(name = "管理员认证", description = "管理员登录相关接口")
public class AdminController {

	private final AdminService adminService;

	@PostMapping("/login")
	@Operation(summary = "管理员登录", description = "使用用户名和密码进行登录，返回JWT token及管理员信息")
	public R<AdminLoginVO> login(@RequestBody @Valid AdminLoginRequestDTO dto) {
		var vo = adminService.login(dto);
		return R.ok(vo);
	}

	@PostMapping("/password")
	@MySecurityRequirement
	@Operation(summary = "修改管理员密码", description = "超级管理员可修改任何管理员的密码，普通管理员只能修改自己的密码")
	public R<?> changePassword(@RequestBody @Valid AdminChangePasswordRequestDTO dto, @HiddenParam @CurrentUserId String currentUserId, @HiddenParam @Nullable Auth auth) {
		adminService.changePassword(currentUserId, auth != null ? auth.getRoles() : java.util.List.of(), dto);
		return R.ok();
	}

	@PostMapping("/user/enabled")
	@MySecurityRequirement
	@Operation(summary = "设置用户启用/禁用状态", description = "启用或禁用指定用户的账户")
	public R<?> setUserEnabled(@RequestBody @Valid SetUserEnabledRequestDTO dto) {
		adminService.setUserEnabled(dto);
		return R.ok();
	}

}
