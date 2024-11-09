package com.ztliao.test;

import java.util.Properties;

/**
 * @author: liaozetao
 * @date: 2022/2/24 3:11 PM
 * @description:
 */
public class AddBinaryTest {

    public static void main(String[] args) {
        Properties properties = new Properties();
        String a = "11";
        String b = "1";
        String str = "";
        int s0 = a.length();
        int s1 = b.length();
        int val = 48;
        int cin = 0;
        for (int i = s0 - 1, j = s1 - 1; i >= 0 || j >= 0; i--, j--) {
            char c0 = '0';
            if (i >= 0) {
                c0 = a.charAt(i);
            }
            char c1 = '0';
            if (j >= 0) {
                c1 = b.charAt(j);
            }
            int x = c0 - val;
            int y = c1 - val;
            int bit = x ^ y ^ cin;
            str = ((char) (bit + val)) + str;
            cin = (x & y) | (x & cin) | (y & cin);
        }
        if (cin != 0) {
            str = ((char) (cin + val)) + str;
        }
        System.out.println(str);
    }
}
