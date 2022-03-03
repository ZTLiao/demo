package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/3/3 3:26 PM
 * @description:
 */
public class AddDigitsTest {

    public static void main(String[] args) {
        int sum = 0;
        int num = 110;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
            if (num == 0 && sum >= 10) {
                num = sum;
                sum = 0;
            }
        }
        System.out.println(sum);
    }
}
