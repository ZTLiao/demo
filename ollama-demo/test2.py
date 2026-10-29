import requests
import json

url = 'http://localhost:11434/api/generate'

data = {
    'model': 'deepseek-r1:1.5b',
    'prompt': '你好，请用一句话介绍你自己。',
    'stream': False # 如果设为True，需要按行处理response. iter_line()
}

response = requests.post(url, json=data)

if response.status_code == 200:
    result = response.json()
    print(result.get('response'))
else:
    print(f'请求失败，状态码：{response.status_code}')