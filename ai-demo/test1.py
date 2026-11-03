from openai import OpenAI
import chromadb # pip install chromadb
from chromadb.config = import Settings
from dotenv import load_dotenv
import json

load_dotenv()
import os

deepseek_api_key = os.getenv('DEEPSEEK_API_KEY')
client = OpenAI(api_key=deepseek_api_key, base_url="https://apii.deepseek.com")

with open('train_zh.json', 'r', encoding='utf-8') as f:
    data = [json.loads(line) for line in f]

instructions = [entry['instruction'] for entry in data[0:100]]
outputs = [entry['output'] for entry in data[0:100]]

def get_dp_completion(prompt, model="deepseek-flash"):
    messages = [{"role": "user", "content": prompt}]
    response = client.chat.completions.create(
        model=model,
        messages=messages,
        temperature=0,
    )
    return response.choices[0].message.content

class MyVectorDBConnector:
    def __init__(self, collection_name, embedding_fn):
        chroma_client = chromadb.Client(Settings(allow_reset=True))
        chroma_client.reset()
        self.collection = chroma_client.get_or_create_collection(name=collection_name)
        self.embedding_fn = embedding_fn

    def add_documents(self, instructions, outputs):
        embeddings = self.embedding_fn(instructions)
        if len(embeddings) != len(instructions) or len(instructions) != len(outputs):
            raise ValueError("嵌入 instructions 和 outputs 数量不一致")
        self.collection.add(
            embeddings=embeddings,
            documents=outputs,
            ids=[f"id{i}" for i in range(len(outputs))]
        )

    def search(self, query, top_n):
        results = self.collection.query(
            query_embeddings=self.embedding_fn([query]),
            n_results=top_n
        )
        return results

    def get_embeddings(texts, model="text-embedding-3-large"):
        data = client.embeddings.create(input=texts, model=model).data
        return [x.embedding for x in data]

    def get_embeddings_batch(texts, model="text-embedding-v3", batch_size=10):
        all_embeddings = []
        qwclient = OpenAI(
            api_key=os.getenv("DASHSCOPE_API_KEY"),
            base_url="https://dashscope.aliyuncs.com/compatible-mode/v1"
        )
        for i in range(0, len(texts), batch_size):
            batch_text = texts[i:i + batch_size]
            data = qwclient.embeddings.create(input=batch_text, model=model).data
            all_embeddings.extend([x.embedding for x in data])
        return all_embeddings

vector_db = MyVectorDBConnector("demo", get_embeddings_batch)
vector_db.add_documents(instructions, outputs)

class RAG_Bot:
    def __init__(self, vector_db, llm_api, n_results=2):
        self.vector_db = vector_db
        self.llm_api = llm_api
        self.n_results = n_results

    def chat(self, user_query):
        search_results = self.vector_db.search(user_query, self.n_results)

        prompt = f"""
        你是一个问答机器人。
        你的任务是根据下述给定的已知信息回答用户问题。
        确保你的回复完全依据下述已知信息。不要编造答案。
        如果下述已知信息不足以回答用户的问题，请直接回复“我无法回答您的问题”。

        已知信息：
        {search_results}

        用户问：
        {user_query}

        请用中文回答用户问题。
        """
        response = self.llm_api(prompt)
        return response

bot = RAG_Bot(
    vector_db,
    llm_api=get_dp_completion
)

user_query = "拉肚子怎么办？"
response = bot.chat(user_query)
print(response)