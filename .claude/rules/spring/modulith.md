---
description: 逻辑模块治理
paths: "**/*.java"
---

# 逻辑模块治理

## package 命名规范

- 如一个模块顶层为 `xxx` 包
    - `xxx.controller` 为Web暴露层
    - `xxx.dao` 为数据访问层接口和实现类
    - `xxx.service` 为服务层接口和实现类
    - `xxx.utility` 为模块内部使用utility类和MapStruct工具
    - `xxx.dto` 为模块内部使用DTO
    - `xxx.vo` 为模块内部使用VO
    - `xxx.bo` 为模块内部使用BO
    - `xxx.entity` 不需要, 实体类保存在专门的子工程
    - `xxx.task` 定时调度任务
    - `xxx.eventlistener` 事件监听器
    - `xxx.mapstruct` MapStruct转换器

## Event 规范

- Event 类统一放在 `addon-model` 模块的 `io.github.yingzhuo.claude.model.event` 包下
- Event 和 EventListener 必须成对命名：
    - `XxxEvent` — 事件类
    - `XxxEventListener` — 事件监听器（放在各业务模块的 `eventlistener` 包下）
- 简单不可变 Event 优先使用 Record
