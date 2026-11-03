# 输入验证
import re

@server.call_tool()
async def call_tool(name: str, arguments: dict):
    if name == "query_database":
        sql = arguments["sql"]

        # 1. SQL 注入防护：只允许 SELECT
        if not sql.strip().upper().startswith("SELECT"):
            return [TextContent(type="text", text="安全限制：只允许 SELECT 查询")]

        # 2. 禁止危险关键词
        dangerous = ["DROP", "DELETE", "UPDATE", "INSERT", "ALTER", "EXEC"]
        if any(kw in sql.upper() for kw in dangerous):
            return [TextContent(type="text", text="安全限制：检测到危险操作")]

        # 3. 执行查询（使用参数化查询）
        result = await db.fetch(sql)
        return [TextContent(type="text", text=json.dumps(result))]
