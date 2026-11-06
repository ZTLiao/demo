import requests
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.header import Header
from openpyxl import load_workbook, Workbook
from langchain_openai import ChatOpenAI
from langchain.agents import AgentExecutor, create_openai_tools_agent
from langchain_core.tools import tool
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

# ==================== 1. 全局配置 ====================
# 大模型配置
LLM_API_KEY = "你的大模型API_KEY"
LLM_API_BASE = "https://api.example.com/v1"
MODEL_NAME = "deepseek-chat"

# 天气API密钥
WEATHER_KEY = "你的和风天气KEY"

# 邮箱配置（QQ邮箱为例）
MAIL_SENDER = "xxx@qq.com"
MAIL_AUTH_CODE = "邮箱授权码"
MAIL_SMTP_SERVER = "smtp.qq.com"
MAIL_SMTP_PORT = 465
# 初始化大模型（Agent 核心大脑）
llm = ChatOpenAI(
    api_key=LLM_API_KEY,
    base_url=LLM_API_BASE,
    model=MODEL_NAME,
    temperature=0,
    timeout=20
)

# =============== 2. 统一工具集 (Function Call 能力底座) ===============
# 工具1: 计算器
@tool
def calculator(expression: str) -> str:
    """数学计算工具, 加减乘除等运算使用, 入参为数学表达式"""
    try:
        return f"计算结果: {eval(expression)}"
    except:
        return "表达式格式错误"

# 工具2: 天气查询
@tool
def get_weather(city: str) -> str:
    """天气查询工具, 查询指定城市实时天气"""
    url = f"https://devapi.qweather.com/v7/weather/now?location={city}&key={WEATHER_KEY}"
    try:
        res = requests.get(url, timeout=10).json()
        if res["code"] != "200":
            return "天气查询失败"
        now = res["now"]
        return f"{city}: {now['text']}, 温度{now['temp']}℃, 体感{now['feelsLike']}℃"
    except Exception as e:
        return f"接口异常: {str(e)}"

# =============== 2. 统一工具集（Function Call 能力底座） ===============
# 工具1：计算器
@tool
def calculator(expression: str) -> str:
    """数学计算工具，加减乘除等运算使用，入参为数学表达式"""
    try:
        return f"计算结果：{eval(expression)}"
    except:
        return "表达式格式错误"

# 工具3: Excel读写
@tool
def excel_op(file_path: str, op_type: str, data: str = "") -> str:
    """
    Excel操作工具
    :param file_path: 文件路径
    :param op_type: read 读取 / write 写入
    :param data: 写入数据，英文逗号分隔
    """
    try:
        if op_type == "read":
            wb = load_workbook(file_path)
            ws = wb.active
            rows = [" | ".join([str(c or "") for c in r]) for r in ws.iter_rows(values_only=True)]
            wb.close()
            return "表格内容：\n" + "\n".join(rows)
        elif op_type == "write":
            try:
                wb = load_workbook(file_path)
            except FileNotFoundError:
                wb = Workbook()
                ws = wb.active
                ws.append(data.split(","))
                wb.save(file_path)
                wb.close()
                return f"已写入数据：{data}"
            return "操作类型错误"
    except Exception as e:
        return f"表格操作异常：{str(e)}"

# 工具4：邮件发送
@tool
def send_mail(receiver: str, subject: str, body: str) -> str:
    """邮件发送工具，传入收件人、标题、正文"""
    try:
        msg = MIMEMultipart()
        msg["From"] = Header(MAIL_SENDER)
        msg["To"] = Header(receiver)
        msg["Subject"] = Header(subject, "utf-8")
        msg.attach(MIMEText(body, "plain", "utf-8"))
        smtp = smtplib.SMTP_SSL(MAIL_SMTP_SERVER, MAIL_SMTP_PORT)
        smtp.login(MAIL_SENDER, MAIL_AUTH_CODE)
        smtp.sendmail(MAIL_SENDER, receiver, msg.as_string())
        smtp.quit()
        return "邮件发送成功"
    except Exception as e:
        return f"邮件发送失败：{str(e)}"

all_tools = [calculator, get_weather, excel_op, send_mail]

# =================== 3. 构建通用 Agent 执行器 ===================
agent_prompt = ChatPromptTemplate.from_messages([
    ("system", """
你是全能智能个人助理与自动化机器人，拥有计算器、天气、表格、邮件四大工具。
工作规则：
1. 分析用户指令，自动选择对应工具，复杂任务分步调用多个工具
2. 工具执行完毕后，整合结果用自然语言回复
3. 严格按照工具要求传入参数，出错及时反馈
4. 可以自动串联多步任务，完成自动化流程
"""),
    MessagesPlaceholder("chat_history", optional=True),
    ("user", "{input}"),
    MessagesPlaceholder("agent_scratchpad")
])

# 创建标准 Function Call Agent
agent = create_openai_tools_agent(llm, all_tools, agent_prompt)
agent_executor = AgentExecutor(
    agent=agent,
    tools=all_tools,
    verbose=True,      # 开启日志，查看Agent思考+函数调用全过程
    handle_parsing_errors=True
)

# =================== 4. 模式一：交互式个人智能助理 ===================
def run_personal_assistant():
    print("===== 个人智能助理 已启动 =====")
    print("支持：计算、查天气、操作表格、发邮件、多步组合任务")
    print("输入 exit 退出\n")
    while True:
        user_input = input("请下达指令：")
        if user_input.lower() == "exit":
            break
            result = agent_executor.invoke({"input": user_input})
            print(f"\n助理回复：{result['output']}\n")

# =============== 5. 模式二：任务自动化机器人（预设流水线） ===============
def run_auto_robot():
    print("===== 自动化任务机器人 开始执行预设流程 =====")
    # 预设固定流水线任务：
    # 任务：查询杭州天气 + 计算 128*8 + 将结果写入表格 + 发送邮件汇报
    task = """
查询杭州今天的天气，计算 128 * 8，把两条结果写入 report.xlsx，
然后发送邮件到 test@qq.com，标题为【每日自动汇报】，正文附上所有结果
"""

    print("正在执行自动化任务...\n")
    result = agent_executor.invoke({"input": task})
    print(f"自动化任务执行完成：\n{result['output']}")

# ==================== 6. 程序入口 ====================
if __name__ == "__main__":
    print("请选择运行模式：")
    print("1 - 个人智能助理（交互对话）")
    print("2 - 自动化任务机器人（预设流程）")
    choice = input("输入 1 或 2 ")

    if choice == "1":
        run_personal_assistant()
    elif choice == "2":
        run_auto_robot()
    else:
        print("输入错误")
