from langchain.agents.middleware import HumanInTheLoopMiddleware

agent = create_agent(
    model=llm,
    tools=[calculate, delete_file],  # delete_file 为模拟的高危工具
    middleware=[
        HumanInTheLoopMiddleware(
            # 配置需要人工审批的工具名称
            approve_tools=["delete_file"]
        )
    ]
)