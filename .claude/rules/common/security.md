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
