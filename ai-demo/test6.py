from openai import OpenAI
import datetime
import json

# =========================== 1. 本地工具函数：时间/日期查询 ===========================
def get_datetime(query_type: str) -> str:  # usage
    """
    获取系统当前时间、日期、星期
    :param query_type: 查询类型，可选值: date(日期)、time(时间)、week(星期)、all(全部)
    :return: 格式化后的时间文本
    """

    now = datetime.datetime.now()
    week_map = {0: "星期一", 1: "星期二", 2: "星期三", 3: "星期四", 4: "星期五", 5: "星期六", 6: "星期日"}
    week = week_map[now.weekday()]

    if query_type == "date":
        return f"当前日期: {now.strftime('%Y-%m-%d')}"
    elif query_type == "time":
        return f"当前时间: {now.strftime('%H:%M:%S')}"
    elif query_type == "week":
        return f"今天是: {week}"
    elif query_type == "all":
        return f"当前日期: {now.strftime('%Y-%m-%d')}, 当前时间: {now.strftime('%H:%M:%S')}, 今天是{week}"
    else:
        return "不支持的查询类型"

# 函数名映射表
function_map = {
    "get_datetime": get_datetime
}

tools = [
{
	"type": "function",
	"function": {
		"name": "get_datetime",
		"description": "当用户需[?]当前日期、当前时间、星期几时调用此工具",
		"parameters": {
			"type": "object",
			"properties": {
				"query_type": {
					"type": "string",
					"description": "查询类型: date=日期, time=时间, week=星期, all=全部信息",
					"enum": ["date", "time", "week", "all"]  # 枚举限制可选值，约束模型入参
				}
			},
			"required": ["query_type"]  # 必传参数
		}
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

# ==================== 5. 测试用例 ====================
if __name__ == "__main__":
    # 测试1: 查当前时间
    chat_with_tools("现在几点了？")
    print("-" * 60)

    # 测试2: 查今天日期
    chat_with_tools("今天是几月几号？")
    print("-" * 60)

    # 测试3: 查星期
    chat_with_tools("今天星期几？")
    print("-" * 60)

    # 测试4: 一次性查询全部时间信息
    chat_with_tools("告诉我要完整的日期、时间和星期")
    print("-" * 60)

    # 测试5: 普通闲聊（不触发工具）
    chat_with_tools("你好呀")