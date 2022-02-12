package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/2/12 1:06 PM
 * @description:
 */
public class IsPalindromeTest {

    public static void main(String[] args) {
        System.out.println(sizeof(1410110141));
        System.out.println(isPalindrome(1410110141));
    }

    public static int sizeof(int i) {
        int n = 0;
        for (int j = 10; (i = i / j) != 0; n++) {
            System.out.println(i);
        }
        return n;
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        if (x == 0) {
            return true;
        }
        char[] string = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int n = 0;
        for (int i = x, j = 10; (i = i / j) != 0; n++) ;
        n = n > 0 ? ++n : 0;
        char[] arr = new char[n];
        for (int i = 0, j = 10, k = x; i < n; i++, k = k / j) {
            arr[i] = string[k % 10];
        }
        boolean b = true;
        int start = 0;
        int end = n - 1;
        while (start < n && end > 0 && start != end) {
            System.out.println("arr[" + start + "]=" + arr[start]);
            System.out.println("arr[" + end + "]=" + arr[end]);
            if (arr[start++] != arr[end--]) {
                b = false;
                break;
            }
        }
        return b;
    }

}
