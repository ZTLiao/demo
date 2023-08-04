package com.ztliao.mapreduce.outputformat;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author: liaozetao
 * @date: 2023/7/26 13:37
 * @description:
 */
public class LogRecordWriter extends RecordWriter<Text, NullWritable> {

    private FSDataOutputStream logRecordOut1;

    private FSDataOutputStream logRecordOut2;

    public LogRecordWriter(TaskAttemptContext job) {
        try {
            FileSystem fileSystem = FileSystem.get(job.getConfiguration());
            logRecordOut1 = fileSystem.create(new Path("logRecord1.log"));
            logRecordOut2 = fileSystem.create(new Path("logRecord2.log"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        String log = key.toString();
        if (log.contains("test")) {
            logRecordOut1.writeBytes(log + "\n");
        } else {
            logRecordOut2.writeBytes(log + "\n");
        }
    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStream(logRecordOut1);
        IOUtils.closeStream(logRecordOut2);
    }
}
