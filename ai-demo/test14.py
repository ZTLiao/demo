from langchain.agents import create_agent
from langchain.agents.middleware import SummarizationMiddleware
from langchain_openai import ChatOpenAI

main_llm = ChatOpenAI(model="gpt-4o-mini", temperature=0)
summary_llm = ChatOpenAI(model="gpt-4o-mini", temperature=0)

agent = create_agent(
    model=main_llm,
    tools=[get_weather, calculate],
    system_prompt="你是一个生活助手",
    middleware=[
        SummarizationMiddleware(
            model=summary_llm,
            # V1.1+ 新参数：触发条件，支持按消息数、Token数、比例三种模式
            trigger=("tokens", 4000),   # 对话Token总数超过4000时触发摘要
            # V1.1+ 新参数：摘要后保留的最新消息数量
            keep=("messages", 20),
            summary_prompt="请将以下对话史浓缩为不超过100字的摘要，保留核心业务信息：\n{messages}"
        )
    ]
)