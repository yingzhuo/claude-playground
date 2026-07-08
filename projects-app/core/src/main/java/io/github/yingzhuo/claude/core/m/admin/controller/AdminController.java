package io.github.yingzhuo.claude.core.m.admin.controller;

import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminLoginRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.service.AdminService;
import io.github.yingzhuo.claude.core.m.admin.vo.AdminLoginVO;
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
}
