package com.ztliao.yarn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/30 10:43
 * @description:
 */
public class WordCount implements Tool {

    private Configuration conf;

    //核心驱动
    @Override
    public int run(String[] args) throws Exception {
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
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //7.提交job
        return job.waitForCompletion(true) ? 0 : 1;
    }

    @Override
    public void setConf(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public Configuration getConf() {
        return this.conf;
    }

    //mapper
    public static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

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
    //reducer

    public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private final IntWritable outV = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            outV.set(sum);
            context.write(key, outV);
        }

    }
}
