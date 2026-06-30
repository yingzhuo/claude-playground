---
description: swagger
paths: "**/*.java"
---

# swagger

## DTO / VO / BO

- 每个字段必须通过 `@Schema` 指明必要性,最小长度,最大长度
- 禁止使用 `@Schema` 的 `required` 属性(自 2.2.5 起已弃用),改用 `requiredMode`
    - 正确: `@Schema(requiredMode = Schema.RequiredMode.REQUIRED)`
    - 错误: `@Schema(required = true)`

## Controller

- 所有 RESTful 接口的最外层返回类型必须是 `R`
- 类型上使用 `@Tag`
- 方法上使用 `@Operation`
