---
name: land
description: 合入主分支并清理，提交→推送→合并到 main→删除分支
---

# /land

## 步骤

1. **保存当前分支名**
   - 运行 `git branch --show-current`，记为 `$branch`
   - 如果当前在 `main` 或 `master`，提示"当前已在主分支，无需操作"并退出

2. **检查是否有未提交改动**
   - 运行 `git status --short`
   - 如果有改动，运行 `/ship` 先提交并推送

3. **拉取最新 main 并合并**
   - 运行 `git fetch origin main`
   - 运行 `git checkout main`
   - 运行 `git merge $branch --no-edit`

4. **推送 main**
   - 运行 `git push origin main`

5. **删除本地分支**
   - 运行 `git branch -d $branch`
   - 如果非强制删除失败，则使用 `-D` 强制删除

6. **删除远程分支**
   - 运行 `git push origin --delete $branch`

7. **输出结果**
   - 运行 `git branch --show-current`
   - 告知用户已合并并清理完毕，当前所在分支
