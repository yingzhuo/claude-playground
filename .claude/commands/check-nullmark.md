---
description: 空值检查实践
---

## 确保空值检查元注释被正确使用

- 确保每个package都有`package-info.java`文件并使用`org.jspecify.annotations.NullMarked`元注释
- 确保 `org.jspecify.annotations.Nullable` 被使用, 不使用其他变体
