package com.ztliao.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * @author: liaozetao
 * @date: 2022/10/10 22:56
 * @description:
 */
public class FileTest {

    public static void main(String[] args) {
        String inputPath = "/Users/liaozetao/Downloads/data.txt";
        String outputPath = "/Users/liaozetao/Downloads/data4.txt";
        Set<String> messIds = new HashSet<>();
        File file = new File(inputPath);
        try (PrintStream printStream = new PrintStream(outputPath);
             InputStreamReader read = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(read)) {
            System.setOut(printStream);
            if (file.isFile() && file.exists()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String giftRecordId = line.split(", giftRecordId:")[1];
                    String messId = line.split(", giftRecordId:")[0].split(",messId:")[1];
                    if (messIds.contains(messId)) {
                        String sql = "insert into temp_gift_send_record_message(mess_id, send_record_id) values('" + messId + "','" + giftRecordId + "');";
                        System.out.println(sql);
                    }
                    messIds.add(messId);
//                    JSONObject jsonObject = JSONObject.parseObject(line);
//                    String messId = jsonObject.getString("messId");
//                    if (messIds.contains(messId)) {
//                        System.out.println(jsonObject.toJSONString());
//                    }
//                    messIds.add(messId);
                }
            }
//            for (JSONObject value : map.values()) {
//                String sql = "insert into mq_message(mess_id, message_json) values('" + value.getString("messId") + "','" + value.toJSONString() + "');";
//                System.out.println(sql);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
