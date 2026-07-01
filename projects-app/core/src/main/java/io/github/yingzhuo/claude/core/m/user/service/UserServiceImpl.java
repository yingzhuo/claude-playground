package io.github.yingzhuo.claude.core.m.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yingzhuo.claude.core.m.user.dao.UserDao;
import io.github.yingzhuo.claude.exception.BusinessException;
import io.github.yingzhuo.claude.model.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userDao.selectList(null);
	}

	@Override
	@Transactional(readOnly = true)
	public User findById(String id) {
		return userDao.selectById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		var wrapper = new LambdaQueryWrapper<User>()
			.eq(User::getUsername, username);
		return userDao.selectOne(wrapper);
	}

	@Override
	@Transactional
	public void create(User user) {
		userDao.insert(user);
	}

	@Override
	@Transactional
	public void update(User user) {
		userDao.updateById(user);
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	@Override
	@Transactional
	public void changePassword(String userId, String oldPassword, String newPassword) {
		var user = userDao.selectById(userId);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}

		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new BusinessException("旧密码错误");
		}

		if (oldPassword.equals(newPassword)) {
			throw new BusinessException("新密码不能与旧密码相同");
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		userDao.updateById(user);
	}
}
