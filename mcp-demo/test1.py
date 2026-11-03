# weather_server.py
import json
import httpx
from mcp.server import Server
from mcp.server.stdio import stdio_server
from mcp.types import Tool, TextContent, Resource

# 创建 MCP Server
server = Server("weather-server")

# ===== 定义 Tool =====
@server.list_tools()
async def list_tools():
    return [
        Tool(
            name="get_weather",
            description="获取指定城市的当前天气信息，包括温度、湿度、天气状况。",
            inputSchema={
                "type": "object",
                "properties": {
                    "city": {
                        "type": "string",
                        "description": "城市名称，如 '北京'、'Shanghai'"
                    }
                },
                "required": ["city"]
            }
        ),
        Tool(
            name="get_forecast",
            description="获取指定城市未来 3 天的天气预报。",
            inputSchema={
                "type": "object",
                "properties": {
                    "city": {"type": "string", "description": "城市名称"},
                    "days": {"type": "integer", "description": "预报天数，1-3", "default": 3}
                },
                "required": ["city"]
            }
        )
    ]

@server.call_tool()
async def call_tool(name: str, arguments: dict):
    if name == "get_weather":
        city = arguments["city"]
        # 实际项目中调用天气 API
        weather_data = {
            "city": city,
            "temperature": 22,
            "humidity": 65,
            "condition": "晴",
            "wind": "东南风 3级"
        }
        return [TextContent(
            type="text",
            text=json.dumps(weather_data, ensure_ascii=False)
        )]

    elif name == "get_forecast":
        city = arguments["city"]
        days = arguments.get("days", 3)
        forecast = [
            {"date": f"Day {i+1}", "high": 22+i, "low": 12+i, "condition": "晴"}
            for i in range(days)
        ]
        return [TextContent(
            type="text",
            text=json.dumps(forecast, ensure_ascii=False)
        )]

    raise ValueError(f"未知工具: {name}")

# ===== 定义 Resource =====
@server.list_resources()
async def list_resources():
    return [
        Resource(
            uri="weather://cities",
            name="支持的城市列表",
            description="当前支持查询天气的所有城市",
            mimeType="application/json"
        )
    ]

@server.read_resource()
async def read_resource(uri: str):
    if uri == "weather://cities":
        cities = ["北京", "上海", "广州", "深圳", "杭州", "成都"]
        return json.dumps(cities, ensure_ascii=False)
    raise ValueError(f"未知资源: {uri}")

# ===== 启动 Server =====
async def main():
    async with stdio_server() as (read_stream, write_stream):
        await server.run(read_stream, write_stream)

if __name__ == "__main__":
    import asyncio
    asyncio.run(main())
