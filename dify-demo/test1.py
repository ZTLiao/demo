import requests

url = "https://api.dify.ai/v1/completion-messages"

headers = {
    "Authorization": "Bearer <token>",  # 替换成你的 API Key
    "Content-Type": "application/json",
}

data = {
    "inputs": {
        "city": "New York"
    },
    "query": "Summarize the following text: ...",
    "response_mode": "blocking",
    "user": "def-456",
}

response = requests.post(url, headers=headers, json=data)

print(response.status_code)
print(response.json())