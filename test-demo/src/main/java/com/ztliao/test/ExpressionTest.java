package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2021/10/29 8:56 AM
 * @description:
 */
public class ExpressionTest {

    static String str = "((((3))))";

    public static void main(String[] args) {
        //str = "(8+2*666+3+1/8*(234+1)-5+(1+(7+2))+1/1+5)";
        System.out.println(str.length() - 1);
        System.out.println(check_parentheses(0, str.length() - 1));
    }

    private static boolean check_parentheses(int p, int q) {
        if (str.charAt(p) == '(' && str.charAt(q) == ')') {
            int n = 0;
            for (int i = p; i <= q; i++) {
                if (str.charAt(i) == '(') {
                    n++;
                }
                if (str.charAt(i) == ')') {
                    n--;
                }
            }
            return n == 0;
        } else {
            return false;
        }
    }

}
