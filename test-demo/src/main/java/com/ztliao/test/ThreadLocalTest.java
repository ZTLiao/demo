package com.ztliao.test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @author: liaozetao
 * @date: 2022/11/8 20:40
 * @description:
 */
public class ThreadLocalTest {

    private static InheritableThreadLocal<List<Thread>> THREAD_LOCAL = new InheritableThreadLocal<List<Thread>>() {
        @Override
        protected List<Thread> initialValue() {
            return new CopyOnWriteArrayList<>();
        }
    };

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        THREAD_LOCAL.get().add(Thread.currentThread());
        CompletableFuture<String> future = (CompletableFuture<String>) CompletableFuture.supplyAsync(() -> {
            Thread thread = Thread.currentThread();
            THREAD_LOCAL.get().add(Thread.currentThread());
            System.out.println("threadName : " + Thread.currentThread().getName() + ", value : test1...");
            for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
                System.out.println("threadName:" + Thread.currentThread().getName() + "," + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")");
            }
            return "test";
        });
        System.out.println("threadName : " + Thread.currentThread().getName() + ", value : test2...");
        String s = future.get();
        System.out.println("result : " + s);
        Thread mainThread = Thread.currentThread();
        for (StackTraceElement stackTraceElement : mainThread.getStackTrace()) {
            System.out.println("threadName:" + Thread.currentThread().getName() + "," + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")");
        }
        for (Thread thread : THREAD_LOCAL.get()) {
            System.out.println(thread.getName() + ", isAlive : " + thread.isAlive() + ", isInterrupted : " + thread.isInterrupted() + ", state : " + thread.getState());
        }
        mainThread.getThreadGroup();
    }

    private static void test() {
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
