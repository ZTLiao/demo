package com.ztliao.mapreduce.partitionerAndWritableComparable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author: liaozetao
 * @date: 2023/7/25 13:25
 * @description:
 */
public class ProvincePartitioner2 extends Partitioner<FlowBean, Text> {
    @Override
    public int getPartition(FlowBean flowBean, Text text, int numPartitions) {
        String phone = text.toString();
        String prefix = phone.substring(0, 3);
        int partition = 0;
        if ("136".equals(prefix)) {
            partition = 0;
        } else if ("137".equals(prefix)) {
            partition = 1;
        } else if ("138".equals(prefix)) {
            partition = 2;
        } else if ("139".equals(prefix)) {
            partition = 3;
        } else {
            partition = 4;
        }
        return partition;
    }
}
