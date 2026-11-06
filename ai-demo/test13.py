from langchain.agents import create_agent
from langchain.agents.middleware import SummarizationMiddleware
from langchain_openai import ChatOpenAI

# 主业务模型
main_llm = ChatOpenAI(model="gpt-4o-mini", temperature=0)
# 专用摘要模型（建议使用轻量模型降低成[?]
summary_llm = ChatOpenAI(model="gpt-4o-mini", temperature=0)

agent = create_agent(
    model=main_llm,
    tools=[get_weather, calculate],
    system_prompt="你是一个生活助手",
    middleware=[
        SummarizationMiddleware(
            model=summary_llm,
            # V1.0 参数：超过4000Token触发摘要
            max_tokens_before_summary=4000,
            # V1.0 参数：摘要后保留最近20条原始消息
            messages_to_keep=20,
            # 可选：自定义摘要提示词
            summary_prompt="请将以下对话历史浓缩为不超过100字的摘要，保留核心业务信息：\n{messages}"
        )
    ]
)