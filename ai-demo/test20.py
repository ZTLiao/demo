llm = ChatOpenAI(
    model="qwen3.7-plus",#模型名称
    base_url="https://dashscope.aliyuncs.com/compatible-mode/v1",
    api_key=os.getenv('DASHSCOPE_API_KEY')
)

response = llm.invoke("你好，你是谁？")
print(response.content)