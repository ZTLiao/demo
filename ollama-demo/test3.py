# pip install openai

from openai import OpenAI

client = OpenAI(
    base_url='http://localhost:11434/v1',
    api_key='ollama', # 必填不校验
)

response = client.chat.completions.create(
    model='deepseek-r1:1.5b',
    message=[
        {'role': 'user', 'content': '你好，请用一句话介绍自己。'}
    ]
)

print(response.choices[0].message.content)