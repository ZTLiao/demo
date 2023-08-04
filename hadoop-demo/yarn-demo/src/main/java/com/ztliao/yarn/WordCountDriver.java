package com.ztliao.yarn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;

/**
 * @author: liaozetao
 * @date: 2023/7/30 10:55
 * @description:
 */
public class WordCountDriver {

    public static void main(String[] args) throws Exception {
        //创建配置
        Configuration conf = new Configuration();
        Tool tool;
        switch (args[0]) {
            case "wordcount": {
                tool = new WordCount();
                break;
            }
            default:
                throw new RuntimeException("no such tool " + args[0]);
        }
        //执行
        int run = ToolRunner.run(conf, tool, Arrays.copyOfRange(args, 1, args.length));
        System.exit(run);
    }
}
