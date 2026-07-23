package io.github.yingzhuo.claude.core.m.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.yingzhuo.claude.core.m.user.controller.dto.ChangePasswordRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.LoginRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.RegisterRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.UpdateProfileRequestDTO;
import io.github.yingzhuo.claude.core.m.user.dao.RoleDao;
import io.github.yingzhuo.claude.core.m.user.dao.UserDao;
import io.github.yingzhuo.claude.core.m.user.dao.UserRoleDao;
import io.github.yingzhuo.claude.core.m.user.mapstruct.UserMapper;
import io.github.yingzhuo.claude.core.m.user.vo.LoginVO;
import io.github.yingzhuo.claude.core.m.user.vo.RefreshTokenVO;
import io.github.yingzhuo.claude.exception.BusinessException;
import io.github.yingzhuo.claude.model.event.UserLoginSuccessEvent;
import io.github.yingzhuo.claude.model.user.Role;
import io.github.yingzhuo.claude.model.user.User;
import io.github.yingzhuo.claude.security.jwt.JwtCreator;
import io.github.yingzhuo.claude.utility.UUIDUtils;
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
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtCreator jwtCreator;
    private final ApplicationEventPublisher eventPublisher;
    private final UserMapper userMapper;
    private final RoleDao roleDao;
    private final UserRoleDao userRoleDao;

    @Nullable
    private User findByUsername(String username) {
        var wrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username);
        return userDao.selectOne(wrapper);
    }

    @Nullable
    private List<String> loadRoleNames(String userId) {
        var roleNames = userRoleDao.findRoleNamesByUserId(userId);
        return roleNames.isEmpty() ? null : roleNames;
    }

    @Override
    @Transactional(readOnly = true)
    public User getProfile(String userId) {
        var user = userDao.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
    }

    @Override
    @Transactional
    public void changePassword(String userId, ChangePasswordRequestDTO dto) {
        var user = userDao.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new BusinessException("旧密码错误");
        }

        if (dto.getOldPassword().equals(dto.getNewPassword())) {
            throw new BusinessException("新密码不能与旧密码相同");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userDao.updateById(user);
    }

    @Override
    @Transactional
    public void updateProfile(String userId, UpdateProfileRequestDTO dto) {
        var user = userDao.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        userMapper.applyProfileUpdate(user, dto);
        userDao.updateById(user);
    }

    @Override
    @Transactional
    public LoginVO login(LoginRequestDTO dto) {
        var user = findByUsername(dto.getUsername());
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        if (!user.isEnabled()) {
            throw new BusinessException("账户已被禁用");
        }

        eventPublisher.publishEvent(new UserLoginSuccessEvent(user.getId()));
        user.setRoles(loadRoleNames(user.getId()));

        var token = jwtCreator.create(user);
        return LoginVO.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    @Override
    @Transactional
    public String register(RegisterRequestDTO dto) {
        if (userDao.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername())) != null) {
            throw new BusinessException("用户名已存在");
        }

        var user = userMapper.toEntity(dto);
        user.setId(UUIDUtils.randomUUIDv7());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        userDao.insert(user);
        // 分配默认角色 ROLE_USER
        var defaultRole = roleDao.selectOne(
                new LambdaQueryWrapper<Role>().eq(Role::getName, "ROLE_USER")
        );
        if (defaultRole != null) {
            userRoleDao.insertUserRole(user.getId(), defaultRole.getId());

        }
        log.debug("用户注册成功: userId={}", user.getId());
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
    @Transactional(readOnly = true)
    public RefreshTokenVO refreshToken(String userId) {
        var user = userDao.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (user.getCancelledAt() != null) {
            throw new BusinessException("账户已注销");
        }

        user.setRoles(loadRoleNames(user.getId()));
        var token = jwtCreator.create(user);
        return RefreshTokenVO.builder()
                .token(token)
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    @Override
    @Transactional
    public int purgeCancelledAccounts() {
        var deadline = LocalDateTime.now().minusWeeks(1);
        var wrapper = new LambdaQueryWrapper<User>()
                .isNotNull(User::getCancelledAt)
                .le(User::getCancelledAt, deadline);

        var count = userDao.delete(wrapper);
        if (count > 0) {
            log.debug("已清理 {} 个已注销超过一周的用户", count);
        }

        return count;
    }
}
