package com.qianyin.utils;

import cn.hutool.crypto.SecureUtil;
import org.apache.commons.codec.digest.DigestUtils;

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
            return DigestUtils.md5Hex(sb.toString()).toUpperCase();
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
        Map<String, String> params = new HashMap<>();
        params.put("pub_timestamp", "1667460047000");
        params.put("roomUid", "90304899");
        params.put("uid", "90304899");
        String sign = SignUtil.genSign(params, "1ea53d260ecf11e7b56e00163e046a26");
        System.out.println("sign = " + sign);
        System.out.println("md5 = " + DigestUtils.md5Hex("pub_timestamp=1685517186776&uid=0&key=4r2vskf2267swgksyyg9k6qzdg91axd8").toUpperCase());
        System.out.println("123 = " + DigestUtils.md5Hex("1234abcd").toUpperCase());
    }
}
