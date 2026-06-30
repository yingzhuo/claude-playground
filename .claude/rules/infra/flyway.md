---
description: 数据迁移
paths: "**/db/migration/*.sql"
---

# 数据迁移

- flyway 迁移文件放在 `主程序` 工程
    - 版本化迁移使用 `CREATE TABLE IF NOT EXISTS t_xxx`，禁止使用 `DROP TABLE IF EXISTS`
    - 不要添加版权声明
    - 在所有SQL正文前加入 `-- @formatter:off`
    - 在所有SQL正文后加入 `-- @formatter:on`
    - SQL中一律使用半角标点符号
