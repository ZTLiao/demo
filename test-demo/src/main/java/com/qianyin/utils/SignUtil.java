package com.qianyin.utils;

import cn.hutool.crypto.SecureUtil;

import java.util.*;

/**
 * @author: liaozetao
 * @date: 2022/9/2 15:34
 * @description:
 */
public class SignUtil {

    /**
     * 公参名
     */
    public static final List<String> PUBLIC_PARAMETER_NAMES = Arrays
            .asList("pub_sign", "pub_uid", "pub_ticket", "appVersion", "appVersionCode", "channel", "deviceId", "ispType", "model", "netType", "os",
                    "osVersion", "app", "ticket", "thirdChannel", "newDeviceId");

    public static String genSign(Map<String, String> parameters, String key) {
        StringBuilder sb = new StringBuilder();
        try {
            Map<String, List<String>> params = new HashMap<>();
            if (parameters != null && !parameters.isEmpty()) {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    params.put(entry.getKey(), Collections.singletonList(entry.getValue()));
                }
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
            System.out.println(" params : " + sb);
            return MD5Util.getMD5String(sb.toString()).toUpperCase();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void excludeFields(Map<String, List<String>> params) {
        params.entrySet().removeIf(e -> PUBLIC_PARAMETER_NAMES.contains(e.getKey()));
    }

    public static String genSign(Map<String, String> parameters){
        return genSign(parameters, "a621c27f579020b20ba9a8d82ddf8130");
    }

    public static void main(String[] args) {
        Map params = new HashMap();
        params.put("pub_timestamp", "1667460047000");
        params.put("roomUid", "90304899");
        params.put("uid", "90304899");
        String sign = SignUtil.genSign(params, "a621c27f579020b20ba9a8d82ddf8130");
        System.out.println("sign = " + sign);
        String str = "request_secret_key=579020b20ba9a8d8&uid=96101293&key=a621c27f579020b20ba9a8d82ddf8130";
        String s = SecureUtil.md5(str).toUpperCase();
        System.out.println(s);
        String s1 = MD5Util.getMD5String(str).toUpperCase();
        System.out.println(s1);
    }
}
