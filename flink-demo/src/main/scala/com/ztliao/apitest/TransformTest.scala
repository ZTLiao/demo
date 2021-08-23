package com.ztliao.apitest

import org.apache.flink.api.common.functions.{FilterFunction, MapFunction, ReduceFunction, RichMapFunction}
import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.functions.ProcessFunction.Context
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
  * @author: liaozetao
  * @date: 2021/8/21 11:23 AM
  * @description:
  */
object TransformTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })


    //dataStream.filter(new MyFilter)
//    val stream1 = dataStream.keyBy(_.id)
//      .minBy("temperatrue")
//    stream1.print()

    val stream2 = dataStream.keyBy(_.id)
      .reduce((curState, newData) => SensorReading(curState.id, newData.timestamp, curState.temperature.min(newData.temperature)))
    stream2.print()

    val highOutputTag = new OutputTag[SensorReading]("high")
    val lowOutputTag = new OutputTag[SensorReading]("low")
    val allOutputTag = new OutputTag[SensorReading]("all")
    val stream3 = dataStream
      .process((value: SensorReading, ctx: Context, out: Collector[SensorReading]) => {
        if (value.temperature > 30.0) {
          ctx.output(highOutputTag, value)
        } else {
          ctx.output(lowOutputTag, value)
        }
        ctx.output(allOutputTag, value)
      })
    val highTempStream = stream3.getSideOutput(highOutputTag)
    val lowTempStream = stream3.getSideOutput(lowOutputTag)
    val allTempStream = stream3.getSideOutput(allOutputTag)

    val waringStream = highTempStream.map(data => (data.id, data.temperature))

    val connectStream = waringStream.connect(lowTempStream)

    val coMapResultStream = connectStream
      .map(waringData => {
        (waringData._1, waringData._2, "warning")
      }, lowTempData => (lowTempData.id, "healthy"))

    coMapResultStream.print("coMap")

    val unionStream = highTempStream.union(lowTempStream, allTempStream)

    env.execute("transform test")
  }
}

class MyReduceFunction extends ReduceFunction[SensorReading] {
  override def reduce(curState: SensorReading, newData: SensorReading): SensorReading = SensorReading(curState.id, newData.timestamp, curState.temperature.min(newData.temperature))
}

class MyFilter extends FilterFunction[SensorReading]{
  override def filter(t: SensorReading): Boolean = {
    t.id.startsWith("sensor_1")
  }
}

class MyMapper extends MapFunction[SensorReading, String]{
  override def map(in: SensorReading): String = in.id + " temperature"
}

class MyRichMapper extends RichMapFunction[SensorReading, String]{

  override def open(parameters: Configuration): Unit = {

  }

  override def map(in: SensorReading): String = in.id + " temperature"
}
