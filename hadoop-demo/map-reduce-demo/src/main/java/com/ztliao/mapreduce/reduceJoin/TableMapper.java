package com.ztliao.mapreduce.reduceJoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/29 09:09
 * @description:
 */
public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

    private String fileName;

    private final Text outK = new Text();

    private final TableBean outV = new TableBean();

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, TableBean>.Context context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        fileName = fileSplit.getPath().getName();

    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, TableBean>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (fileName.contains("order")) {
            String[] split = line.split("\t");
            outK.set(split[1]);
            outV.setId(split[0]);
            outV.setPid(split[1]);
            outV.setAmount(Integer.parseInt(split[2]));
            outV.setpName("");
            outV.setFlag("order");
        } else if (fileName.contains("pd")) {
            String[] split = line.split("\t");
            outK.set(split[0]);
            outV.setId("");
            outV.setPid(split[0]);
            outV.setAmount(0);
            outV.setpName(split[1]);
            outV.setFlag("pd");
        }
        context.write(outK, outV);
    }
}
