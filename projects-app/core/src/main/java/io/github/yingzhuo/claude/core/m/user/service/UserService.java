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

	void changePassword(String userId, String oldPassword, String newPassword);
}
