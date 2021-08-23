package com.ztliao.apitest

import java.util.{Properties, Random}

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
  * @author: liaozetao
  * @date: 2021/8/21 10:10 AM
  * @description:
  */
case class SensorReading(id: String, timestamp: Long, temperature: Double)

object SourceTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val dataList = List(
      SensorReading("sensor_1", 1, 1),
      SensorReading("sensor_2", 2, 2),
      SensorReading("sensor_3", 3, 3),
      SensorReading("sensor_4", 4, 4)
    )
//    val stream1 = env.fromCollection(dataList)
//    //    env.fromElements(1.0, 35, "hello")
//    stream1.print()
//
//    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
//    val stream2 = env.readTextFile(inputPath)
//    stream2.print()
//
//    val properties = new Properties()
//    properties.setProperty("bootstrap.servers", "localhost:9092")
//    properties.setProperty("group.id", "consumer-group")
//    val stream3 = env.addSource(new FlinkKafkaConsumer[String]("sensor", new SimpleStringSchema(), properties))
//    stream3.print()

    val stream4 = env.addSource(new MySensorSource())
    stream4.print()
    env.execute("source test")
  }
}

class MySensorSource() extends SourceFunction[SensorReading] {

  var running: Boolean = true

  override def run(sourceContext: SourceFunction.SourceContext[SensorReading]): Unit = {
    val rand = new Random()
    var curTemp = 1.to(10).map(i => ("sensor_" + i, rand.nextDouble() * 100))
    while (running) {
      curTemp.map(
        data => (data._1, data._2 + rand.nextGaussian())
      )
      val curTime = System.currentTimeMillis()
      curTemp.foreach(data => sourceContext.collect(SensorReading(data._1, curTime, data._2)))
    }
    Thread.sleep(1000)
  }

  override def cancel(): Unit = {
    running = false
  }
}
