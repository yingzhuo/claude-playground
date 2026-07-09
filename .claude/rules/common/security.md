---
description: 安全
paths: "**/*.java"
---

# 安全

## SQL 注入

- 禁止拼接 SQL，使用 MyBatis XML/Annotation/Criteria API

## 密码密钥

- 密码和密钥使用环境变量注入，禁止硬编码
- 禁止明文存储密码，使用 `PasswordEncoder` 比较

## 访问控制

### 获取安全上下文

- 使用 `io.github.yingzhuo.claude.security.annotation` 包下的注解：
    - `@CurrentUserId` / `@CurrentUsername` / `@CurrentRoles` — 获取安全上下文信息

### 鉴权配置

- 所有鉴权规则统一配置在 `ApplicationBootSecurity.java` 的 `authorizeHttpRequests` 块中
- 禁止在 Controller 方法上使用 `@Secured`、`@PreAuthorize` 等 Spring Security 方法级鉴权注解
- 鉴权规则按路径优先匹配，精确路径在前，通配在后：
    - `permitAll()` — 无需认证即可访问（如登录、注册）
    - `authenticated()` — 只需登录即可访问（如用户个人信息）
    - `hasRole("xxx")` — 需登录且拥有指定角色（如管理后台）
- 公共路径（如 `/admin/login`）显式声明 `permitAll()`，禁止依赖默认 `anyRequest().permitAll()`
