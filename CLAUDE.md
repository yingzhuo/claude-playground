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

- JDK 21
- Gradle 8.14+, 使用 Groovy DSL, 禁用 Kotlin DSL
- 默认使用 IntelliJ IDEA, 启用 Save Actions 自动格式化

## 核心技术栈

- SpringBoot 4.1.x: 核心框架
- SpringSecurity 4.1.x: Web安全框架
- SpringModulith 2.1.x: 逻辑模块化治理框架
- MyBatisPlus 3.5.x: ORM框架
- Swagger 2.2.x: API文档化工具

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

## Make 命令快捷方式

项目提供 makefile，可通过 make 快速执行常见操作：

- `make build` — 打包（跳过测试和检查）
- `make rebuild` — 清理并重新构建
- `make test` — 运行测试
- `make check` — 代码风格检查
- `make compile` — 编译
- `make update-dependencies` — 刷新依赖

## 我不喜欢的写法

- 不要在方法内写超过 80 行的逻辑
- 不要用 if-else 嵌套超过 3 层,优先卫语句或策略模式

## 项目负责人

- 应卓 [yingzhor@gmail.com](mailto:yingzhor@gmail.com)
