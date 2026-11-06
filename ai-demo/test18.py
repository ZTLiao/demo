response = agent.invoke({"messages": [{"role": "user", "content": "你好"}]})
last_msg = response["messages"][-1]

# 统一消息内容，兼容所有模型厂商
for block in last_msg.content_blocks:
    if block.type == "text":
        print("文本内容：", block.text)
    elif block.type == "reasoning":
        print("推理过程：", block.content)