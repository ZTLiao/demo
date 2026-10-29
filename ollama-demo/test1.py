# pip install allama

import ollama

response = ollama.chat(
    model='qwen2',
    messages=[{'role': 'user', 'content': '你好，请用一句话介绍你自己。'}],
    stream=True
)

for chunk in response:
    print(chunk['message']['content'], end='', flush=True)