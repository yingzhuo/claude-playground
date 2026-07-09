package io.github.yingzhuo.claude.core.m.admin.service;

import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminChangePasswordRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminLoginRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.SetUserEnabledRequestDTO;
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

	/**
	 * 修改管理员密码
	 * <p>
	 * 超级管理员可修改任何管理员的密码（含自身），普通管理员只能修改自身的密码。
	 * </p>
	 *
	 * @param currentUserId 当前操作的管理员ID
	 * @param currentRoles  当前操作的管理员角色列表
	 * @param dto           修改密码请求
	 * @throws BusinessException 密码不一致、无权限或管理员不存在
	 */
	void changePassword(String currentUserId, java.util.List<String> currentRoles, AdminChangePasswordRequestDTO dto);

	/**
	 * 设置用户启用/禁用状态
	 *
	 * @param dto 请求
	 */
	void setUserEnabled(SetUserEnabledRequestDTO dto);
}
