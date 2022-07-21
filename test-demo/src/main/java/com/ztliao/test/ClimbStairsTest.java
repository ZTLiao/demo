package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/3/3 6:47 PM
 * @description:
 */
public class ClimbStairsTest {

    public static void main(String[] args) {
        int n = 4;
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        System.out.println(dp[n - 1]);
    }
}
