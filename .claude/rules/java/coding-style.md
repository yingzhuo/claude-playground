---
description: 编码风格
paths: "**/*.java"
version: 1.0
---

# 编码风格

## 语言级别

- Java 21, 禁止使用 preview 特性(除非项目明确开启)
- 禁止 raw type,必须带泛型参数
- 禁止 `java.util.Date` / `java.util.Calendar`,统一 `java.time.*`
- 当不影响可读性时 局部变量优先使用 `var` 关键字
- 遵循 Google Java Style Guide(使用 google-java-format)
- 缩进 4 空格, 行宽 100
- 禁止使用 tab, 禁止尾随空格
- 导入顺序: static → java* → javax* → org* → com* → 其他
- 标记型接口要使用 `@FunctionalInterface`

## Jar 依赖

- 禁止直接引入未在 BOM 子项目中管理的第三方依赖

## 文档注释

- 所有公共方法(含 protected)必须写 JavaDoc,含 `@param` 和 `@return`
- JavaDoc 应描述"外部可见的行为"(什么场景触发,返回什么),而非"内部如何实现"
- 禁止在 JavaDoc 中记录修改历史,作者,日期(用 git)
- Controller 层的方法例外,不要 JavaDoc,用 Swagger `@Operation` 说明接口功能
- Controller Advice 的方法需要 JavaDoc,说明触发条件,异常类型和响应结构
- 功能简单的私有方法不要 JavaDoc
- `@Bean` 方法: JavaDoc 说明该 Bean 的用途和配置属性来源
- Service 接口方法必须 JavaDoc,实现类可以 `{@inheritDoc}` 复用
- DTO/VO/BO 字段: `@Schema` 已覆盖字段说明,不需额外 JavaDoc

## 命名

- 类名 UpperCamelCase,方法/变量 lowerCamelCase
- 常量 `UPPER_SNAKE_CASE`
- 禁止魔法值,提取为常量或枚举型

## Lombok

- 用 `@Slf4j`, `@RequiredArgsConstructor`, `@Getter`, `@Setter`
- `@Builder` 只在 DTO/VO/BO 用,Entity 用 `@Builder(toBuilder = true)` 或不建

## POJO

- DTO, VO, BO 不允许使用 Record
- DTO, VO, BO 等POJO 必须显示实现 `java.io.Serializable` 接口

## Entity (DB相关)

- 数据库相关实体类放在 `模型` 工程
- 需要放在不同逻辑模块的不同 package 下
- 使用 MyBatis/MyBatisPlus 相关描述数据库映射的元注释
- 使用 Lombok 元注释
- 每个property必须要有Java文档注释清晰标记具体用途
- MyBatisPlus 已配置了所有的表都以 "t_" 开头

## 返回值

- 集合返回 empty-list等,禁止返回 null
- 单值可能不存在 → Optional<T>, 但禁止字段类型用 Optional,禁止方法参数用 Optional<T>

## 避免NPE

- 确保每个package都有`package-info.java`, 使用`org.jspecify.annotations.NullMarked`元注释
- 确保 `org.jspecify.annotations.Nullable` 被使用, 不使用其他变体
- `org.jspecify.annotations.NonNull` 不要显示使用
