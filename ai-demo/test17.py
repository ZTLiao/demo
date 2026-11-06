from langchain.agents.middleware import AgentMiddleware

class TokenLimitMiddleware(AgentMiddleware):
    """自定义限流中间件：限制单轮对话工具调用最大次数"""
    def __init__(self, max_calls: int = 5):
        self.max_calls = max_calls

    def wrap_tool_call(self, next, state, context):
        # 统计当前工具调用次数，超限则拦截
        call_count = state.get("tool_call_count", 0)
        if call_count >= self.max_calls:
            raise RuntimeError("工具调用次数已达上限")
        state["tool_call_count"] = call_count + 1
        return next(state, context)