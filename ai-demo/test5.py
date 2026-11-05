from openai import OpenAI

def calculator(a: float, b: float, op: str) -> str:
    if op == "+":
        res = a + b
    elif op == "-":
        res = a - b
    elif op == "*":
        res = a * b
    elif op == "/":
        if b == 0:
            return "错误：除数不能为0"
        res = a / b
    else:
        return f"错误：不支持运算符{op}"
    return f"计算结果：{a} {op} {b} = {res}"
    
function_map = {
    "calculator": calculator
}

tools = [
    {
        "type": "function",
        "function": {
            "name": "calculator",
            "description": "用于两个数字的加减乘除四则运算，用户计算",
            "parameters": {
                "type": "object",
                "properties": {
                    "a": {
                        "type": "number",
                        "description": "第一个运算数字"
                    },
                    "b": {
                        "type": "number",
                        "description": "第二个运算数字"
                    },
                    "op": {
                        "type": "string",
                        "description": "运算符，仅支持+、-、*、/"
                    },
                },
            },
            "required": ["a", "b", "op"]
        }
    }
]

client = OpenAI(
   api_key=os.getenv('DEEPSEEK_API_KEY'),
   base_url='https://api.deepseek.com/v1'
)

def run_chat(user_query: str):
        message = [{"role": "user", "content": prompt}]
        response = deepseek_client.chat.completions.create(
            model="deepseek-flash",
            messages=messages,
            tools=tools,
            tool_choice="auto",
        )
        msg = response.choices[0].message
        # 判断：模型是否需要调用函数 ======
        if msg.tool_calls:
            print("===== 模型触发函数调用 =====")
            # 遍历所有待调用的函数（支持多函数调用）
            for tool_call in msg.tool_calls:
                func_name = tool_call.function.name
                # 解析模型返回的 JSON 参数
                import json
                func_args = json.loads(tool_call.function.arguments)
                print(f"调用函数：{func_name}，入参：{func_args}")

            # 2. 本地执行函数
            func = function_map[func_name]
            result = func(**func_args)
            print(f"函数执行结果：{result}")
            messages.append(msg)
            messages.append({
                "role": "tool",
                "tool_call_id": tool_call_id,
                "content": result
            })

        # 4. 二次请求：将函数结果回传给模型，生成最终自然语言回答
        final_response = client.chat.completions.create(
            model="deepseek-chat",
            messages=messages
        )
        final_answer = final_response.choices[0].message.content
        print("\n==== 模型最终回复 =====")
        print(final_answer)
    else:
        # 无需调用函数，直接返回回答
        print("===== 无需调用工具，直接回复 =====")
        print(msg.content)

# ==================== 5. 测试运行 ====================
if __name__ == "__main__":
    # 测试1：加法运算
    run_chat("帮我算一下 123 加 456 等于多少")
    print("-" * 50)
    # 测试2：除法运算
    run_chat("78除以6是多少")
    print("-" * 50)
    run_chat("今天天气怎么样？")
