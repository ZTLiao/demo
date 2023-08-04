package com.ztliao.mapreduce.reduceJoin;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: liaozetao
 * @date: 2023/7/29 09:22
 * @description:
 */
public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Reducer<Text, TableBean, TableBean, NullWritable>.Context context) throws IOException, InterruptedException {
        List<TableBean> orderBeans = new ArrayList<>();
        TableBean pdBean = new TableBean();
        for (TableBean value : values) {
            String flag = value.getFlag();
            if ("order".equals(flag)) {
                TableBean tableBean = new TableBean();
                try {
                    BeanUtils.copyProperties(tableBean, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                orderBeans.add(tableBean);
            } else if ("pd".equals(flag)) {
                try {
                    BeanUtils.copyProperties(pdBean, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (TableBean orderBean : orderBeans) {
            orderBean.setpName(pdBean.getpName());
            context.write(orderBean, NullWritable.get());
        }
    }
}
