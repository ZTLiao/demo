package com.ztliao.test;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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
        String str = "1ea53d260ecf11e7b56e00163e046a26";
        String encrypt = encrypt1(str);
        System.out.println(encrypt);
        System.out.println(decrypt1(encrypt));
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

    private static String encrypt1(String key) {
        Random random = new Random();
        byte[] keyArray = key.getBytes();
        byte[] uuidArray = new byte[32 * (keyArray.length / 4)];
        for (int i = 0, len = keyArray.length / 4; i < len; i++) {
            for (int j = 0, k = 7, l = 0; j < 32; j++) {
                int j1 = random.nextInt(2);
                int j2 = random.nextInt(j1 == 0 ? 26 : 10);
                uuidArray[i * 32 + j] = (byte) (j2 + (j1 == 0 ? 97 : 48));
                System.out.println("keyArray[" + (i * 4 + l) + "] = " + keyArray[i * 4 + l]);
                if ((keyArray[i * 4 + l] & (1 << k)) == 0) {
                    uuidArray[i * 32 + j] &= (byte) ((keyArray[i * 4 + l] | ~(1 << k)) & 0xFF);
                } else {
                    uuidArray[i * 32 + j] |= (1 << k);
                }
                System.out.println("uuidArray[" + (i * 32 + j) + "] = " + Integer.toBinaryString(uuidArray[i * 32 + j]));
                if (k == 0) {
                    k = 7;
                    l++;
                } else {
                    k--;
                }
            }
        }
        System.out.println(ReflectionToStringBuilder.toString(uuidArray));
        return new String(uuidArray);
    }

    private static String decrypt1(String encrypt) {
        byte[] keyArray = new byte[32];
        byte[] encryptArray = encrypt.getBytes();
        for (int i = 0, len = encryptArray.length / 8; i < len; i++) {
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
