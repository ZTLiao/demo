package com.ztliao.test;

import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

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
            if (file.isFile() && file.exists()) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] arr = line.split(StrUtil.COMMA);
                    System.out.println(Arrays.toString(arr));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
