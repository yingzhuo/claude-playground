package io.github.yingzhuo.claude.core.m.user.controller;

import io.github.yingzhuo.claude.core.m.user.controller.dto.LoginRequestDTO;
import io.github.yingzhuo.claude.core.m.user.service.JwtBlacklistService;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.core.m.user.vo.LoginVO;
import io.github.yingzhuo.claude.core.m.user.vo.RefreshTokenVO;
import io.github.yingzhuo.claude.model.webmvc.R;
import io.github.yingzhuo.claude.security.Auth;
import io.github.yingzhuo.claude.security.annotation.CurrentUserId;
import io.github.yingzhuo.claude.security.swagger.HiddenParam;
import io.github.yingzhuo.claude.security.swagger.MySecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "用户认证", description = "登录相关接口")
public class LoginController {

	private final UserService userService;
	private final JwtBlacklistService jwtBlacklistService;

	@PostMapping("/login")
	@Operation(summary = "用户登录", description = "使用用户名和密码进行登录，返回JWT token及用户信息")
	public R<LoginVO> login(@RequestBody @Valid LoginRequestDTO dto) {
		var vo = userService.login(dto);
		return R.ok(vo);
	}

	@PostMapping("/token/refresh")
	@Operation(summary = "刷新JWT令牌", description = "使用当前有效的JWT token换取新的token，旧token立即失效。需在请求头中携带有效的X-Auth-Token或X-Token。")
	@MySecurityRequirement
	public R<RefreshTokenVO> refresh(@HiddenParam @CurrentUserId String userId) {
		var auth = (Auth) SecurityContextHolder.getContext().getAuthentication();
		var vo = userService.refreshToken(userId);

		if (auth.getTokenJti() != null && auth.getTokenExpiresAt() != null) {
			jwtBlacklistService.add(auth.getTokenJti(), auth.getTokenExpiresAt());
		}

		return R.ok(vo);
	}

}
