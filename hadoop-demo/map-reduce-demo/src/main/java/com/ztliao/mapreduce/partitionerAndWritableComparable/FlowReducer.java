package com.ztliao.mapreduce.partitionerAndWritableComparable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/22 14:48
 * @description:
 */
public class FlowReducer extends Reducer<FlowBean, Text, Text, FlowBean> {



    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Reducer<FlowBean, Text, Text, FlowBean>.Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(value, key);
        }
    }
}
