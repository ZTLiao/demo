package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/11/8 20:40
 * @description:
 */
public class ThreadLocalTest {


    public static void main(String[] args) {
        TestWare testWare = new TestWare();
        testWare.set(1);
        System.out.println(testWare.get());
        for (int i = 0; i < 64; i++) {
            byte[] b = new byte[1024];
            System.out.println(b);
        }
        System.gc();
        System.out.println(testWare.get());
    }

    static class TestWare {
        private final ThreadLocal<Integer> num = new ThreadLocal<>();

        public void set(Integer i) {
            num.set(i);
        }

        public Integer get() {
            return num.get();
        }
    }

}
