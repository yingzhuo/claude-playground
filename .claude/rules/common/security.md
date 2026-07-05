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

- 使用 `io.github.yingzhuo.claude.security.annotation` 包下的注解：
  - `@CurrentUserId` / `@CurrentUsername` / `@CurrentRoles` — 获取安全上下文信息
