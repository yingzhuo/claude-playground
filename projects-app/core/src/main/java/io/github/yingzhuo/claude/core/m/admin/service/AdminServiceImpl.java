package io.github.yingzhuo.claude.core.m.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.*;
import io.github.yingzhuo.claude.core.m.admin.dao.AdminDao;
import io.github.yingzhuo.claude.core.m.admin.dao.UserDao;
import io.github.yingzhuo.claude.core.m.admin.vo.AdminLoginVO;
import io.github.yingzhuo.claude.core.m.admin.vo.UserListPageVO;
import io.github.yingzhuo.claude.exception.BusinessException;
import io.github.yingzhuo.claude.model.admin.Admin;
import io.github.yingzhuo.claude.model.event.UserDeletedEvent;
import io.github.yingzhuo.claude.model.event.UserEnabledEvent;
import io.github.yingzhuo.claude.model.user.User;
import io.github.yingzhuo.claude.security.jwt.JwtCreator;
import io.github.yingzhuo.claude.utility.MyBatisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminDao adminDao;
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	private final JwtCreator jwtCreator;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@Transactional
	public AdminLoginVO login(AdminLoginRequestDTO dto) {
		var admin = adminDao.selectOne(
			new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, dto.getUsername())
		);
		if (admin == null || !passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
			throw new BusinessException("用户名或密码错误");
		}

		admin.setLastLoginTime(LocalDateTime.now());
		adminDao.updateById(admin);

		var token = jwtCreator.create(admin);
		return AdminLoginVO.builder()
			.token(token)
			.adminId(admin.getId())
			.username(admin.getUsername())
			.build();
	}

	@Override
	@Transactional
	public void changePassword(String currentUserId, List<String> currentRoles, AdminChangePasswordRequestDTO dto) {
		if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
			throw new BusinessException("两次输入的密码不一致");
		}

		var targetAdminId = dto.getAdminId() != null ? dto.getAdminId() : currentUserId;
		if (!targetAdminId.equals(currentUserId) && !currentRoles.contains("ROLE_SUPER")) {
			throw new BusinessException("无权修改其他管理员的密码");
		}

		var targetAdmin = adminDao.selectById(targetAdminId);
		if (targetAdmin == null) {
			throw new BusinessException("管理员不存在");
		}

		targetAdmin.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		adminDao.updateById(targetAdmin);
		log.debug("管理员密码已修改: adminId={}", targetAdminId);
	}

	@Override
	@Transactional
	public void setUserEnabled(SetUserEnabledRequestDTO dto) {
		eventPublisher.publishEvent(new UserEnabledEvent(dto.getUserId(), dto.isEnabled()));
	}

	@Override
	@Transactional
	public void deleteUser(String currentUserId, AdminDeleteUserRequestDTO dto) {
		var admin = adminDao.selectById(currentUserId);
		if (admin == null || !passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
			throw new BusinessException("密码错误");
		}

		eventPublisher.publishEvent(new UserDeletedEvent(dto.getUserId()));
	}

	@Override
	@Transactional(readOnly = true)
	public UserListPageVO listUsers(UserListRequestDTO dto) {
		var wrapper = buildUserQueryWrapper(dto.getSearchKey());
		var page = new Page<User>(dto.getPageNumber(), dto.getPageSize());
		var result = userDao.selectPage(page, wrapper);

		return UserListPageVO.builder()
			.pageNumber(result.getCurrent())
			.pageSize(result.getSize())
			.total(result.getTotal())
			.totalPages(result.getPages())
			.items(result.getRecords())
			.build();
	}

	private LambdaQueryWrapper<User> buildUserQueryWrapper(@Nullable String searchKey) {
		if (searchKey == null || searchKey.isBlank()) {
			return new LambdaQueryWrapper<>();
		}
		return new LambdaQueryWrapper<User>()
			.likeRight(User::getUsername, MyBatisUtils.escapeLike(searchKey.trim()));
	}

}
