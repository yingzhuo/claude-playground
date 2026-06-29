#!/usr/bin/env bash
set -e

git status
git add .

if git diff --quiet && git diff --staged --quiet; then
    echo "[INFO] 没有检测到更改"
    echo "[INFO] 跳过提交"
    exit 0
fi

TIMESTAMP=$(date "+%F %T")
git commit -m "$TIMESTAMP"

REMOTES=$(git remote)
for remote in $REMOTES; do
    git push "$remote" HEAD
done
