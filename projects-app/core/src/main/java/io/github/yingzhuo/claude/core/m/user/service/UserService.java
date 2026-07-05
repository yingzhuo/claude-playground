package io.github.yingzhuo.claude.core.m.user.service;

import io.github.yingzhuo.claude.core.m.user.controller.dto.ChangePasswordRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.LoginRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.RegisterRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.UpdateProfileRequestDTO;
import io.github.yingzhuo.claude.core.m.user.vo.LoginVO;
import io.github.yingzhuo.claude.model.user.entity.User;

public interface UserService {

	/**
	 * 获取当前用户个人信息
	 *
	 * @param userId 用户ID
	 * @return 用户实体
	 * @throws io.github.yingzhuo.claude.exception.BusinessException 用户不存在
	 */
	User getProfile(String userId);

	/**
	 * 修改用户密码
	 * <p>
	 * 验证旧密码正确后更新为新密码。新旧密码不能相同。
	 * </p>
	 *
	 * @param userId 用户ID
	 * @param dto    修改密码请求（含旧密码、新密码）
	 * @throws io.github.yingzhuo.claude.exception.BusinessException 用户不存在、旧密码错误或新旧密码相同
	 */
	void changePassword(String userId, ChangePasswordRequestDTO dto);

	/**
	 * 修改个人信息
	 *
	 * @param userId 用户ID
	 * @param dto    修改请求
	 */
	void updateProfile(String userId, UpdateProfileRequestDTO dto);

	/**
	 * 用户登录
	 *
	 * @param dto 登录请求
	 * @return 登录响应（含 JWT token、用户ID、用户名）
	 * @throws io.github.yingzhuo.claude.exception.BusinessException 用户名或密码错误
	 */
	LoginVO login(LoginRequestDTO dto);

	/**
	 * 用户注册
	 *
	 * @param dto 注册请求
	 * @return 注册成功的用户ID
	 */
	String register(RegisterRequestDTO dto);

	/**
	 * 注销当前登录用户账户
	 * <p>
	 * 将当前用户的 {@code cancelledAt} 字段设置为当前时间，标记为"已注销"。
	 * 账户不会立即删除，系统会在注销满 7 天后自动清理。
	 * </p>
	 *
	 * @param userId 用户ID
	 */
	void cancelAccount(String userId);

	/**
	 * 物理删除已注销超过一周的用户
	 *
	 * @return 删除的记录数
	 */
	int purgeCancelledAccounts();

	/**
	 * 恢复已注销的账户
	 * <p>
	 * 将 {@code cancelledAt} 设置为 {@code null}，表示用户撤销注销操作。
	 * </p>
	 *
	 * @param userId 用户ID
	 */
	void reactivateAccount(String userId);
}
