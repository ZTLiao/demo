package com.ztliao.mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/22 11:06
 * @description:
 */
public class WordCountDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1.获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置jar包路径
        job.setJarByClass(WordCountDriver.class);
        //3.关联mapper和reducer
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        //4.设置mapper的输出kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //5.设置最终的输出kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, new Path("hdfs://hadoop001/"));
        FileOutputFormat.setOutputPath(job, new Path("hdfs://hadoop001/wordcount/output"));
        //7.提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
