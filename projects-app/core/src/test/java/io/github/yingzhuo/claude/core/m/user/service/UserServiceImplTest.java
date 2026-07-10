package io.github.yingzhuo.claude.core.m.user.service;

import com.auth0.jwt.JWT;
import io.github.yingzhuo.claude.core.m.user.controller.dto.ChangePasswordRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.LoginRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.RegisterRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.UpdateProfileRequestDTO;
import io.github.yingzhuo.claude.exception.BusinessException;
import io.github.yingzhuo.claude.model.user.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Test
	void should_register_and_login_successfully() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("test_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.dob(LocalDate.of(2000, 1, 1))
			.build();

		var userId = userService.register(registerDTO);
		assertThat(userId).isNotBlank();

		var loginDTO = LoginRequestDTO.builder()
			.username("test_user")
			.password("Passw0rd!")
			.build();

		var loginVO = userService.login(loginDTO);
		assertThat(loginVO).isNotNull();
		assertThat(loginVO.getUserId()).isEqualTo(userId);
		assertThat(loginVO.getUsername()).isEqualTo("test_user");
		assertThat(loginVO.getToken()).isNotBlank();

		var decoded = JWT.decode(loginVO.getToken());
		assertThat(decoded.getClaim("roles").asList(String.class))
			.isNotNull()
			.containsExactly("ROLE_USER");
	}

	@Test
	void should_throw_when_login_with_wrong_password() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("wrong_pwd_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.FEMALE)
			.build();

		userService.register(registerDTO);

		var loginDTO = LoginRequestDTO.builder()
			.username("wrong_pwd_user")
			.password("WrongPass1!")
			.build();

		assertThatThrownBy(() -> userService.login(loginDTO))
			.isInstanceOf(BusinessException.class)
			.hasMessage("用户名或密码错误");
	}

	@Test
	void should_change_password_then_old_password_fails() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("pwd_change_user")
			.password("OldPass1!")
			.confirmPassword("OldPass1!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build();

		var userId = userService.register(registerDTO);

		var changeDTO = new ChangePasswordRequestDTO();
		changeDTO.setOldPassword("OldPass1!");
		changeDTO.setNewPassword("NewPass1!");

		userService.changePassword(userId, changeDTO);

		var oldLogin = LoginRequestDTO.builder()
			.username("pwd_change_user")
			.password("OldPass1!")
			.build();
		assertThatThrownBy(() -> userService.login(oldLogin))
			.isInstanceOf(BusinessException.class)
			.hasMessage("用户名或密码错误");

		var newLogin = LoginRequestDTO.builder()
			.username("pwd_change_user")
			.password("NewPass1!")
			.build();
		var loginVO = userService.login(newLogin);
		assertThat(loginVO).isNotNull();
	}

	@Test
	void should_update_profile() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("profile_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("原名")
			.gender(Gender.MALE)
			.dob(LocalDate.of(2000, 1, 1))
			.build();

		var userId = userService.register(registerDTO);

		var updateDTO = new UpdateProfileRequestDTO();
		updateDTO.setNickname("新名");
		updateDTO.setGender(Gender.FEMALE);
		updateDTO.setDob(LocalDate.of(1999, 12, 31));

		userService.updateProfile(userId, updateDTO);

		var loginVO = userService.login(LoginRequestDTO.builder()
			.username("profile_user")
			.password("Passw0rd!")
			.build());
		assertThat(loginVO.getUsername()).isEqualTo("profile_user");
	}

	@Test
	void should_cancel_and_reactivate_account() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("cancel_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build();

		var userId = userService.register(registerDTO);

		userService.cancelAccount(userId);

		userService.reactivateAccount(userId);
	}

	@Test
	void should_refresh_token_successfully() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("refresh_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build();

		var userId = userService.register(registerDTO);

		var vo = userService.refreshToken(userId);
		assertThat(vo).isNotNull();
		assertThat(vo.getToken()).isNotBlank();
		assertThat(vo.getUserId()).isEqualTo(userId);
		assertThat(vo.getUsername()).isEqualTo("refresh_user");
	}

	@Test
	void should_throw_when_refresh_token_with_non_existent_user() {
		assertThatThrownBy(() -> userService.refreshToken("non_existent_id"))
			.isInstanceOf(BusinessException.class)
			.hasMessage("用户不存在");
	}

	@Test
	void should_throw_when_refresh_token_with_cancelled_account() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("cancelled_refresh_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.FEMALE)
			.build();

		var userId = userService.register(registerDTO);
		userService.cancelAccount(userId);

		assertThatThrownBy(() -> userService.refreshToken(userId))
			.isInstanceOf(BusinessException.class)
			.hasMessage("账户已注销");
	}

	@Test
	void should_get_profile_successfully() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("profile_get_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build();

		var userId = userService.register(registerDTO);
		var profile = userService.getProfile(userId);

		assertThat(profile).isNotNull();
		assertThat(profile.getId()).isEqualTo(userId);
		assertThat(profile.getUsername()).isEqualTo("profile_get_user");
		assertThat(profile.getNickname()).isEqualTo("测试");
		assertThat(profile.getGender()).isEqualTo(Gender.MALE);
	}

	@Test
	void should_update_profile_with_email_and_avatar_url() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("profile_email_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build();

		var userId = userService.register(registerDTO);

		var updateDTO = new UpdateProfileRequestDTO();
		updateDTO.setNickname("新昵称");
		updateDTO.setEmail("test@example.com");
		updateDTO.setAvatarUrl("https://example.com/avatar.png");

		userService.updateProfile(userId, updateDTO);

		// re-register to verify update — user already exists, so register would fail
		// instead, verify via JWT claims after re-login
		var loginVO = userService.login(LoginRequestDTO.builder()
			.username("profile_email_user")
			.password("Passw0rd!")
			.build());

		assertThat(loginVO.getUsername()).isEqualTo("profile_email_user");
		// getProfile to verify email and avatarUrl
		var profile = userService.getProfile(userId);
		assertThat(profile.getEmail()).isEqualTo("test@example.com");
		assertThat(profile.getAvatarUrl()).isEqualTo("https://example.com/avatar.png");
	}

	@Test
	void should_throw_when_register_duplicate_username() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("duplicate_user")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build();

		userService.register(registerDTO);

		var duplicate = RegisterRequestDTO.builder()
			.username("duplicate_user")
			.password("Other1@3!")
			.confirmPassword("Other1@3!")
			.nickname("其他")
			.gender(Gender.FEMALE)
			.build();

		assertThatThrownBy(() -> userService.register(duplicate))
			.isInstanceOf(BusinessException.class)
			.hasMessage("用户名已存在");
	}

}
