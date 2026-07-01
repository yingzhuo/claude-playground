package io.github.yingzhuo.claude.core.m.user.service;

import io.github.yingzhuo.claude.model.user.entity.User;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface UserService {

	List<User> findAll();

	User findById(String id);

	@Nullable
	User findByUsername(String username);

	void create(User user);

	void update(User user);

	void deleteById(String id);

	/**
	 * @param userId      用户ID
	 * @param oldPassword 旧密码
	 * @param newPassword 新密码
	 * @throws io.github.yingzhuo.claude.exception.BusinessException 旧密码错误、新旧密码相同或密码复杂度不满足要求时抛出
	 */
	void changePassword(String userId, String oldPassword, String newPassword);
}
