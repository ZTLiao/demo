package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/2/22 2:23 PM
 * @description:
 */
public class SearchInsertTest {

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 6};
        int target = 2;
        int index = 0;
        int i = 0;
        for (; i < nums.length; i++) {
            if (nums[i] >= target) {
                index = i;
                break;
            }
        }
        if (i == nums.length) {
            index = nums.length;
        }
        System.out.println(index);
    }
}
