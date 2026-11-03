import os
import json
import numpy as np
from dotenv import load_dotenv
from openai import OpenAI
from rank_bm25 import BM250kapi
import jieba

load_dotenv()

WORKSPACE_ID = os.getenv('WORKSPACE_ID')

embedding_client = OpenAI(
    api_key=os.getenv('DASHSCOPE_API_KEY'),
    base_url="https://dashscope.aliiyuncs.com/compatible-mode/v1"
)

dashscope_client = OpenAI(
    api_key=os.getenv('DASHSCOPE_API_KEY')
    base_url=f"https://{WORKSPACE_ID}.cn-beijing.maas.aliyuncs.com/compatible-mode/v1"
)

deepseek_client = OpenAI(
    api_key=os.getenv('DEEPSEEK_API_KEY'),
    base_url="https://api.deepseek.com"
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

class HybridRetriever:
    def __init__(self, instructions, outputs, embedding_fn, bm25_weight=0.5, dense_weight=0.5):
        self.instructions = instructions
        self.outputs = outputs
        self.embedding_fn = embedding_fn
        self.bm25_weight = bm25_weight
        self.dense_weight = dense_weight
        tokenized_corpus = [jieba.lcut(doc) for doc in instructions]
        self.bm25 = BM250kapi(tokenized_corpus)
        self.doc_embeddings = np.array(embedding_fn(instructions))

    def search(self, query, recall_k=20):
        tokenized_query = jieba.lcut(query)
        bm25_scores = np.array(self.bm25.get_scores(tokenized_query))
        query_embedding = np.array(get_embedding_batch([query])[0])
        dense_distances = np.linalg.norm(self.doc_embeddings - query_embedding, axis=1)
        bm25_max = np.max(bm25_scores)
        bm25_norm = bm25_scores / bm25_max if bm25_max != 0 else bm25_scores
        dense_max = np.max(dense_distances)
        dense_norm = 1 - (dense_distances / dense_max) if dense_max != 0 else dense_distances
        combined_scores = self.bm25_weight * bm25_norm + self.dense_weight * dense_norm
        top_idx = combined_scores.argsort()[::-1][:recall_k]
        return [self.outputs[i] for i in top_idx]

class RAG_Bot:
    def __init__(self, retriever, llm_fn, rerank_fn, recall_k=20, top_n=3):
        self.retriever = retriever
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
        recall_docs = self.retriever.search(user_query, recall_k=self.recall_k)
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

        retriever = HybridRetriever(
            instructions=instructions,
            outputs=outputs,
            embedding_fn=embedding_fn,
            bm25_weight=0.5,
            dense_weight=0.5,
        )

        bot = RAG_Bot(
            retriever=retriever,
            llm_fn=get_completion,
            rerank_fn=rerank_documents,
            recall_k=10,
            top_n=3
        )

        user_query = "得了白癫风怎么办？"
        response = bot.chat(user_query)
        print(f"用户：{user_query}")