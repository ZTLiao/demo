package com.ztliao.chapter02;

/**
 * @author: liaozetao
 * @date: 2022/2/13 2:00 PM
 * @description:
 */
public class ClassLoaderTest2 {

    public static void main(String[] args) {
        try {
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);

            ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();
            System.out.println(classLoader1);

            ClassLoader classLoader2 = ClassLoader.getSystemClassLoader();
            System.out.println(classLoader2);

            System.out.println(classLoader2.getParent());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
