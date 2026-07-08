---
description: Swagger / OpenAPI 规范
paths: "**/*.java"
---

# Swagger

## DTO / VO / BO

- 每个字段必须通过 `@Schema` 指明必要性、最小长度、最大长度
- `@Schema` 禁止使用已弃用的 `required` 属性，改用 `requiredMode`

### 字段校验规则

- 所有 String 类型的字段必须同时具备：
    - `jakarta.validation` 校验注解（`@NotBlank` / `@Size` / `@Email` 等）
    - `@Schema` 声明 `minLength` 和 `maxLength`
- 必填 String 字段：`@NotBlank` + `@Size` + `@Schema(requiredMode = RequiredMode.REQUIRED, minLength, maxLength)`
- 可选 String 字段：`@Nullable` + `@Size(max = ...)` + `@Schema(maxLength = ...)`
- 邮件地址：`@Email` + `@Size(max = ...)`
- URL 地址：`@URL`（`org.hibernate.validator.constraints.URL`）+ `@Size(max = ...)`

## Controller

- 所有 RESTful 接口最外层返回类型必须是 `R`
- 类上加 `@Tag`，方法上加 `@Operation`
- Controller 方法无需 JavaDoc，改用 `@Operation` 说明接口功能

## Security-Swagger 集成

- 需要认证的接口加 `@MySecurityRequirement` 标明
- 从 SecurityContext 获取的参数（如 `@CurrentUserId`）加 `@HiddenParam`（实质为 `@Parameter(hidden = true)`）
