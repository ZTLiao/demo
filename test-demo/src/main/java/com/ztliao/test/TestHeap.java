package com.ztliao.test;

/**
 * @author MaxStar
 * @date 2022/3/19
 */
public class TestHeap {

    public static void main(String[] args) throws InterruptedException {
        cycle();
    }


    public static void cycle() {
        for (int i = 0; i < 1000000000; i++) {
            TestObject escape = new TestObject();
            escape.setName("冯宝宝");
            escape.setAge(18);
            escape.setGender("女");
        }
    }

    static class TestObject {
        private String name;
        private int age;
        private String gender;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
