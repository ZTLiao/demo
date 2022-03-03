package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/2/24 10:22 AM
 * @description:
 */
public class LengthOfLastWordTest {

    public static void main(String[] args) {
        String s = "   fly me to the moon  ";
        int size = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == 32) {
                System.out.println("i = " + i + ", c = " + c);
                System.out.println("size = " + size);
                size = 0;
            } else {
                size++;
            }
        }
    }
}
