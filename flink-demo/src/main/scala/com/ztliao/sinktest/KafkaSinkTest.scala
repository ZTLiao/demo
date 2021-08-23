/*
package com.ztliao.sinktest

import java.util.Properties

import com.ztliao.apitest.SensorReading
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}

/**
  * @author: liaozetao
  * @date: 2021/8/21 1:58 PM
  * @description:
  */
object KafkaSinkTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    var dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble).toString
    })

    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "consumer-group")
    val stream3 = env.addSource(new FlinkKafkaConsumer[String]("sensor", new SimpleStringSchema(), properties))

    dataStream = stream3.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble).toString
    })

    dataStream.addSink(new FlinkKafkaProducer[String](
      "localhost:9092",
      "sinkTest",
      new SimpleStringSchema()
    ))
    env.execute("kafka sink test")
  }

}
*/
