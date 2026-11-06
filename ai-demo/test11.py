import requests
from langchain_openai import ChatOpenAI
from langchain.agents import AgentExecutor, create_openai_tools_agent
from langchain_core.tools import tool
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

# =============== 1. 全局配置（大模型 + 业务系统鉴权） ===============
# 大模型配置（支持 DeepSeek/通义千问 等 OpenAI 兼容接口）
LLM_API_KEY = "你的大模型API_KEY"
LLM_API_BASE = "https://api.example.com/v1"
MODEL_NAME = "deepseek-chat"

# =============== 企业内部业务系统鉴权（CRM / ERP 统一身份） ===============
BUSINESS_TOKEN = "company_erp_crm_2026_token"  # 业务系统访问令牌
HEADERS = {
    "Authorization": "f\"Bearer {BUSINESS_TOKEN}\"",
    "Content-Type": "application/json"
}

# 模拟企业内部接口根地址（替换为你公司真实内网接口）
CRM_BASE_URL = "http://127.0.0.1:8000/crm"
ERP_BASE_URL = "http://127.0.0.1:8000/erp"

# 初始化大模型
llm = ChatOpenAI(
    api_key=LLM_API_KEY,
    base_url=LLM_API_BASE,
    model=MODEL_NAME,
    temperature=0,  # 业务系统要求精准，禁止随机发散
    timeout=20
)

# ==================== 2、业务工具封装：CRM 客户管理工具集 ====================
@tool
def crm_query_customer(customer_name: str = "", customer_id: str = "") -> str:
    """
    CRM客户查询工具
    使用场景：查询客户信息、根据姓名/客户ID查找客户
    :param customer_name: 客户姓名，选填
    :param customer_id: 客户编号ID，选填
    """
    try:
        params = {}
        if customer_name:
            params["name"] = customer_name
        if customer_id:
            params["id"] = customer_id

        url = f"{CRM_BASE_URL}/customer/query"
        resp = requests.get(url, headers=HEADERS, params=params, timeout=10)
        resp.raise_for_status()
        data = resp.json()

        if not data.get("data"):
            return "未查询到对应客户信息"
        res = "【客户信息查询结果】\n"
        for item in data["data"]:
            res += f"客户ID: {item.get('id')}, 姓名: {item.get('name')}, 电话: {item.get("phone")}"
        return res
    except Exception as e:
        return f"CRM客户查询接口异常: {str(e)}"

@tool
def crm_add_customer(name: str, phone: str, dept: str) -> str:
    """
    CRM新增客户工具
    使用场景：录入新客户、添加客户资料
    :param name: 客户姓名
    :param phone: 客户联系电话
    :param dept: 客户所属部门/公司
    """
    try:
        url = f"{CRM_BASE_URL}/customer/add"
        body = {
            "name": name,
            "phone": phone,
            "dept": dept
        }
        resp = requests.post(url, headers=HEADERS, json=body, timeout=10)
        resp.raise_for_status()
        data = resp.json()
        if data.get("code") == 200:
            return f"新增客户成功，客户ID: {data.get('customer_id')}"
        else:
            return f"新增客户失败: {data.get('msg')}"
    except Exception as e:
        return f"CRM新增客户接口异常: {str(e)}"

@tool
def crm_query_follow_record(customer_id: str) -> str:
    """
    CRM客户跟进记录查询
    使用场景：查询某客户的历史沟通、拜访、跟进记录
    :param customer_id: 客户编号ID
    """
    try:
        url = f"{CRM_BASE_URL}/follow/record"
        params = {"customer_id": customer_id}
        resp = requests.get(url, headers=HEADERS, params=params, timeout=10)
        resp.raise_for_status()
        data = resp.json()
        if not data.get("data"):
            return "该客户暂无跟进记录"
        res = "【客户跟进记录】\n"
        for record in data["data"]:
            res += f"时间：{record['time']} | 内容：{record['content']}\n"
        return res
    except Exception as e:
        return f"跟进记录查询异常：{str(e)}"

# ==================== 3、业务工具封装：ERP 进销存/订单工具集 ====================
@tool
def erp_query_stock(goods_name: str = "", goods_id: str = "") -> str:
    """
    ERP库存查询工具
    使用场景：查询商品库存、商品数量、仓库存货
    :param goods_name: 商品名称，选填
    :param goods_id: 商品编号，选填
    """
    try:
        params = {}
        if goods_name:
            params["goods_name"] = goods_name
        if goods_id:
            params["goods_id"] = goods_id

        url = f"{ERP_BASE_URL}/stock/query"
        resp = requests.get(url, headers=HEADERS, params=params, timeout=10)
        resp.raise_for_status()
        data = resp.json()
        if not data.get("data"):
            return "未查询到对应商品库存"
        res = "【库存查询结果】\n"
        for item in data["data"]:
            res += f"商品ID: {item['id']}，名称: {item['name']}，当前库存: {item['stock']}"
        return res
    except Exception as e:
        return f"库存查询接口异常：{str(e)}"

@tool
def erp_query_order(order_id: str = "", customer_id: str = "") -> str:
    """
    ERP订单查询工具
    使用场景：根据订单号查订单、根据客户ID查该客户所有订单
    :param order_id: 订单编号，选项
    :param customer_id: 客户编号，选项
    """
    try:
        params = {}
        if order_id:
            params["order_id"] = order_id
        if customer_id:
            params["customer_id"] = customer_id

        url = f"{ERP_BASE_URL}/order/query"
        resp = requests.get(url, headers=HEADERS, params=params, timeout=10)
        resp.raise_for_status()
        data = resp.json()
        if not data.get("data"):
            return "未查询到相关订单"
        res = "【订单查询结果】\n"
        for order in data["data"]:
            res += (
                f"订单号：{order['order_id']}，客户ID：{order['customer_id']}，"
                f"金额：{order['amount']}，状态：{order['status']}\n"
            )
        return res
    except Exception as e:
        return f"订单查询接口异常: {str(e)}"

# =========== 4. 注册所有业务工具 ===========
business_tools = [
    crm_query_customer,
    crm_add_customer,
    crm_query_follow_record,
    erp_query_stock,
    erp_query_order
]

# =========== 5. 构建企业业务Agent ===========
system_prompt = ""
你是企业内部业务助手，可通过自然语言操作CRM客户系统、ERP进销存系统。
规则：
1. 查询/新增客户、查询跟进记录 → 使用 CRM 系列工具
2. 查询库存、查询订单 → 使用 ERP 系列工具
3. 复杂任务需要分步调用多个工具，例如：先查客户ID，再查该客户订单
4. 严格按照工具参数传参，接口返回数据后，整理成简洁中文回复员工
5. 接口报错如实提示，不编造业务数据

prompt = ChatPromptTemplate.from_messages([
    ("system", system_prompt),
    MessagesPlaceholder("chat_history", optional=True),
    ("user", "{input}"),
    MessagesPlaceholder("agent_scratchpad")
])

# 创建Agent与执行器
agent = create_openai_tools_agent(llm, business_tools, prompt)
agent_executor = AgentExecutor(
    agent=agent,
    tools=business_tools,
    verbose=True,
    handle_parsing_errors=True
)

# ==================== 6. 交互入口 ====================
if __name__ == "__main__":
    print("===== 企业CRM/ERP 自然语言操作助手 启动 =====")
    print("可用指令示例：")
    print("1. 查询客户 张三 的信息")
    print("2. 新增客户：李四，电话13800138000，部门市场部")
    print("3. 查询商品 笔记本电脑 的库存")
    print("4. 根据客户ID查询该客户所有订单")
    print("输入 exit 退出\n")

    while True:
        user_cmd = input("请输入业务操作指令：")
        if user_cmd.lower() == "exit":
            break
        result = agent_executor.invoke({"input": user_cmd})
        print(f"\n【业务执行结果】：{result['output']}\n")