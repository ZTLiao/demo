package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/2/19 3:49 PM
 * @description:
 */
public class IsValidTest {

    public static void main(String[] args) {
        String s = "{[]}";
        s = "()[]{}";
        s = "()";
        s = "(]";
        s = "([)]";
        s = "1 + (1 - 2)";
        boolean isValid = false;
        SolutionStack stack = new SolutionStack();
        for (int i = 0, len = s.length(); i < len; i++) {
            char c0 = s.charAt(i);
            if (c0 == '(' || c0 == '[' || c0 == '{') {
                stack.push(c0);
            }
            if (c0 == ')' || c0 == ']' || c0 == '}') {
                char c1 = stack.pop();
                System.out.println("c1 = " + c1 + ", c0 = " + c0);
                isValid = (c1 == '(' && c0 == ')') || (c1 == '[' && c0 == ']') || (c1 == '{' && c0 == '}');
                if (!isValid) {
                    break;
                }
            }
            System.out.println(stack.toString());
            isValid = isValid && stack.size == 0;
        }
        System.out.println(isValid);
    }

    static class StackFrame {
        StackFrame prev;
        char val = '\0';
    }

    static class SolutionStack {
        StackFrame Ebp;
        StackFrame Esp;
        int size = 0;

        public void push(char val) {
            StackFrame stackFrame = new StackFrame();
            stackFrame.val = val;
            stackFrame.prev = Esp;
            if (Ebp == null) {
                Ebp = stackFrame;
            }
            Esp = stackFrame;
            size++;
        }

        public char pop() {
            StackFrame stackFrame = Esp;
            if (stackFrame == null) {
                return '\0';
            }
            if (stackFrame.prev != null) {
                Esp = stackFrame.prev;
            }
            char val = stackFrame.val;
            size--;
            if (size == 0) {
                Ebp = null;
                Esp = null;
            }
            return val;
        }

        public String toString() {
            String str = "";
            StackFrame stackFrame = Esp;
            while (stackFrame != Ebp) {
                str = " val = " + stackFrame.val;
                stackFrame = stackFrame.prev;
            }
            return str;
        }
    }
}
