package com.ztliao.test;

import java.util.Random;

/**
 * @author: liaozetao
 * @date: 2022/11/2 16:10
 * @description:
 */
public class KeyTest {

    public static byte a[] =
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static String s[] =
            {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static byte n[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static byte aa[][] = {
            {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'},
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}
    };

    public static void main(String[] args) {
//        Random random = new Random();
//        for (long i = 0; i < Long.MAX_VALUE; i++) {
//            int num = random.nextInt(32);
//            byte[] bytes = new byte[num];
//            for (int j = 0; j < num; j++) {
//                int j1 = random.nextInt(2);
//                int j2 = random.nextInt(j1 == 0 ? 26 : 10);
//                bytes[j] = aa[j1][j2];
//            }
//            String str = new String(bytes);
//            long start = System.currentTimeMillis();
//            String str1 = encryptSign(str);
//            long end = System.currentTimeMillis();
//            System.out.println("encryptSign time is : " + (end - start));
//            String str2 = decryptSign(str1);
//            System.out.println("原文 : " + str);
//            System.out.println("加密 : " + str1);
//            System.out.println("解密 : " + str2);
//            assert str.equals(str2);
//        }
        String str = "{\"data\":200,\"msg\":\"success\"}";
        String encrypt = encryptSign(str);
        String decrypt = decryptSign(encrypt);
        System.out.println("encrypt : " + encrypt);
        System.out.println("decrypt : " + decrypt);
        assert encrypt.equals(decrypt);
    }

    private static String encrypt(String key) {
        Random random = new Random();
        byte[] keyArray = key.getBytes();
        byte b = 0;
        byte[] uuidArray = new byte[33 * 8];
        for (int i = 0; i < 8; i++) {
            int rand = random.nextInt(8);
            while ((b & (0x1 << rand)) != 0) {
                rand = random.nextInt(8);
            }
            b |= (0x1 << rand);
            int start = i * 33;
            int step = rand * 4;
            uuidArray[start] = a[rand];
            for (int j = start + 1; j <= start + step; j++) {
                uuidArray[j] = a[random.nextInt(26)];
            }
            uuidArray[start + step + 1] = keyArray[i * 4];
            uuidArray[start + step + 2] = keyArray[i * 4 + 1];
            uuidArray[start + step + 3] = keyArray[i * 4 + 2];
            uuidArray[start + step + 4] = keyArray[i * 4 + 3];
            for (int j = start + step + 5; j < start + 33; j++) {
                int j1 = random.nextInt(2);
                int j2 = random.nextInt(j1 == 0 ? 26 : 10);
                uuidArray[j] = aa[j1][j2];
            }
        }
        return new String(uuidArray);
    }

    private static String decrypt(String encrypt) {
        byte[] keyArray = new byte[32];
        byte[] encryptArray = encrypt.getBytes();
        for (int i = 0; i < 8; i++) {
            int index = encryptArray[i * 33] - 97;
            keyArray[i * 4] = encryptArray[index * 4 + i * 33 + 1];
            keyArray[i * 4 + 1] = encryptArray[index * 4 + i * 33 + 2];
            keyArray[i * 4 + 2] = encryptArray[index * 4 + i * 33 + 3];
            keyArray[i * 4 + 3] = encryptArray[index * 4 + i * 33 + 4];
        }
        return new String(keyArray);
    }

    private static long next = System.currentTimeMillis();

    private static int rand(int bound) {
        next = next * 1103515245 + 12345;
        long rand = (next / 65536) % 32768;
        return (int) ((rand < 0 ? -rand : rand) % bound);
    }

    public static String encryptSign(String key) {
        byte[] keyArray = key.getBytes();
        int length = keyArray.length;
        int word = 8;
        byte[] encryptArray = new byte[length * word];
        for (int i = 0; i < length; i++) {
            int step = i * word;
            for (int j = 0, k = word - 1; j < word; j++, k--) {
                int r1 = rand(2);
                int r2 = rand(r1 == 0 ? 26 : 10);
                encryptArray[step + j] = (byte) (r2 + (r1 == 0 ? 97 : 48));
                if ((keyArray[i] & (1 << k)) == 0) {
                    encryptArray[step + j] &= (byte) ((keyArray[i] | ~(1 << k)) & 0xFF);
                } else {
                    encryptArray[step + j] |= (1 << k);
                }
            }
        }
        return new String(encryptArray);
    }

    public static String decryptSign(String encrypt) {
        byte[] encryptArray = encrypt.getBytes();
        int size = encryptArray.length / 8;
        byte[] keyArray = new byte[size];
        for (int i = 0; i < size; i++) {
            int step = i * 8;
            keyArray[i] |= encryptArray[step] & 128;
            keyArray[i] |= encryptArray[step + 1] & 64;
            keyArray[i] |= encryptArray[step + 2] & 32;
            keyArray[i] |= encryptArray[step + 3] & 16;
            keyArray[i] |= encryptArray[step + 4] & 8;
            keyArray[i] |= encryptArray[step + 5] & 4;
            keyArray[i] |= encryptArray[step + 6] & 2;
            keyArray[i] |= encryptArray[step + 7] & 1;
        }
        return new String(keyArray);
    }

}
