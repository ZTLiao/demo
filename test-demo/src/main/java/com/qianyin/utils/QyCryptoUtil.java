package com.qianyin.utils;

import java.util.*;

/**
 * @author: liaozetao
 * @date: 2023/5/29 17:26
 * @description:
 */
public class QyCryptoUtil {

    static {
        System.load("/Users/liaozetao/IdeaProjects/demo/test-demo/src/main/resources/libqyencrypt.so");
    }

    private static native String genSignQy(Map<String, String> params, List<String> excludes, String key);

    public static native String decryptDesQy(String base64Text, String key);

    public static native String decryptAesQy(String base64Text, String key);

    public static native String encryptDesQy(String plainText, String key);

    public static native String encryptAesQy(String plainText, String key);

    public static String generateSignatureQy(Map<String, String> params, List<String> excludes, String key) {
        if (params != null && !params.isEmpty()) {
            params = new HashMap<>(params);
        }
        if (excludes != null && !excludes.isEmpty()) {
            excludes = new ArrayList<>(excludes);
        }
        return genSignQy(params, excludes, key);
    }
}
