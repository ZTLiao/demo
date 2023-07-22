package com.ztliao.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

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
        configuration.set("dfs.replication", "2");
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

    public void testPut() throws IOException {
        //参数解读 参数一：删除原数据；参数二：是否覆盖；参数三：原数据路径；参数四：目的地路径；
        fs.copyFromLocalFile(false, false, new Path("/Users/liaozetao/IdeaProjects/demo/hadoop-demo/src/main/java/com/ztliao/hdfs/HdfsClient.java"), new Path("hdfs://hadoop001/xiyou/huaguoshan"));
    }

    public void testGet() throws IOException {
        //参数解读 参数一：原文件是否删除；参数二：原文件的路径；参数三：目标地址路径；
        fs.copyToLocalFile(false, new Path("/xiyou/huaguoshan"), new Path("/Users/liaozetao/IdeaProjects/"), false);
    }

    public void testRm() throws IOException {
        //参数解读 参数一：删除的路径；参数二：是否要递归删除
        fs.delete(new Path("/xiyou/huaguoshan"), false);
        //删除空目录
        fs.delete(new Path("/xiyou"), false);
        //删除非空目录
        fs.delete(new Path("/jinguo"), true);
    }

    public void testMv() throws IOException {
        //参数解读 参数一：原文件路径；参数二：目标路径；
        fs.rename(new Path("/xiyou"), new Path("/xinxiyou"));
        //文件移动和更名
        fs.rename(new Path("/input/ss.txt"), new Path("/cls.txt"));
        //目录更名
        fs.rename(new Path("/input"), new Path("/output"));
    }

    public void findDetail() throws IOException {
        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(new Path("/"), true);
        while (iterator.hasNext()) {
            LocatedFileStatus fileStatus = iterator.next();
            System.out.println("====" + fileStatus.getPath() + "====");
            System.out.println(fileStatus.getPermission());
            System.out.println(fileStatus.getOwner());
            System.out.println(fileStatus.getGroup());
            System.out.println(fileStatus.getLen());
            System.out.println(fileStatus.getModificationTime());
            System.out.println(fileStatus.getReplication());
            System.out.println(fileStatus.getBlockSize());
            System.out.println(fileStatus.getPath().getName());
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            System.out.println(Arrays.toString(blockLocations));
        }
    }

    public void testFile() throws IOException {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            String name = fileStatus.getPath().getName();
            if (fileStatus.isFile()) {
                System.out.println("fileName : " + name);
            } else {
                System.out.println("dirName : " + name);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        HdfsClient hdfsClient = new HdfsClient();
        hdfsClient.init();
        try {
            //创建目录
            hdfsClient.testMkdir();
            //上传文件
            hdfsClient.testPut();
            //下载文件
            hdfsClient.testGet();
            //删除文件
            hdfsClient.testRm();
            //重命名
            hdfsClient.testMv();
            //详情
            hdfsClient.findDetail();
            //
            hdfsClient.testFile();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            hdfsClient.close();
        }
    }
}
