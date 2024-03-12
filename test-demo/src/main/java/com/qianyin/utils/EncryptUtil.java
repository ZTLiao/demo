package com.qianyin.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

/**
 * @author: liaozetao
 * @date: 2022/9/7 20:01
 * @description:
 */
public class EncryptUtil {

    public static String encryptUseDES(String plainText, String key) {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] plainTextBytes = encoder.encode(cipherBytes);
            return new String(plainTextBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptUseDES(String plainText) {
        return encryptUseDES(plainText, "1ea53d260ecf11e7b56e00163e046a26");
    }

    public static void main(String[] args) {
        String des = encryptUseDES("123456", "1ea53d260ecf11e7b56e00163e046a26");
        System.out.println(des);
    }
}
