from dotenv import load_dotenv
import os
from openai import OpenAI
import chromadb
from chromadb.config import Settings
import json

load_dotenv()

WORKSPACE_ID=os.getenv('WORKSPACE_ID')

embedding_client = OpenAI(
    api_key=os.getenv('DASHSCOPE_API_KEY'),
    base_url='https://dashscope.aliyuncs.com/compatible-mode/v1'
)

dashscope_client = OpenAI(
    api_key=os.getenv('DASHSCOPE_API_KEY'),
    base_url=f'https://{WORKSPACE_ID}.cn-beijing.maas.aliyuncs.com/compatible-mode/v1'
)

deepseek_client = OpenAI(
    api_key=os.getenv('DEEPSEEK_API_KEY'),
    base_url='https://api.deepseek.com'
)

def get_embeddings_batch(texts, model="text-embedding-v3", batch_size=10):
    all_embeddings = []
    for i in range(0, len(texts), batch_size):
        batch = texts[i:i + batch_size]
        data = embedding_client.embeddings.create(input=batch, model=model).data
        all_embeddings.extend([x.embedding for x in data])
    return all_embeddings

def rerank_documents(query, documents, model="qwen3-rerank", top_n=3):
    response = dashscope_client.post(
        '/reranks',
        body={
            'model': model,
            'query': query,
            'documents': documents,
            'top_n': top_n
        },
        cast_to=object
    )

    sorted_docs = [documents[item['index']] for item in response['results']]
    return sorted_docs

def get_completion(prompt, model="deepseek-flash"):
    message = [{"role": "user", "content": prompt}]
    response = deepseek_client.chat.completions.create(
        model=model,
        messages=messages,
        temperature=0,
    )
    return response.choices[0].message.content

class VectorDB:
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

class RAG_Bot:
    def __init__(self, vector_db, llm_fn, rerank_fn, recall_k=20, top_n=3):
        self.vector_db = vector_db
        self.llm_fn = llm_fn
        self.rerank_fn = rerank_fn
        self.recall_k = recall_k
        self.top_n = top_n

        self.prompt_template = """
        你是专业的问助手。
        请严格根据下面的【已知信息】回答用户问题。
        如果已知信息中没有答案，请直接回答“抱歉，我无法回答这个问题”。
        禁止编造信息，禁止使用已知信息以外的知识。

        【已知信息】
        {context}

        【用户问题】
        {query}

        请用中文简洁回答：
        """

    def chat(self, user_query):
        recall_docs = self.vector_db.search(user_query, top_n=self.recall_k)
        print(f"recall_docs:{recall_docs}")
        ranked_docs = self.rerank_fn(user_query, recall_docs, top_n=self.top_n)
        context = "\n---\n".join(ranked_docs)
        print(f'context:{context}')
        prompt = self.prompt_template.format(context=context, query=user_query)
        answer = self.llm_fn(prompt)
        return answer


if __name__ == '__main__':
    with open('train_zh.json', 'r', encoding='utf-8') as f:
        data = [json.loads(line) for line in f]

    instructions = [entry['instructions'] for entry in data[:300]]
    outputs = [entry['output'] for entry in data[:300]]

    vector_db = VectorDB('rag_qwen3_rerank', get_embeddings_batch)
    vector_db.add_documents(instructions, outputs)

    bot = RAG_Bot(
        vector_db=vector_db,
        llm_fn=get_completion,
        rerank_fn=rerank_documents,
        recall_k=10,
        top_n=3
    )

    user_query = "得了白癫风怎么办？"
    response = bot.chat(user_query)
    print(f"用户：{user_query}")