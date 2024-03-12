package com.ztliao.test;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

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
        String outputPath = "/Users/liaozetao/Downloads/data1.txt";
        File file = new File(inputPath);
        try (PrintStream printStream = new PrintStream(outputPath);
             InputStreamReader read = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(read)) {
            System.setOut(printStream);
            int sum = 0;
            if (file.isFile() && file.exists()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (StrUtil.isNotEmpty(line)) {
                        String[] array = line.split("params=");
                        if (array.length > 1) {
                            String s = array[1];
                            if (s.contains(", result=")) {
                                String[] split = s.split(", result=");
                                JSONObject jsonObject1 = JSONObject.parseObject(split[0]);
                                JSONObject jsonObject2 = JSONObject.parseObject(split[1]);
                                String toAccIdStr = jsonObject1.getString("toAccids");
                                List<String> strings = JSONArray.parseArray(toAccIdStr, String.class);
                                String timeTag = jsonObject2.getString("timetag");
                                for (String toAccId : strings) {
                                    System.out.println(toAccId + "," + timeTag);
                                }
                            }
                        }
                    }
                }
                System.out.println(sum);
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
