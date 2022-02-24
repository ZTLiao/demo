package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/2/23 2:07 PM
 * @description:
 */
public class ReverseOnlyLettersTest {

    public static void main(String[] args) {
        String s = "Test1ng-Leet=code-Q!";
        int start = 0;
        int end = s.length() - 1;
        char[] chars = s.toCharArray();
        while (start <= end) {
            char startChar = chars[start];
            char endChar = chars[end];
            if (!((65 <= startChar && 90 >= startChar) || (97 <= startChar && 122 >= startChar))) {
                start++;
                continue;
            }
            if (!((65 <= endChar && 90 >= endChar) || (97 <= endChar && 122 >= endChar))) {
                end--;
                continue;
            }
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start++;
            end--;
        }
        System.out.println(chars);
    }
}
