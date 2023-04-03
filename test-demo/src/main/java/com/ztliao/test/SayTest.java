package com.ztliao.test;

import java.util.Random;

/**
 * @author: liaozetao
 * @date: 2023/2/20 20:16
 * @description:
 */
public class SayTest {

    public static void main(String[] args) {
        int sayCount = 0;
        int notSayCount = 0;
        for (int i = 0; i < 100; i++) {
            int rand = new Random().nextInt(2);
            if (rand == 1) {
                sayCount++;
            } else {
                notSayCount++;
            }
        }
        System.out.println("say : " + sayCount + "%");
        System.out.println("not say : " + notSayCount + "%");
    }
}
