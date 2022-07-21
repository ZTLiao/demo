package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/2/24 5:24 PM
 * @description:
 */
public class MySqrtTest {

    public static void main(String[] args) {
        int x = 8;
        int ret = 0;
        int left = 1;
        int right = x;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (mid == x / mid) {
                ret = mid;
                break;
            } else if (mid > x / mid) {
                right = mid - 1;
            } else {
                left = mid + 1;
                ret = mid;
            }
        }
        System.out.println(ret);
    }

}
