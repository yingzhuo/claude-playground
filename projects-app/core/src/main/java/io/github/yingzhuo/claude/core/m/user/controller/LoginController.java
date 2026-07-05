package io.github.yingzhuo.claude.core.m.user.controller;

import io.github.yingzhuo.claude.core.m.user.controller.dto.LoginRequestDTO;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.core.m.user.vo.LoginVO;
import io.github.yingzhuo.claude.model.webmvc.R;
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
@Tag(name = "用户认证", description = "登录相关接口")
public class LoginController {

	private final UserService userService;

	@PostMapping("/login")
	@Operation(summary = "用户登录", description = "使用用户名和密码进行登录，返回JWT token及用户信息")
	public R<LoginVO> login(@RequestBody @Valid LoginRequestDTO request) {
		var vo = userService.login(request.getUsername(), request.getPassword());
		return R.ok(vo);
	}

}
