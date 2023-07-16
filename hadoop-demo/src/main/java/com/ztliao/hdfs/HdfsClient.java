package com.ztliao.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

/**
 * @author: liaozetao
 * @date: 2023/7/16 19:48
 * @description:
 */
public class HdfsClient {

    private static FileSystem fs;

    public void init() throws Exception {
        //链接地址
        URI uri = new URI("hdfs://hadoop001:8020");
        //配置文件
        Configuration configuration = new Configuration();
        //用户
        String user = "bigdata";
        //获取客户端对象
        fs = FileSystem.get(uri, configuration, user);
    }

    public void close() throws Exception {
        fs.close();
    }

    public void testMkdir() throws Exception {
        //创建文件夹
        fs.mkdirs(new Path("/xiyou/huaguoshan"));
        //关闭资源
        fs.close();
    }

    public static void main(String[] args) throws Exception {
        HdfsClient hdfsClient = new HdfsClient();
        hdfsClient.init();
        hdfsClient.testMkdir();
        hdfsClient.close();
    }
}
