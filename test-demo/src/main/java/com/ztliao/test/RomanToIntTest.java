package com.ztliao.test;

/**
 * @author: liaozetao
 * @date: 2022/2/12 2:44 PM
 * @description:
 */
public class RomanToIntTest {

    public static void main(String[] args) {
        String s = "MCMXCIV";
        String[] romans = {
                "I",
                "V",
                "X",
                "L",
                "C",
                "D",
                "M",
                "IV",
                "IX",
                "XL",
                "XC",
                "CD",
                "CM"
        };
        int[] ints = {
                1,
                5,
                10,
                50,
                100,
                500,
                1000,
                4,
                9,
                40,
                90,
                400,
                900
        };
        int sum = 0;
        for (int i = 0, len = s.toCharArray().length; i < len; i++) {
            char c = s.charAt(i);
            String str = String.valueOf(c);
            if ((c == 'I' || c == 'X' || c == 'C') && (i + 1) < len) {
                String temp = str + s.charAt(i + 1);
                boolean isExist = false;
                for (int j = 0; j < romans.length; j++) {
                    if (romans[j].equals(temp)) {
                        isExist = true;
                        break;
                    }
                }
                if (isExist) {
                    i++;
                    str = temp;
                }
            }
            System.out.println(str);
            for (int j = 0; j < romans.length; j++) {
                String roman = romans[j];
                if (roman.equals(str)) {
                    sum += ints[j];
                    break;
                }
            }
        }
        System.out.println(sum);
    }
}
