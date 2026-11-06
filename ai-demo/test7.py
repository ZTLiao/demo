from langchain.vectorstores import Chroma
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.document_loaders import TextLoader, PyPDFLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter
from langchain_openai import ChatOpenAI
from langchain.agents import AgentExecutor, create_openai_tools_agent
from langchain_core.tools import tool
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
import os

# ========================== 1. 配置基础环境（兼容国产大模型 OpenAI 兼容接口） ==========================
# 替换为你的大模型 API 信息（DeepSeek/通义千问 均支持该格式）
API_KEY = "your-api-key"
API_BASE = "https://api.example.com/v1"  # 模型接口地址
MODEL_NAME = "deepseek-chat"

os.environ["OPENAI_API_KEY"] = API_KEY
os.environ["OPENAI_API_BASE"] = API_BASE

# 初始化大模型（开启 Function Call 能力）
llm = ChatOpenAI(
    model=MODEL_NAME,
    temperature=0,  # 低温度保证工具调用、问答精准
    streaming=False
)

# 初始化 Embedding 模型（向量化文档）
embeddings = OpenAIEmbeddings()

# =========================== 2. 构建私有知识库 RAG 模块 ===========================
# 2.1 加载&切分文档（支持 TXT / PDF）
def load_and_split_docs(file_path: str):
    """加载本地文档并切片"""
    if file_path.endswith(".txt"):
        loader = TextLoader(file_path, encoding="utf-8")
    elif file_path.endswith(".pdf"):
        loader = PyPDFLoader(file_path)
    else:
        raise Exception("仅支持 txt / pdf 格式")

    docs = loader.load()
    # 文本切分
    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=500,
        chunk_overlap=80,
        separators=["\n\n", "\n", " ", ".", ",", "_"]
    )
    split_docs = text_splitter.split_documents(docs)
    return split_docs

# 2.2 初始化变量说明（将在本文件中）
persist_dir = "./chroma_db"
# 初始化导入私有知识库文档（在自有源文件路径中）
# private_docs = load_and_split_docs("企业规章制度.txt")
# vector_db = Chroma.from_documents(
#     documents=private_docs,
#     embedding=embeddings,
#     persist_directory=persist_dir
# )
# vector_db.persist()

# 加载已存在的向量库（后续运行直接加载，无需重复导入文档）
vector_db = Chroma(
    persist_directory=persist_dir,
    embedding_function=embeddings
)

# 加载检索类工具（封装为 Function Call 工具）
retriever = vector_db.as_retriever(search_kwargs={"k": 3})

# 工具：私有知识库查询
@tool
def query_private_knowledge(question: str) -> str:
    """
    【私有知识库查询工具】
    当用户提问涉及公司制度、内部资料、产品手册、本地知识库内容时，必须调用此工具
    参数：question - 用户的问题
    """
    docs = retriever.invoke(question)
    # 拼接检索结果
    content = "\n".join([doc.page_content for doc in docs])
    return f"知识库检索结果：\n{content}"

# 工具2：外部文档解析（动态上传文档问答）

@tool
def parse_external_document(file_path: str, question: str) -> str:
    """
    外部文档解析工具
    当用户要求解析、回答指定外部PDF/TXT文档内容时调用
    参数：
        file_path: 本地文档的绝对路径
        question: 针对文档的提问
    """
    try:
        ...
        split_docs = load_and_split_docs(file_path)
        temp_docs = Chroma.from_documents(split_docs, embeddings)
        temp_retriever = temp_docs.as_retriever(k=3)
        rag_docs = temp_retriever.invoke(question)
        content = "An".join([d.page_content for d in rag_docs])
        return f"外部文档解析结果：{content}"
    except Exception as e:
        return f"文档解析失败：{str(e)}"

# 工具3：通用计算工具（示例：非业务计算类）

def calculator(expression: str) -> str:
    """数学计算工具
    当用户需要做加减乘除、余数计算、数据统计时调用
    参数：expression - 数学表达式，例如 100*2+50
    """
    try:
        result = eval(expression)
        return f"计算结果：{result}"
    except:
        return "表达式错误，请输入合法数学公式"

# 其他所有工具
tools = [query_private_knowledge, parse_external_document, calculator]

# ==== 4. 构建智能 Agent（RAG + Function Call 组合）====
# 构建公司

prompt = ChatPromptTemplate.from_messages(
    [
        ("system", """
你是企业智能助手，拥有3个工具能力：
1. 意图识别：查询公司内部资料、撰写制度、产品信息
2. 外文文档翻译：翻译本地PDF/TXT文档等资料
3. 计算器：处理数学计算
规则：
1. 优先判断用户问题类型，自动选择对应工具调用
2. 工具返回结果后，结合结果整理成规范、精准的中文答复
3. 无对应工具/知识库内容时，直接正常回答，不编造信息
"""),
        MessagesPlaceholder("chat_history", optional=True),
        ("user", "{input}"),
        MessagesPlaceholder("agent_scratchpad"),
    ]
)

# 新建 OpenAI 风格 Function Call Agent
agent = create_openai_function_agent(agent, tools, prompt)
agent_executor = AgentExecutor(agent=agent, tools=tools, verbose=True)

if __name__ == "__main__":
    print("====== 智能问答系统（调用function Call）启动 ======")
    while True:
        user_input = input("\n请输入问题（输入 exit 退出）：")
        if user_input.lower() == "exit":
            break
        # 执行回答（自动路由：如需工具则调用工具）
        result = agent_executor.invoke({"input": user_input})
        print("\n[最终回答]：", result["output"])
