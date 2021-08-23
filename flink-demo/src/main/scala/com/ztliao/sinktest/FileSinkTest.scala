package com.ztliao.sinktest

import com.ztliao.apitest.SensorReading
import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.scala._

/**
  * @author: liaozetao
  * @date: 2021/8/21 1:49 PM
  * @description:
  */
object FileSinkTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })

    dataStream.print()
    //dataStream.writeAsCsv("/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/out.txt")
    dataStream.addSink(StreamingFileSink.forRowFormat(
      new Path("/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/out.txt"),
      new SimpleStringEncoder[SensorReading]()
    ).build())
    env.execute("file sink test")
  }
}
