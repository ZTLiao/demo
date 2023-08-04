package com.ztliao.mapreduce.etl;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/29 12:35
 * @description:
 */
public class WebLogMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        boolean result  = parseLog(line);
        if (!result) {
            return;
        }
        context.write(value, NullWritable.get());
    }

    private boolean parseLog(String line) {
        String[] fields = line.split(" ");
        return fields.length > 11;
    }
}
