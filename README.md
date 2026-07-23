# claude-playground

个人的练习项目，无生产意义，主要用来感受 “氛围编程”。<br>
请参考[CLAUDE.md](./CLAUDE.md)。

## ⚠️风险提示⚠️

工信部已发布风险提示，指出AI编程工具 Claude Code 存在严重安全后门隐患。<br>
根据工业和信息化部网络安全威胁和漏洞信息共享平台（NVDB）于2026年7月8日发布的紧急通报，具体情况如下：

- ‌风险详情‌：美国 [Anthropic](https://www.anthropic.com/)
  公司开发的 [Claude Code](https://claude.com/product/claude-code)
  被监测出内置监控机制，可在未经用户同意的情况下，向远程服务器回传用户地域、身份标识、设备唯一标识及部分代码片段等敏感信息。
- ‌受影响版本‌：‌`2.1.91` 至 `2.1.196` 版本 。
- 官方建议
    - 相关单位和用户立即开展全面排查。
    - 对于安装上述受影响版本的开发终端，‌立即卸载‌或升级至已清除后门代码的最新安全版本。
    - 加强核心业务网段内开发工具的外联权限管控与流量监测，防止敏感数据违规外传。

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
# 安装
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
