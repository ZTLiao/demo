package com.ztliao.test;

import java.util.Arrays;

/**
 * @author: liaozetao
 * @date: 2022/2/22 12:58 PM
 * @description:
 */
public class RemoveElementTest {

    public static void main(String[] args) {
        int[] nums = {2};
        int val = 3;
        int i = 0;
        for (int j = nums.length - 1; i < nums.length && i <= j; ) {
            int num0 = nums[i];
            if (num0 == val) {
                int num1 = nums[j];
                if (num1 != val) {
                    nums[j] = nums[i] ^ nums[j];
                    nums[i] = nums[i] ^ nums[j];
                    nums[j] = nums[i] ^ nums[j];
                    i++;
                } else {
                    j--;
                }
            } else {
                i++;
            }
        }
        System.out.println(i);
        System.out.println(Arrays.toString(nums));
    }
}
