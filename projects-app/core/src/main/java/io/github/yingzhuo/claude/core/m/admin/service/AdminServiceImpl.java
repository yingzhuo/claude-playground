package io.github.yingzhuo.claude.core.m.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminChangePasswordRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminLoginRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.dao.AdminDao;
import io.github.yingzhuo.claude.core.m.admin.vo.AdminLoginVO;
import io.github.yingzhuo.claude.exception.BusinessException;
import io.github.yingzhuo.claude.model.admin.Admin;
import io.github.yingzhuo.claude.security.jwt.JwtCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
	private final PasswordEncoder passwordEncoder;
	private final JwtCreator jwtCreator;

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
}
