package com.ztliao.mapreduce.wordcount2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/22 11:06
 * @description:
 */

/**
 * KEYIN：map阶段输入的key的类型，LongWriteable
 * VALUEIN：map阶段输入value类型，Text
 * KEYOUT：map阶段输出的key类型，Text
 * VALUEOUT：map阶段输出的value类型，IntWriteable
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final Text outK = new Text();

    private final IntWritable outV = new IntWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        //1.获取一行
        String line = value.toString();
        //2.切割
        String[] words = line.split(" ");
        //3.循环写出
        for (String word : words) {
            //封装out
            outK.set(word);
            //写出
            context.write(outK, outV);
        }
    }
}
