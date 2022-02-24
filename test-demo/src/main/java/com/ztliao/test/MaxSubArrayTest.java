package com.ztliao.test;

import java.util.Arrays;

/**
 * @author: liaozetao
 * @date: 2022/2/23 9:07 AM
 * @description:
 */
public class MaxSubArrayTest {

    public static void main(String[] args) {
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        //int[] nums = {5, 4, -1, 7, 8};
        int[] dp = new int[nums.length];
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i == 0) {
                dp[i] = nums[i];
            } else {
                if (nums[i] > dp[i - 1] + nums[i]) {
                    dp[i] = nums[i];
                } else {
                    dp[i] = dp[i - 1] + nums[i];
                }
            }
            if (dp[i] > max) {
                max = dp[i];
            }
            System.out.println(Arrays.toString(dp));
        }
        System.out.println(max);
    }
}
