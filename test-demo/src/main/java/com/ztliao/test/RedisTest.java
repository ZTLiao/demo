package com.ztliao.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

/**
 * @author: liaozetao
 * @date: 2022/12/12 11:21
 * @description:
 */
public class RedisTest {

    public static void main(String[] args) {
//        JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(), "112.74.180.169", 1379, 1000, "lgf@(!@nb*123as", 0);
//        Jedis jedis = jedisPool.getResource();
        String inputPath = "/Users/liaozetao/Downloads/bibi_block_account.txt";
        File file = new File(inputPath);
        int count = 0;
        try (InputStreamReader read = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(read)) {
            if (file.isFile() && file.exists()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] arr = line.split("\":\"\\{");
                    if (arr.length == 2) {
                        try {
                            String key = arr[0].replace("\"", "");
                            String value = ("{" + arr[1]).replace("}\"", "}");
                            Map<String, Object> map = JSONObject.parseObject(value, new TypeReference<Map<String, Object>>() {
                            });
                            String blockStartTime = map.get("blockStartTime").toString();
                            Date date = new Date(blockStartTime);
                            Instant instant = date.toInstant();
                            ZoneId zoneId = ZoneId.systemDefault();
                            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                            if (localDateTime.isAfter(LocalDateTime.of(2022, 12, 1, 0, 0))) {
                                System.out.println("blockStartTime : " + blockStartTime);
                                count++;
                            }
                        }catch (Exception ignored) {

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

}
