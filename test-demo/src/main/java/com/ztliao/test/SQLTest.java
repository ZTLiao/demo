package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2023/8/13 14:28
 * @description:
 */
public class SQLTest {

    public static void main(String[] args) {
        String[] log = {
        };
        for (String str : log) {
            String[] split = str.split(" ");
            String signInDate = split[0];
            String signInTime = split[1];
            String registerTime = signInDate + " " + signInTime;
            String content = split[9];
        }
    }
}
