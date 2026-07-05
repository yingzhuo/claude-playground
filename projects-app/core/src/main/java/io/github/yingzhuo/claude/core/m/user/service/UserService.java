package io.github.yingzhuo.claude.core.m.user.service;

import io.github.yingzhuo.claude.core.m.user.vo.LoginVO;
import io.github.yingzhuo.claude.model.user.entity.Gender;
import io.github.yingzhuo.claude.model.user.entity.User;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

	List<User> findAll();

	User findById(String id);

	@Nullable
	User findByUsername(String username);

	void create(User user);

	void update(User user);

	void deleteById(String id);

	void changePassword(String userId, String oldPassword, String newPassword);

	/**
	 * 修改个人信息
	 *
	 * @param userId   用户ID
	 * @param nickname 昵称（为 {@code null} 则不修改）
	 * @param gender   性别（为 {@code null} 则不修改）
	 * @param dob      出生日期（为 {@code null} 则不修改）
	 */
	void updateProfile(String userId, @Nullable String nickname, @Nullable Gender gender, @Nullable LocalDate dob);

	/**
	 * 用户登录
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录响应（含 JWT token、用户ID、用户名）
	 * @throws io.github.yingzhuo.claude.exception.BusinessException 用户名或密码错误
	 */
	LoginVO login(String username, String password);

	/**
	 * 用户注册
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @param gender   性别
	 * @param dob      出生日期
	 * @return 注册成功的用户ID
	 */
	String register(String username, String password, Gender gender, @Nullable LocalDate dob);

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
