---
description: Spring规约
paths: "**/*.java"
---

# Spring规约

## 注入

- 尽量使用构造器注入, 尽量避免 `@Autowired` 字段注入, 尽量避免 `@Autowired` Setter注入
- 单构造器可省 `@Autowired`
- 禁止在 `@Configuration` 类里 `@Autowired` 另一个 `@Configuration`

## 逻辑分层

- 从上到下分为 `Controller` `Service` `Dao` 层
- 禁止同一层次相互注入
- 逻辑层中日志使用 `debug` 级别

## Controller

- 统一错误处理类 `ControllerExceptionHandlers`

## 定时调度任务

- 调度任务响应Bean日志统一使用 `debug` 级别

## 同名Bean

不同Spring Modulith模块中, 如果出现同名 `@Mapper` DAO Bean, 会导致 `@MapperScan` 扫描时 Bean name 冲突。

解决方法：在 `@Mapper` 旁加 `@Component("xxx")` 指定唯一 Bean name，Spring 的 `AnnotationBeanNameGenerator` 会优先使用
`@Component` 的 value 作为 Bean name。

```java
@Mapper
@Component("adminUserDao")  // Bean name 不会与 user 模块的 userDao 冲突
public interface UserDao extends BaseMapper<User> {
}
```
