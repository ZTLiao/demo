package com.ztliao.test;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

public class SignTest {

    public static void main(String[] args) {
        // 应用ID
        String appId = "1578948593831571457";
        // 应用secret
        String appSecret = "w3a3Ud9oRskgRymkvQQeK5eYc3tyv7w0";
        // 请求时间戳（发送请求的时间戳）
        String timestamp = "1717567711335";
        // 随机字符串 (自定义随机字符串)
        String nonce = "c461152d23fd47a8bc07f243e1241e19";
        // 请求body (请求body， 需保证发送方与接收方的数据一致，建议在拦截器里做对应认证)
        String body = "{\"app_id\":\"1578948593831571457\",\"data\":{\"room_id\":\"6062279786\"},\"event\":\"room_clear\",\"mg_id\":\"1468180338417074177\",\"timestamp\":1717567711335}";

        // 签名串
        String signContent = String.format("%s\n%s\n%s\n%s\n", appId, timestamp, nonce, body);
        // 签名值
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA1, appSecret.getBytes());
        String signature = hMac.digestHex(signContent);
        System.out.println(signature);
    }

}