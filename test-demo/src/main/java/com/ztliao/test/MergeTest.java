package com.ztliao.test;

import java.util.Arrays;

/**
 * @author: liaozetao
 * @date: 2022/3/4 4:07 PM
 * @description:
 */
public class MergeTest {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;
        int i = (m + n) - 1;
        int start = 0;
        int end = n - 1;
        while (i != 0) {
            if (nums1[start] > nums2[end]) {
                int temp = nums1[start];
                nums1[start] = nums2[end];
                nums2[end] = temp;
                start++;
            }
            nums1[i--] = nums2[end--];
        }
        for (i = 0; i < nums1.length; i++) {
            System.out.println(nums1[i]);
        }
    }
}
