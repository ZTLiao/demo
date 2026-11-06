from langchain.chat_models import init_chat_model
from dotenv import load_dotenv
load_dotenv()
import os

llm = init_chat_model(
    model="deepseek/deepseek-chat",  #模型名称
    model="qwen3.7-plus",           #模型名称
    model_provider="openai",        # 借用 openai 的函数路由
    openai_api_base="https://dashscope.aliyuncs.com/compatible-mode/v1",  # 阿里官方的 OpenAI 兼容接口
    openai_api_key=os.getenv("DASHSCOPE_API_KEY")
)

response = llm.invoke("你好，你是谁？") #和模型对话
print(response.content)