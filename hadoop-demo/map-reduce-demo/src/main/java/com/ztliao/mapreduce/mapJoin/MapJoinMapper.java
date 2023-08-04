package com.ztliao.mapreduce.mapJoin;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author: liaozetao
 * @date: 2023/7/29 10:14
 * @description:
 */
public class MapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private final HashMap<String, String> pdMap = new HashMap<>();

    private final Text outK = new Text();

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        URI[] cacheFiles = context.getCacheFiles();
        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        FSDataInputStream fis = fileSystem.open(new Path(cacheFiles[0]));
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        String line;
        while (StringUtils.isNotEmpty(line = reader.readLine())) {
            String[] split = line.split("\t");
            pdMap.put(split[0], split[1]);
        }
        IOUtils.closeStream(reader);
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split("\t");
        String pName = pdMap.get(split[1]);
        String id = split[0];
        int amount = Integer.parseInt(split[2]);
        outK.set(id + "\t" + pName + "\t" + amount);
        context.write(outK, NullWritable.get() );
    }
}
