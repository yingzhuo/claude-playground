---
description: 编码风格
paths: "**/*.java"
---

# 编码风格

## 语言级别

- Java 21, 禁止 preview 特性
- 禁止 raw type
- 禁止 `java.util.Date`/`Calendar`，统一 `java.time.*`
- 不影响可读性时局部变量优先 `var`
- 遵循 Google Java Style Guide（google-java-format）
- 缩进 4 空格，行宽 100，禁止 tab，禁止尾随空格
- 导入顺序：static → java → javax → org → com → 其他
- 标记型接口用 `@FunctionalInterface`

## 依赖

- 禁止直接引入不在 BOM 管理的第三方依赖

## 版权

- 禁止在源文件中添加版权声明或许可证头部注释
- 合规信息统一放在根目录 `LICENSE.txt`

## 文档注释

- 所有 public/protected 方法必须写 JavaDoc，含 `@param` 和 `@return`
- JavaDoc 描述外部可见行为，而非内部实现
- 禁止记录修改历史、作者、日期（用 git）
- Controller 方法例外，改用 `@Operation`（见 swagger 规范）
- Controller Advice 方法需要 JavaDoc，说明触发条件、异常类型和响应结构
- 功能简单的 private 方法无需 JavaDoc
- `@Bean` 方法需 JavaDoc 说明用途和配置来源
- Service 接口方法必须 JavaDoc，实现类可用 `{@inheritDoc}` 复用

## 命名

- 类名 UpperCamelCase，方法/变量 lowerCamelCase
- 常量 `UPPER_SNAKE_CASE`
- 禁止魔法值，提取为常量或枚举

## Lombok

- 使用 `@Slf4j`、`@RequiredArgsConstructor`、`@Getter`、`@Setter`
- `@Builder` 限 DTO/VO/BO；Entity 不建或用 `@Builder(toBuilder = true)`

## POJO

- DTO/VO/BO 禁止使用 Record
- Event 等简单不可变数据载体可以使用 Record
- 使用 Lombok 元注解

## Entity

- 放在 `模型` 工程
- 不同模块在不同 package 下
- 使用 MyBatis/MyBatisPlus 映射注解
- 每个 property 必须有 JavaDoc 说明用途
- MyBatisPlus 表前缀统一为 `t_`

## 返回值

- 集合返回 empty 集合，禁止返回 null
- 单值可能不存在 → `Optional<T>`，但禁止字段类型和方法参数用 Optional

## 避免 NPE

- 每个 package 必须含 `package-info.java`，使用 `@NullMarked`
- 使用 `org.jspecify.annotations.Nullable`，不使用其他变体
