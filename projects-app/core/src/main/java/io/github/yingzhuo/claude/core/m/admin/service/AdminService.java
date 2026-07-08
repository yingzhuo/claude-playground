package io.github.yingzhuo.claude.core.m.admin.service;

import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminLoginRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.vo.AdminLoginVO;
import io.github.yingzhuo.claude.exception.BusinessException;

/**
 * 管理员服务
 */
public interface AdminService {

	/**
	 * 管理员登录
	 *
	 * @param dto 登录请求
	 * @return 登录响应（含 JWT token、管理员ID、用户名）
	 * @throws BusinessException 用户名或密码错误
	 */
	AdminLoginVO login(AdminLoginRequestDTO dto);
}
