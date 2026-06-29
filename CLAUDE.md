# 项目名称

claude-playground

## 项目概述

这是一个骨架项目, 旨在帮助开发者快速搭建一个Java项目, 使用Gradle进行构建

## 沟通习惯

- 回答问题时优先给出具体代码示例, 结论先行, 避免长篇大论
- 回答问题不要谄媚, 不要使用 "当然可以" "你想得很对" 之类的客套话
- 重构时请保留原有测试用例, 必要时新增
- 如果需要修改 Gradle 配置, 先说明原因

## 基础环境

- JDK 21, 优先使用用 Pattern Matching、Text Blocks 等现代语法, 但不允许使用 Records
- Gradle 8.14+, 使用 Groovy DSL, 禁用 Kotlin DSL
- 默认使用 IntelliJ IDEA, 启用 Save Actions 自动格式化

## 编码风格

- 遵循 Google Java Style Guide（使用 google-java-format）
- 缩进 4 空格, 行宽 100
- 禁止使用 tab, 禁止尾随空格
- 导入顺序：static → java* → javax* → org* → com* → 其他

## 核心技术栈

- SpringBoot: 核心框架
- SpringSecurity: Web安全框架
- SpringModulith: 逻辑模块化治理框架
- MyBatisPlus: ORM框架

## 项目结构及具体要求

```txt
+--- Project ':project-bom' - 物料清单(BOM)
+--- Project ':projects-app'
|    \--- Project ':projects-app:core' - 主程序
\--- Project ':projects-lib'
     +--- Project ':projects-lib:addon-common' - 通用工具及杂项
     +--- Project ':projects-lib:addon-model' - 模型
     \--- Project ':projects-lib:addon-security' - 安全相关组件
```

有具体要求如下:

- 逻辑模块化治理非常重要
- 数据库相关实体类放在 `模型` 工程
    - 需要放在不同逻辑模块的不同 package 下
    - 使用 MyBatis/MyBatisPlus 相关描述数据库映射的元注释
    - 使用 Lombok 元注释
    - 每个property必须要有Java文档注释清晰标记具体用途
    - MyBatisPlus 已配置了所有的表都以 "t_" 开头
- Swagger具体要求

- Spring `Controller` 层具体要求
    - 所有Restful接口, 最外层模型是 `io.github.yingzhuo.claude.model.webmvc.R`

## Make 命令快捷方式

项目提供 makefile，可通过 make 快速执行常见操作：

- `make build` — 打包（跳过测试和检查）
- `make rebuild` — 清理并重新构建
- `make test` — 运行测试
- `make check` — 代码风格检查
- `make compile` — 编译
- `make update-dependencies` — 刷新依赖

## 我喜欢的写法

- 禁止直接引入未在 BOM 子项目中管理的第三方依赖
- 使用 Lombok @Slf4j, @Data, @Builder
- 数据库访问用 MyBatis-Plus, 不使用 Spring Data JPA
- 单元测试用 JUnit 5 + AssertJ + Mockito,不使用 TestNG

## 我不喜欢的写法

- 不要在方法内写超过 80 行的逻辑
- 不要用 if-else 嵌套超过 3 层,优先卫语句或策略模式
- 不要写魔法数字,定义常量或用枚举
