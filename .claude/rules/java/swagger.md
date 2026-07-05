---
description: Swagger / OpenAPI 规范
paths: "**/*.java"
---

# Swagger

## DTO / VO / BO

- 每个字段必须通过 `@Schema` 指明必要性、最小长度、最大长度
- `@Schema` 禁止使用已弃用的 `required` 属性，改用 `requiredMode`

## Controller

- 所有 RESTful 接口最外层返回类型必须是 `R`
- 类上加 `@Tag`，方法上加 `@Operation`
- Controller 方法无需 JavaDoc，改用 `@Operation` 说明接口功能

## Security-Swagger 集成

- 需要认证的接口加 `@MySecurityRequirement` 标明
- 从 SecurityContext 获取的参数（如 `@CurrentUserId`）加 `@HiddenParam`（实质为 `@Parameter(hidden = true)`）
