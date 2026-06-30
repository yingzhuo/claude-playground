---
description: Spring规约
paths: "**/*.java"
version: 1.0
---

# Spring规约

## 注入

- 尽量使用构造器注入, 尽量避免 `@Autowired` 字段注入, 尽量避免 `@Autowired` Setter注入
- 单构造器可省 `@Autowired`
- 禁止在 `@Configuration` 类里 `@Autowired` 另一个 `@Configuration`

## 逻辑分层

- 从上到下分为 `Controller` `Service` `Dao` 层
- 禁止同一层次相互注入

## Controller

- 统一错误处理类 `ControllerExceptionHandlers`
