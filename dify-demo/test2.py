import requests
import json

# 1. 设置 API URL 和 密钥
url = "https://api.dify.ai/v1/chat-messages"
api_key = "app-xxxxxxxxxxxxxxxxxxxx"  # 请替换为您在 Dify 后台获取的真实 API Key

headers = {
    "Authorization": f"Bearer {api_key}",
    "Content-Type": "application/json"
}

# 2. 构造请求 Payload
payload = {
    "inputs": {
        # 这里严格对应您截图中的 4 个必填变量
        "subject": "机器学习",
        "student_level": "大学",
        "language": "en",
        "explain_style": "大白话"
    },
    "query": "请帮我解释一下什么是监督学习？", # 用户的提问
    "response_mode": "blocking", # 阻塞模式 (直接返回结果)；如果是流式输出请改为 "streaming"
    "user": "user-123456",       # 用户标识，建议每次请求保持唯一
    "conversation_id": ""        # 留空表示开启新对话，传入历史ID可保持上下文
}

# 3. 发送请求
try:
    response = requests.post(url, headers=headers, json=payload)
    response.raise_for_status() # 检查 HTTP 状态码

    result = response.json()

    # 4. 解析并打印结果
    print("--- 请求成功 ---")
    print(f"会话 ID: {result.get('conversation_id')}")
    print(f"回答内容:\n{result.get('answer')}")

except requests.exceptions.RequestException as e:
    print(f"--- 请求失败 ---")
    print(e)
    if 'response' in locals():
        print(response.text)