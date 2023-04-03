package com.ztliao.test;

import java.util.concurrent.TimeUnit;

/**
 * @author: liaozetao
 * @date: 2023/2/21 13:23
 * @description:
 */
public class SecondTest {

    public static void main(String[] args) {
        int us = 10000;
        int sec = 1;
        int seconds = 0;
        new Thread(() -> {
            int num = 0;
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("thread : " + num);
                } catch (Exception e) {

                }
            }
        }).start();
        while (true) {
            while (us / 1000000 < sec) {
                us++;
            }
            System.out.println("main : " + (++seconds));
            if (us > (1000000 * 1000)) {
                us = 0;
            }
        }
    }
}
