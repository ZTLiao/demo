package com.ztliao.mapreduce.reduceJoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/29 09:23
 * @description:
 */
public class TableDriver {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //1.获取job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        //2.设置jar
        job.setJarByClass(TableDriver.class);
        //3.关联mapper和reducer
        job.setMapperClass(TableMapper.class);
        job.setReducerClass(TableReducer.class);
        //4.设置mapper输出的key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);
        //5.设置最终数据输出的key和value类型
        job.setOutputKeyClass(TableBean.class);
        job.setOutputValueClass(NullWritable.class);
        //6.设置数据的输入和输出
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //7.提交job
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}
