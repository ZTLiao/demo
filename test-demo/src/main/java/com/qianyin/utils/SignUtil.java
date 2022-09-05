package com.ztliao.test;

import cn.hutool.crypto.SecureUtil;

import java.util.*;

/**
 * @author: liaozetao
 * @date: 2022/9/2 15:34
 * @description:
 */
public class SignTest {

    /**
     * 公参名
     */
    public static final List<String> PUBLIC_PARAMETER_NAMES = Arrays
            .asList("pub_sign", "pub_uid", "pub_ticket", "appVersion", "appVersionCode", "channel", "deviceId", "ispType", "model", "netType", "os",
                    "osVersion", "app", "ticket", "thirdChannel");

    public static String genSign(Map<String, List<String>> parameters, String key) {
        StringBuilder sb = new StringBuilder();
        try {
            Map<String, List<String>> params = new HashMap<>();
            if (parameters != null && !parameters.isEmpty()) {
                params.putAll(parameters);
            }

            if (!params.isEmpty()) {
                excludeFields(params); //排除不需要参与签名的公参
                params.entrySet()
                        .stream()
                        .sorted(Map.Entry.comparingByKey()) //key字典升序
                        .forEach(paramEntry -> {
                            String paramValue = String.join(",", Arrays.stream(paramEntry.getValue().toArray(new String[]{})).sorted().toArray(String[]::new));
                            sb.append(paramEntry.getKey()).append("=").append(paramValue).append('&');
                        });
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("&key=").append(key);
            return SecureUtil.md5(sb.toString()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void excludeFields(Map<String, List<String>> params) {
        params.entrySet().removeIf(e -> PUBLIC_PARAMETER_NAMES.contains(e.getKey()));
    }

    public static void main(String[] args) {
        Map params = new HashMap();
        params.put("uid", Collections.singletonList("80000677"));
        params.put("version", Collections.singletonList("2.2.1"));
        params.put("app", Collections.singletonList("hani"));
        params.put("channel", Collections.singletonList("appstore_hani"));
        params.put("os", Collections.singletonList("ios"));
        params.put("versionCode", Collections.singletonList("85"));
        String sign = SignTest.genSign(params, "a621c27f579020b20ba9a8d82ddf8130");
        System.out.println("sign = " + sign);
    }
}
