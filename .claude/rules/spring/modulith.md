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
