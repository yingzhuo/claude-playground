package io.github.yingzhuo.claude.core.m.user.service;

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
}
