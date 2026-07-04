package io.github.yingzhuo.claude.core.m.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yingzhuo.claude.core.m.user.dao.UserDao;
import io.github.yingzhuo.claude.exception.BusinessException;
import io.github.yingzhuo.claude.model.user.entity.Gender;
import io.github.yingzhuo.claude.model.user.entity.User;
import io.github.yingzhuo.claude.utility.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

	@Override
	@Transactional
	public void updateProfile(String userId, @Nullable String nickname, @Nullable Gender gender, @Nullable LocalDate dob) {
		var user = userDao.selectById(userId);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}

		if (nickname != null) {
			user.setNickname(nickname);
		}
		if (gender != null) {
			user.setGender(gender);
		}
		if (dob != null) {
			user.setDob(dob);
		}

		userDao.updateById(user);
	}

	@Override
	@Transactional
	public String register(String username, String password, Gender gender, @Nullable LocalDate dob) {
		if (userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username)) != null) {
			throw new BusinessException("用户名已存在");
		}

		var user = new User();
		user.setId(UUIDUtils.randomUUIDv7());
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setGender(gender);
		user.setDob(dob);
		user.setCreatedAt(LocalDateTime.now());

		userDao.insert(user);
		return user.getId();
	}

	@Override
	@Transactional
	public void cancelAccount(String userId) {
		var user = userDao.selectById(userId);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}
		if (user.getCancelledAt() != null) {
			throw new BusinessException("账户已注销");
		}
		user.setCancelledAt(LocalDateTime.now());
		userDao.updateById(user);
		log.debug("用户账户已标记为注销: userId={}", userId);
	}

	@Override
	@Transactional
	public void reactivateAccount(String userId) {
		var user = userDao.selectById(userId);
		if (user == null) {
			throw new BusinessException("用户不存在");
		}
		if (user.getCancelledAt() == null) {
			return;
		}
		user.setCancelledAt(null);
		userDao.updateById(user);
		log.debug("用户账户已恢复（取消注销）: userId={}", userId);
	}

	@Override
	@Transactional
	public int purgeCancelledAccounts() {
		var deadline = LocalDateTime.now().minusWeeks(1);
		var wrapper = new LambdaQueryWrapper<User>()
			.isNotNull(User::getCancelledAt)
			.le(User::getCancelledAt, deadline);

		var expiredUsers = userDao.selectList(wrapper);

		if (expiredUsers.isEmpty()) {
			return 0;
		}

		log.debug("发现 {} 个已过一周注销等待期的用户账户，开始清理", expiredUsers.size());

		for (var user : expiredUsers) {
			userDao.deleteById(user.getId());
			log.debug("已永久删除已注销用户: userId={}, cancelledAt={}", user.getId(), user.getCancelledAt());
		}

		return expiredUsers.size();
	}
}
