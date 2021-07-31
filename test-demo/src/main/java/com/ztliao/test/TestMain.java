package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2021/7/19 9:05 PM
 * @description:
 */
public class TestMain {

    /**
     * 进位
     *
     * @param num
     * @param letters
     * @param chars
     * @return
     */
    public static int getChars(int num, char[] letters, char[] chars) {
        int over = 0;
        for (int i = 0, len = chars.length, j = len - 1; i < num && j >= 0; ) {
            int tmp = chars[j] + 1;
            int bool = 0;
            if (tmp > letters[letters.length - 1]) {
                tmp = letters[0];
                if (j == 0) {
                    over++;
                } else {
                    bool = 1;
                }
            }
            chars[j] = (char) tmp;
            if (bool == 0) {
                j = len - 1;
                i++;
            } else {
                j--;
            }
        }
        return over;
    }

    public static void main(String[] args) {
        char[][] LETTERS = {
                {65, 90},
                {48, 57}
        };
        String highValue = "AA";
        String lowValue = "000";
        char[] highChars = highValue.toCharArray();
        char[] lowChars = lowValue.toCharArray();
        while (true) {
            getChars(getChars(1, LETTERS[1], lowChars), LETTERS[0], highChars);
            String s = String.valueOf(highChars) + String.valueOf(lowChars);
            System.out.println(s);
            if (s.equals("ZZ999")) {
                System.out.println("结束.");
                break;
            }
        }
    }
}
