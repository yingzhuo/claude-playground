---
description: 安全
paths: "**/*.java"
version: 1.0
---

# 安全

## SQL 注入

- SQL 语句必须使用 MyBatis XML/MyBatis Annotation/MyBatis Criteria API, 禁止拼接字符串

## 密码密钥

- 密码/密钥使用环境变量等, 禁止硬编码

## 密码持久化

- 数据禁止存储用户密码明文
- 密码比较时应实用 `org.springframework.security.crypto.password.PasswordEncoder`

## 访问控制

- 使用 `io.github.yingzhuo.claude.security.annotation` 包下的元注释进行访问控制(Controller / Service 层均可使用)
  - `@PermitAll` - 允许所有用户访问
  - `@DenyAll` - 拒绝所有用户访问
  - `@IsAuthenticated` - 要求用户已登录(认证通过)
  - `@IsFullyAuthenticated` - 要求完全认证(非 Remember-Me)
  - `@IsAnonymous` - 允许匿名用户访问
