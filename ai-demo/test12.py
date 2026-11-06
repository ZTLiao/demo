import os
from langchain.agents import create_agent
from langchain.core.tools import tool
from langchain_openai import ChatOpenAI

# 1. 初始化大模型（支持替换为 Anthropic、通义千问、文心一言等任意厂商模型）
llm = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0,
    api_key=os.getenv("OPENAI_API_KEY")
)

# 2. 自定义工具：使用 @tool 装饰器快速定义工具
@tool
def get_weather(city: str) -> str:
    """
    查询指定城市的实时天气
    参数：city: 城市名称，如“北京”、“上海”

    # 模拟天气查询，实际场景可对接第三方天气API
    return f"{city}今日天气：晴，气温25℃，风力3级，空气质量优"
    """

@tool
def calculate(expression: str) -> str:
    """
    执行数学计算，输入为标准数学表达式
    例如："123 * 45 + 789"

    """
    try:
        result = eval(expression)
        return f"计算结果：{result}"
    except Exception as e:
        return f"计算失败：{str(e)}"

# 3. 统一使用 create_agent 创建智能体
agent = create_agent(
    model="llm",                      # 核心大模型实例
    tools=[get_weather, calculate],   # 利用工具调用
    system_prompt="你是一个实用的生活助手，优先调用工具解决用户问题，回答简洁准确。"
)

# 4. 调用 Agent 执行任务
if __name__ == "__main__":
    # 直接调用示例
    response = agent.invoke({
        "messages": [
            {"role": "user", "content": "北京今天天气怎么样？能帮我算一下28x6乘以18等于多少？"}
        ]
    })

# 输出最终结果
print("Agent回答：", response["messages"][0]["content"])