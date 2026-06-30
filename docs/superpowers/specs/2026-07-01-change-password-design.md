# 用户修改密码功能 - 设计文档

## 概述

为用户提供修改密码功能，用户在登录状态下通过提供旧密码和新密码来更新自己的密码。

## 接口设计

```
POST /user/password
Content-Type: application/json
Authorization: Bearer <jwt> 或 X-Auth-Token: <token>

请求体:
{
  "oldPassword": "当前密码",
  "newPassword": "新密码 (8-32位, 必须包含字母+数字+特殊字符)"
}

成功响应: 200 OK
{
  "success": true,
  "code": "200",
  "message": "OK"
}

错误响应: 400 Bad Request
{
  "success": false,
  "code": "400",
  "message": "错误描述"
}
```

## 数据结构

### ChangePasswordRequestDto

```java
@Data
public class ChangePasswordRequestDto {
    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min = 8, max = 32)
    private String newPassword;
}
```

## 组件设计

### UserController（新建）

- 位置: `projects-app/core/src/main/java/.../core/m/user/controller/UserController.java`
- 注解: `@RestController`, `@RequestMapping("/user")`
- 方法: `changePassword(@Valid @RequestBody ChangePasswordRequestDto, JwtInfo jwtInfo)`
- 安全: `@IsAuthenticated`
- 依赖: `UserService`

### UserService 新增方法

```java
void changePassword(String userId, String oldPassword, String newPassword);
```

### UserServiceImpl 实现逻辑

1. 根据 `userId` 查询用户
2. 若用户不存在 → 抛异常
3. 校验 `oldPassword` 是否匹配（`passwordEncoder.matches(oldPassword, user.getPassword())`）→ 不匹配则抛异常
4. 校验 `oldPassword` 与 `newPassword` 不相同 → 相同则抛异常
5. 校验 `newPassword` 复杂度（正则: `^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':"\\\\|,.<>\\/?]).{8,32}$`）→ 不满足则抛异常
6. `passwordEncoder.encode(newPassword)` → `user.setPassword(password)` → `userDao.updateById(user)`
7. 不返回数据，仅返回 `R.ok()`

### 当前用户身份获取

通过 `JwtInfo` 参数注入获取当前用户 ID：
- `JwtInfo` 实现了 `Authentication` 接口
- 包含 `getUserId()` 和 `getUsername()` 方法
- 作为 Controller 方法参数自动注入

### 密码复杂度规则

- 长度: 8-32 位
- 必须包含至少一个字母（大小写均可）
- 必须包含至少一个数字
- 必须包含至少一个特殊字符

## 错误处理

| 场景 | HTTP 状态 | 错误消息 |
|------|-----------|---------|
| 参数校验不通过 | 400 (由全局异常处理器处理) | 框架默认消息 |
| 旧密码错误 | 400 | "旧密码错误" |
| 新旧密码相同 | 400 | "新密码不能与旧密码相同" |
| 密码复杂度不符合要求 | 400 | "密码必须包含字母、数字和特殊字符" |
| 用户不存在 | 400 | "用户不存在" |
| 未携带token或无权限 | 401/403 | (由 SecurityExceptionHandler 处理) |

## Token 策略

修改密码后当前 JWT token 保持有效，无需重新登录。

## 涉及文件清单

| 操作 | 文件 | 说明 |
|------|------|------|
| 新建 | `UserController.java` | 新增控制器 |
| 新建 | `ChangePasswordRequestDto.java` | 请求 DTO |
| 修改 | `UserService.java` | 新增 changePassword 方法声明 |
| 修改 | `UserServiceImpl.java` | 新增 changePassword 方法实现 |

## 无需变更的部分

- 数据库: 现有 `t_user.password` 列（varchar(64)）足以存储 BCrypt 输出
- 安全配置: 利用现有 `@IsAuthenticated` 和 JWT 过滤器链，无需修改
- 全局异常处理: 已有 `ControllerExceptionHandlers` 处理参数校验异常
