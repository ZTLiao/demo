import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart
from email.header import Header
from openpyxl import load_workbook, Workbook
from langchain_openai import ChatOpenAI
from langchain.agents import AgentExecutor, create_openai_tools_agent
from langchain_core.tools import tool
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

# ------------------------- 1. 各项配置 -------------------------
# 1.1 配置 OpenAI（DeepSeek/阿里云/百度文心/通义千问等）
LLM_API_KEY = "你的大模型API_KEY"
LLM_API_BASE = "https://api.example.com/v1"
MODEL_NAME = "deepseek-chat"

# 2. 邮件配置（以 QQ 邮箱为例，开启 SMTP 服务，使用授权码登录）
MAIL_SENDER = "xxx@qq.com"
MAIL_AUTH_CODE = "邮箱SMTP授权码"
MAIL_SMTP_SERVER = "smtp.qq.com"
MAIL_SMTP_PORT = 465

# 3. 初始化大模型
llm = ChatOpenAI(
    api_key=LLM_API_KEY,
    base_url=LLM_API_BASE,
    model=MODEL_NAME,
    temperature=0,   # 办公场景要求结果稳定，温度设为0
    timeout=20
)

@tool

def excel_operate(file_path: str, operate_type: str, data: str = "", sheet_name: str = "Sheet1"):

    Excel表格操作工具，支持读取、写入数据
    使用场景：用户要求查看表格内容、行/列写入数据、新增数据行时调用
    :param file_path: Excel文件路径，后缀必须是.xlsx
    :param operate_type: 操作类型，可选值：read(读取) / write(写入)
    :param data: 写入的数据，多个单元格用英文逗号分隔，例如：张三,25,88
    :param sheet_name: 工作表名称，默认 Sheet1
    :return: 操作结果

    try:
        if operate_type == "read":
            wb = load_workbook(file_path)
            ws = wb[sheet_name]
            content = []
            # 读取前20行数据
            for row in ws.iter_rows(values_only=True):
                if any(cell is not None for cell in row):
                    content.append(" | ".join([str(c) if c else "" for c in row]))
            wb.close()
            return f"表格【{file_path}】内容：\n" + "\n".join(content)
        elif operate_type == "write":
            try:
                wb = load_workbook(file_path)
            except FileNotFoundError:
                # 文件不存在则新建
                wb = Workbook()
            ws = wb[sheet_name]
            row_data = data.split(",")
            ws.append(row_data)
            wb.save(file_path)
            wb.close()
            return f"已成功向 {file_path} 写入数据：{data}"
        else:
            return "操作类型错误，仅支持 read / write"
        except Exception as e:
            return f"表格操作失败：{str(e)}"

@tool
def excel_statistics(file_path: str, stat_type: str, col_index: int, sheet_name: str = "Sheet1"):
    """
    Excel数据统计工具，支持列求和、求平均值
    使用场景：用户要求统计数据表格数据、计算总分、平均分、合计时调用
    :param file_path: Excel文件路径
    :param stat_type: 统计类型，可选 sum(求和) / avg(平均值)
    :param col_index: 要统计的列序号，从1开始
    :param sheet_name: 工作表名称
    :return: 统计结果
    """
    try:
        wb = load_workbook(file_path)
        ws = wb[sheet_name]
        values = []
        # 跳过表头，从第二行开始统计
        for row in ws.iter_rows(min_row=2, values_only=True):
            cell_val = row[col_index - 1]
            if isinstance(cell_val, (int, float)):
                values.append(cell_val)
        wb.close()

        if not values:
            return "该列无有效数字数据"

        if stat_type == "sum":
            total = sum(values)
            return f"第{col_index}列数据求和结果: {total}"
        elif stat_type == "avg":
            avg = sum(values) / len(values)
            return f"第{col_index}列数据平均值: {avg:.2f}"
        else:
            return "统计类型错误, 仅支持 sum / avg"
    except Exception as e:
        return f"数据统计失败: {str(e)}"

def send_email(receiver: str, subject: str, body: str, attach_file: str = "") -> str:
    """
    邮件发送工具，支持纯文本正文 + 本地文件附件
    使用场景：用户要求发送邮件、推送报表、发送文件时调用
    :param receiver: 收件人邮箱，多个邮箱用英文分号;分隔
    :param subject: 邮件标题
    :param body: 邮件正文内容
    :param attach_file: 附件文件路径，为空则不添加附件
    :return: 发送结果
    """
    try:
        # 组装邮件
        msg = MIMEMultipart()
        msg["From"] = Header(MAIL_SENDER)
        msg["To"] = Header(receiver)
        msg["Subject"] = Header(subject, "utf-8")

        # 添加正文
        msg.attach(MIMEText(body, "plain", "utf-8"))

        # 添加附件
        if attach_file:
            with open(attach_file, "rb") as f:
                attach = MIMEText(f.read(), "base64", "utf-8")
                attach["Content-Type"] = "application/octet-stream"
                attach["Content-Disposition"] = f'attachment; filename="{attach_file}"'
                msg.attach(attach)

        # 连接SMTP服务器发送（SSL加密）
        smtp = smtplib.SMTP_SSL(MAIL_SMTP_SERVER, MAIL_SMTP_PORT)
        smtp.login(MAIL_SENDER, MAIL_AUTH_CODE)
        smtp.sendmail(MAIL_SENDER, receiver.split(";"), msg.as_string())
        smtp.quit()
        return f"邮件发送成功！收件人：{receiver}"
    except Exception as e:
        return f"邮件发送失败: {str(e)}"

# 注册所有办公工具
tools = [excel_operate, excel_statistics, send_email]

# =============== 3. 构建Agent 调度器 ===============
prompt = ChatPromptTemplate.from_messages([
    ("system", """
你是智能办公助手，可自动操作Excel表格、统计数据、发送邮件。
规则：
1. 查看表格/写入数据 → 调用 excel_operate
2. 计算表格求和、平均值 → 调用 excel_statistics
3. 发送邮件、推送报表文件 → 调用 send_email
4. 严格按照工具参数要求传参，列序号从1开始
5. 工具返回结果后，整理成清晰中文回复用户，不要返回原始代码/参数
6. 出现错误如实告知用户
"""),
    MessagesPlaceholder("chat_history", optional=True),
    ("user", "{input}"),
    MessagesPlaceholder("agent_scratchpad")
])

agent = create_openai_tools_agent(llm, tools, prompt)
agent_executor = AgentExecutor(
    agent=agent,
    tools=tools,
    verbose=True,
    handle_parsing_errors=True
)

# =========4. 交互测试入口 =========
if __name__ == "__main__":
    print("===== 自动化办公助手（表格+统计+邮件）启动 =====")
    print("指令示例：")
    print("1. 读取 data.xlsx 表格内容")
    print("2. 往 data.xlsx 写入 李四,22,92")
    print("3. 统计 data.xlsx 第3列求和")
    print("4. 发送邮件到 test@qq.com, 标题：月度报表，正文：附件为本月数据，附件：data.xlsx\n")

while True:
    user_input = input("请输入办公指令(输入 exit 退出)：")
    if user_input.lower() == "exit":
        break

    res = agent_executor.invoke({"input": user_input})
    print(f"\n[执行结果]：{res['output']}\n")