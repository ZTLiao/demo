package com.ztliao.mapreduce.partitionerAndWritableComparable;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/22 14:48
 * @description:
 */
public class FlowMapper extends Mapper<LongWritable, Text, FlowBean, Text> {

    private FlowBean outK = new FlowBean();

    private Text outV = new Text();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, FlowBean, Text>.Context context) throws IOException, InterruptedException {
        //获取一行
        String line = value.toString();
        //切割
        String[] lineArray = line.split("\t");
        //封装
        outV.set(lineArray[0]);
        outK.setUpFlow(Long.parseLong(lineArray[1]));
        outK.setUpFlow(Long.parseLong(lineArray[2]));
        outK.setSumFlow();
        context.write(outK, outV);
    }
}
