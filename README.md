# claude-playground

Java应用程序骨架。请参考[CLAUDE.md](./CLAUDE.md)。

## 开发环境

- Claude Code 2.1.201
    - `sudo npm install -g @anthropic-ai/claude-code@latest` to upgrade CC
- deepseek-v4-pro

## 已安装 Skill

- [ponytail](https://github.com/DietrichGebert/ponytail)
- [superpowers](https://github.com/obra/superpowers)

## 已安装的 MCP

### @benborla29/mcp-server-mysql

```bash
# 安装
sudo npm install -g @benborla29/mcp-server-mysql
```

```bash
claude mcp add mysql-local \
  -e MYSQL_HOST="localhost" \
  -e MYSQL_PORT="3306" \
  -e MYSQL_USER="root" \
  -e MYSQL_PASS="root" \
  -e MYSQL_DB="claude_playground" \
  -e ALLOW_INSERT_OPERATION="false" \
  -e ALLOW_UPDATE_OPERATION="false" \
  -e ALLOW_DELETE_OPERATION="false" \
  --scope project \
  -- npx @benborla29/mcp-server-mysql
```

### @modelcontextprotocol/server-redis

```bash
sudo npm install -g @modelcontextprotocol/server-redis
```

```bash
claude mcp add redis-local \                                                                                                                                                                                                                     1 ↵
  -e REDIS_HOST="localhost" \
  -e REDIS_PORT="6379" \
  -e REDIS_USERNAME="mcp" \
  -e REDIS_PASSWORD="mcppass" \
  -e REDIS_DB="0" \
  --scope project \
  -- npx @modelcontextprotocol/server-redis
```

## License

- [Apache 2.0](./LICENSE.txt)
