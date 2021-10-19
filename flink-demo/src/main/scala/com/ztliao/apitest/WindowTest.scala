package com.ztliao.apitest

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.assigners.{SlidingEventTimeWindows, TumblingEventTimeWindows}
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @author: liaozetao
  * @date: 2021/8/21 4:08 PM
  * @description:
  */
object WindowTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(50)
    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })
      .assignAscendingTimestamps(_.timestamp * 1000L)
      .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor[SensorReading](Time.seconds(3)) {
        override def extractTimestamp(t: SensorReading): Long = t.timestamp * 1000L
      })

    val inputStream1 = env.socketTextStream("localhost", 7777)

    val latetag = new OutputTag[(String, Double, Long)]("late")
    val resultStream = dataStream.map(data => (data.id, data.temperature, data.timestamp))
      .keyBy(_._1)
      //        .window(TumblingEventTimeWindows.of(Time.seconds(15)))
      //        .window(SlidingEventTimeWindows.of(Time.seconds(15), Time.seconds(3)))
     //.countWindow(15)
      .timeWindow(Time.seconds(15))
      .allowedLateness(Time.minutes(1))
      .sideOutputLateData(latetag)
    //        .minBy(1)
        .reduce((curRes, newData) => {
      (curRes._1, curRes._2.min(newData._2), curRes._3)
    })

    resultStream.getSideOutput(latetag).print("late")
    resultStream.print()
    env.execute("window test")
  }
}


class MyReduceFunction extends ReduceFunction[SensorReading]{
  override def reduce(t: SensorReading, t1: SensorReading): SensorReading = SensorReading(t.id, t1.timestamp, t1.temperature.min(t.temperature))
}