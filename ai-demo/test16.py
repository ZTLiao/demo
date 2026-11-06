from langchain.agents.middleware import agent_middleware

@agent_middleware
def log_middleware(next, *args, **kwargs):
    """简单日志中间件：记录每次模型调用的开始与结束---"""
    print("=== 模型调用开始 ===")
    result = next(*args, **kwargs)
    print("=== 模型调用结束 ===")
    return result

# 挂载到 Agent
agent = create_agent(
    model=llm,
    tools=[get_weather],
    middleware=[log_middleware]
)