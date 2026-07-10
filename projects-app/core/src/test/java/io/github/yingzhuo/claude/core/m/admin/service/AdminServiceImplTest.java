package io.github.yingzhuo.claude.core.m.admin.service;

import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminChangePasswordRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminDeleteUserRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.AdminLoginRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.SetUserEnabledRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.controller.dto.UserListRequestDTO;
import io.github.yingzhuo.claude.core.m.admin.dao.AdminDao;
import io.github.yingzhuo.claude.core.m.user.controller.dto.LoginRequestDTO;
import io.github.yingzhuo.claude.core.m.user.controller.dto.RegisterRequestDTO;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.exception.BusinessException;
import io.github.yingzhuo.claude.model.admin.Admin;
import io.github.yingzhuo.claude.model.admin.AdminRole;
import io.github.yingzhuo.claude.model.user.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class AdminServiceImplTest {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private AdminDao adminDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		if (adminDao.selectById("test-admin-id") == null) {
			var admin = new Admin();
			admin.setId("test-admin-id");
			admin.setUsername("testadmin");
			admin.setPassword(passwordEncoder.encode("TestPass1!"));
			admin.setRole(AdminRole.SUPER);
			adminDao.insert(admin);
		}
	}

	@Test
	void should_login_as_super_admin() {
		var dto = AdminLoginRequestDTO.builder()
			.username("testadmin")
			.password("TestPass1!")
			.build();

		var vo = adminService.login(dto);
		assertThat(vo).isNotNull();
		assertThat(vo.getUsername()).isEqualTo("testadmin");
		assertThat(vo.getToken()).isNotBlank();
	}

	@Test
	void should_throw_when_login_with_wrong_password() {
		var dto = AdminLoginRequestDTO.builder()
			.username("testadmin")
			.password("wrong_password")
			.build();

		assertThatThrownBy(() -> adminService.login(dto))
			.isInstanceOf(BusinessException.class)
			.hasMessage("用户名或密码错误");
	}

	@Test
	void should_throw_when_login_with_non_existent_username() {
		var dto = AdminLoginRequestDTO.builder()
			.username("nonexistent")
			.password("somepassword")
			.build();

		assertThatThrownBy(() -> adminService.login(dto))
			.isInstanceOf(BusinessException.class)
			.hasMessage("用户名或密码错误");
	}

	@Test
	void should_change_own_password() {
		var dto = AdminChangePasswordRequestDTO.builder()
			.newPassword("NewPass1!")
			.confirmPassword("NewPass1!")
			.adminId(null)
			.build();

		adminService.changePassword("test-admin-id",
			java.util.List.of("ROLE_SUPER", "ROLE_ADMIN"), dto);
	}

	@Test
	void should_throw_when_change_password_mismatch() {
		var dto = AdminChangePasswordRequestDTO.builder()
			.newPassword("NewPass1!")
			.confirmPassword("Different1!")
			.adminId(null)
			.build();

		assertThatThrownBy(() -> adminService.changePassword(
			"test-admin-id",
			java.util.List.of("ROLE_SUPER", "ROLE_ADMIN"), dto))
			.isInstanceOf(BusinessException.class)
			.hasMessage("两次输入的密码不一致");
	}

	@Test
	void should_set_user_enabled() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("enable_test_user2")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build();

		var userId = userService.register(registerDTO);

		var disableDTO = new SetUserEnabledRequestDTO();
		disableDTO.setUserId(userId);
		disableDTO.setEnabled(false);

		adminService.setUserEnabled(disableDTO);

		assertThatThrownBy(() -> userService.login(LoginRequestDTO.builder()
			.username("enable_test_user2")
			.password("Passw0rd!")
			.build()))
			.isInstanceOf(BusinessException.class)
			.hasMessage("账户已被禁用");

		var enableDTO = new SetUserEnabledRequestDTO();
		enableDTO.setUserId(userId);
		enableDTO.setEnabled(true);

		adminService.setUserEnabled(enableDTO);

		var loginVO = userService.login(LoginRequestDTO.builder()
			.username("enable_test_user2")
			.password("Passw0rd!")
			.build());
		assertThat(loginVO).isNotNull();
	}

	@Test
	void should_delete_user() {
		var registerDTO = RegisterRequestDTO.builder()
			.username("delete_test_user2")
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build();

		var userId = userService.register(registerDTO);

		var deleteDTO = AdminDeleteUserRequestDTO.builder()
			.userId(userId)
			.password("TestPass1!")
			.build();

		adminService.deleteUser("test-admin-id", deleteDTO);

		assertThatThrownBy(() -> userService.login(LoginRequestDTO.builder()
			.username("delete_test_user2")
			.password("Passw0rd!")
			.build()))
			.isInstanceOf(BusinessException.class)
			.hasMessage("用户名或密码错误");
	}

	@Test
	void should_throw_when_delete_user_with_wrong_password() {
		var deleteDTO = AdminDeleteUserRequestDTO.builder()
			.userId("test-admin-id")
			.password("wrong_password")
			.build();

		assertThatThrownBy(() -> adminService.deleteUser("test-admin-id", deleteDTO))
			.isInstanceOf(BusinessException.class)
			.hasMessage("密码错误");
	}

	// --- listUsers ---

	@Test
	void should_list_users_with_default_pagination() {
		registerUser("ls_user_alpha");
		registerUser("ls_user_beta");

		var dto = UserListRequestDTO.builder().build();
		var result = adminService.listUsers(dto);

		assertThat(result.getPageNumber()).isEqualTo(1);
		assertThat(result.getPageSize()).isEqualTo(10);
		assertThat(result.getTotal()).isGreaterThanOrEqualTo(2);
		assertThat(result.getTotalPages()).isGreaterThanOrEqualTo(1);
		assertThat(result.getItems()).isNotEmpty();
	}

	@Test
	void should_list_users_with_custom_page_size() {
		for (int i = 0; i < 15; i++) {
			registerUser("ls_page_size_" + i);
		}

		var dto = UserListRequestDTO.builder().pageSize(10).pageNumber(1).build();
		var result = adminService.listUsers(dto);

		assertThat(result.getPageSize()).isEqualTo(10);
		assertThat(result.getItems()).hasSize(10);
		assertThat(result.getTotalPages()).isGreaterThanOrEqualTo(2);
	}

	@Test
	void should_list_users_filtered_by_search_key() {
		registerUser("ls_search_abc");
		registerUser("ls_search_def");
		registerUser("other_user");

		var dto = UserListRequestDTO.builder().searchKey("ls_search").build();
		var result = adminService.listUsers(dto);

		assertThat(result.getItems())
			.extracting("username")
			.allMatch(u -> ((String) u).contains("ls_search"));
	}

	@Test
	void should_return_empty_list_when_search_key_no_match() {
		var dto = UserListRequestDTO.builder().searchKey("__non_existent_user_xyz__").build();
		var result = adminService.listUsers(dto);

		assertThat(result.getItems()).isEmpty();
		assertThat(result.getTotal()).isZero();
	}

	@Test
	void should_return_all_users_when_search_key_is_null() {
		registerUser("ls_null_key_a");
		registerUser("ls_null_key_b");

		var dto = UserListRequestDTO.builder().searchKey(null).build();
		var result = adminService.listUsers(dto);

		assertThat(result.getItems()).isNotEmpty();
	}

	@Test
	void should_return_all_users_when_search_key_is_blank() {
		registerUser("ls_blank_key_a");
		registerUser("ls_blank_key_b");

		var dto = UserListRequestDTO.builder().searchKey("  ").build();
		var result = adminService.listUsers(dto);

		assertThat(result.getItems()).isNotEmpty();
	}

	@Test
	void should_list_users_on_second_page() {
		for (int i = 0; i < 15; i++) {
			registerUser("ls_page2_" + i);
		}

		var dto = UserListRequestDTO.builder().pageNumber(2).pageSize(10).build();
		var result = adminService.listUsers(dto);

		assertThat(result.getPageNumber()).isEqualTo(2);
		assertThat(result.getItems()).isNotEmpty();
	}

	private void registerUser(String username) {
		userService.register(RegisterRequestDTO.builder()
			.username(username)
			.password("Passw0rd!")
			.confirmPassword("Passw0rd!")
			.nickname("测试")
			.gender(Gender.MALE)
			.build());
	}
}
