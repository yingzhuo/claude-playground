# 用户修改密码功能 - 实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 为用户提供修改密码功能，用户登录后通过旧密码验证来设置新密码

**Architecture:** 新增 `UserController` + `ChangePasswordRequestDto`，在 `UserService` 中新增 `changePassword` 方法。遵循现有 MVC 分层模式，使用 `@IsAuthenticated` 保护端点。

**Tech Stack:** Spring Boot 4.1.x, Spring Security 4.1.x, MyBatis-Plus 3.5.x, Spring Modulith 2.1.x

## 全局约束

- 用户模块为 CLOSED Spring Modulith 模块，新文件必须在 `io.github.yingzhuo.claude.core.m.user` 下
- 密码使用 BCrypt 编码（通过 `PasswordEncoder`）
- 所有公共方法必须写 JavaDoc
- Controller 方法使用 `@Operation`（Swagger）而非 JavaDoc
- DTO 字段使用 `@Schema` 描述
- 响应统一使用 `R` 类型
- 所有包必须有 `@NullMarked` 的 `package-info.java`
- `JwtInfo` 从 Controller 方法参数注入获取当前用户

---

### Task 1: 创建 ChangePasswordRequestDto

**文件:**
- 新建: `projects-app/core/src/main/java/io/github/yingzhuo/claude/core/m/user/controller/dto/ChangePasswordRequestDto.java`

**接口:**
- 消费: 无（独立 DTO）
- 产出: `ChangePasswordRequestDto` 包含 `oldPassword` 和 `newPassword` 字段

- [ ] **Step 1: 创建 ChangePasswordRequestDto**

```java
package io.github.yingzhuo.claude.core.m.user.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "修改密码请求")
public class ChangePasswordRequestDto implements Serializable {

    @NotBlank
    @Schema(description = "旧密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldPassword;

    @NotBlank
    @Size(min = 8, max = 32)
    @Schema(description = "新密码 (8-32位，必须包含字母、数字和特殊字符)", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 8, maxLength = 32)
    private String newPassword;
}
```

- [ ] **Step 2: 编译验证**

```bash
make compile
```
Expected: 编译通过

- [ ] **Step 3: 提交**

```bash
git add projects-app/core/src/main/java/io/github/yingzhuo/claude/core/m/user/controller/dto/ChangePasswordRequestDto.java
git commit -m "feat: 添加修改密码请求 DTO

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

### Task 2: 在 UserService 中添加 changePassword 方法

**文件:**
- 修改: `projects-app/core/src/main/java/io/github/yingzhuo/claude/core/m/user/service/UserService.java`

**接口:**
- 消费: 无
- 产出: `void changePassword(String userId, String oldPassword, String newPassword)` 方法声明

- [ ] **Step 1: 在 UserService 接口中新增方法**

在 `deleteById` 方法之后添加：

```java
/**
 * 修改用户密码
 *
 * @param userId      用户ID
 * @param oldPassword 旧密码
 * @param newPassword 新密码
 * @throws IllegalArgumentException 旧密码错误、新旧密码相同或密码复杂度不满足要求时抛出
 */
void changePassword(String userId, String oldPassword, String newPassword);
```

- [ ] **Step 2: 编译验证**

```bash
make compile
```
Expected: 编译失败（`UserServiceImpl` 未实现新方法）

- [ ] **Step 3: 提交**

```bash
git add projects-app/core/src/main/java/io/github/yingzhuo/claude/core/m/user/service/UserService.java
git commit -m "feat: 添加 changePassword 方法到 UserService 接口

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

### Task 3: 实现 UserService.changePassword

**文件:**
- 修改: `projects-app/core/src/main/java/io/github/yingzhuo/claude/core/m/user/service/UserServiceImpl.java`

**接口:**
- 消费: `UserService.changePassword` 接口定义（来自 Task 2）
- 产出: `changePassword` 方法的完整实现
- 注入依赖: `UserDao`, `PasswordEncoder`（`PasswordEncoder` 已在 `UserServiceImpl` 中 `@RequiredArgsConstructor` 注入）

- [ ] **Step 1: 注入 PasswordEncoder**

`UserServiceImpl` 当前构造器只有 `UserDao`。添加 `PasswordEncoder` 依赖：

```java
private final UserDao userDao;
private final PasswordEncoder passwordEncoder;
```

注意：`UserServiceImpl` 使用 `@RequiredArgsConstructor`，所以只需要声明 `private final` 字段即可自动注入。

- [ ] **Step 2: 实现 changePassword 方法**

在 `deleteById` 方法之后添加：

```java
private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,32}$";

private static final Pattern PASSWORD_REGEX = Pattern.compile(PASSWORD_PATTERN);

@Override
@Transactional
public void changePassword(String userId, String oldPassword, String newPassword) {
    var user = userDao.selectById(userId);
    if (user == null) {
        throw new IllegalArgumentException("用户不存在");
    }

    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
        throw new IllegalArgumentException("旧密码错误");
    }

    if (oldPassword.equals(newPassword)) {
        throw new IllegalArgumentException("新密码不能与旧密码相同");
    }

    var matcher = PASSWORD_REGEX.matcher(newPassword);
    if (!matcher.matches()) {
        throw new IllegalArgumentException("密码必须包含字母、数字和特殊字符");
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userDao.updateById(user);
}
```

添加 import：

```java
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.regex.Pattern;
```

- [ ] **Step 3: 编译验证**

```bash
make compile
```
Expected: 编译通过

- [ ] **Step 4: 提交**

```bash
git add projects-app/core/src/main/java/io/github/yingzhuo/claude/core/m/user/service/UserServiceImpl.java
git commit -m "feat: 实现 UserService.changePassword 方法

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

### Task 4: 创建 UserController

**文件:**
- 新建: `projects-app/core/src/main/java/io/github/yingzhuo/claude/core/m/user/controller/UserController.java`

**接口:**
- 消费: `UserService.changePassword`、`ChangePasswordRequestDto`、`JwtInfo`
- 产出: `POST /user/password` 端点

- [ ] **Step 1: 创建 UserController**

```java
package io.github.yingzhuo.claude.core.m.user.controller;

import io.github.yingzhuo.claude.core.m.user.controller.dto.ChangePasswordRequestDto;
import io.github.yingzhuo.claude.core.m.user.service.UserService;
import io.github.yingzhuo.claude.model.webmvc.R;
import io.github.yingzhuo.claude.security.annotation.IsAuthenticated;
import io.github.yingzhuo.claude.security.jwt.JwtInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 *
 * @author 应卓
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "用户信息", description = "用户信息相关接口")
public class UserController {

    private final UserService userService;

    @PostMapping("/password")
    @IsAuthenticated
    @Operation(summary = "修改密码", description = "用户修改自己的密码，需要提供旧密码进行验证")
    public R<?> changePassword(@RequestBody @Valid ChangePasswordRequestDto request, JwtInfo jwtInfo) {
        try {
            userService.changePassword(jwtInfo.getUserId(), request.getOldPassword(), request.getNewPassword());
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.error400(e.getMessage());
        }
    }
}
```

- [ ] **Step 2: 编译验证**

```bash
make compile
```
Expected: 编译通过

- [ ] **Step 3: 提交**

```bash
git add projects-app/core/src/main/java/io/github/yingzhuo/claude/core/m/user/controller/UserController.java
git commit -m "feat: 创建 UserController 并添加修改密码接口

Co-Authored-By: Claude <noreply@anthropic.com>"
```

---

### Task 5: 整体编译验证

- [ ] **Step 1: 完整编译**

```bash
make compile
```
Expected: 编译通过，无错误

- [ ] **Step 2: 打包验证**

```bash
make build
```
Expected: 打包成功
