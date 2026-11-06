from dotenv import load_dotenv
from langchain.messages import AIMessage, HumanMessage, SystemMessage
from langchain.chat_models import init_chat_model

load_dotenv()

llm = init_chat_model(
    model="deepseek:deepseek-chat",  #模型名称
)
# 手动构建对话历史
messages = [
    SystemMessage(content="你是一位翻译专家，将中文翻译成英文。"),
    HumanMessage(content="你好，世界"),
    AIMessage(content="hello, world"),
    HumanMessage(content="我正在写博客"),  # 当前问题
]

messages = [
    {"role": "system", "content": "你是一位翻译专家，将中文翻译成英文。"},
    {"role": "user", "content": "你好，世界"},
    {"role": "assistant", "content": "hello, world"},
    {"role": "user", "content": "我正在写博客"},
]

# 调用模型
response = llm.invoke(messages)
print(response.content)
# 输出: I'm writing a blog