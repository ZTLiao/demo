import os
from http import HTTPStatus
from dashscope import Application
from dotenv import load_dotenv
load_dotenv()

response = Application.call(
    api_key=os.getenv("DASHSCOPE_API_KEY"),
    app_id=os.getenv("DASHSCOPE_APP_ID"),
    prompt='中文的embedding模型有哪些？'
)

if response.status_code != HTTPStatus.OK:
    print(f'request_id={response.request_id}')
    print(f'code={response.status_code}')
    print(f'message={response.message}')
