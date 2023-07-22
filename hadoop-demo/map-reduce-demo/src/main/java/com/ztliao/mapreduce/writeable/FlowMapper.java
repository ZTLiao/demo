package com.ztliao.mapreduce.writeable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/22 14:48
 * @description:
 */
public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    private final Text outK = new Text();

    private final FlowBean outV = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context) throws IOException, InterruptedException {
        //1.获取一行
        String line = value.toString();
        //2.切割
        String[] array = line.split("\t");
        //3.抓取想要的数据
        String phone = array[1];
        String upFlow = array[array.length - 3];
        String downFlow = array[array.length - 2];
        //4.封装
        outK.set(phone);
        outV.setUpFlow(Long.parseLong(upFlow));
        outV.setDownFlow(Long.parseLong(downFlow));
        outV.setSumFlow();
        //5.写出
        context.write(outK, outV);
    }
}
