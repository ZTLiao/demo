package com.ztliao.apitest

import java.util

import org.apache.flink.api.common.functions.{RichFlatMapFunction, RichMapFunction}
import org.apache.flink.api.common.state._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
  * @author: liaozetao
  * @date: 2021/8/22 11:08 AM
  * @description:
  */
object StateTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })

    val alertStream = dataStream.keyBy(_.id)
      //        .flatMap(new TempChangeAlert(10.0))
      .filterWithState[Double]({
      case (data: SensorReading, None) => (false, Some(data.temperature))
      case (data: SensorReading, lastTemp: Some[Double]) => {
        val diff = (data.temperature - lastTemp.get).abs
        if (diff > 10.0) {
          (true, Some(data.temperature))
        } else {
          (false, Some(data.temperature))
        }
      }
    })

    alertStream.print()

    env.execute("state test")
  }
}


class MyRichMapper1 extends RichMapFunction[SensorReading, String] {

  var valueState: ValueState[Double] = _

  lazy val listState: ListState[Int] = getRuntimeContext.getListState(new ListStateDescriptor[Int]("liststate", classOf[Int]))

  lazy val mapState: MapState[String, Double] = getRuntimeContext.getMapState(new MapStateDescriptor[String, Double]("mapstate", classOf[String], classOf[Double]))

  lazy val reduceState: ReducingState[SensorReading] = getRuntimeContext.getReducingState(new ReducingStateDescriptor[SensorReading]("reduringstate", new MyReduceFunction(), classOf[SensorReading]))

  override def open(parameters: Configuration): Unit = {
    valueState = getRuntimeContext.getState(new ValueStateDescriptor[Double]("valuestate", classOf[Double]))
  }

  override def map(in: SensorReading): String = {
    val myV = valueState.value()
    valueState.update(in.temperature)
    listState.add(1)
    val list = new util.ArrayList[Int]()
    list.add(1)
    list.add(2)
    list.add(3)
    listState.addAll(list)
    listState.update(list)
    listState.get()

    mapState.contains("sensor_1")
    mapState.get("sensor_1")
    mapState.put("sensor_1", 2)

    reduceState.get()
    reduceState.add(in)

    in.id
  }
}

class TempChangeAlert(d: Double) extends RichFlatMapFunction[SensorReading, (String, Double, Double)] {

  lazy val lastTempState: ValueState[Double] = getRuntimeContext.getState(new ValueStateDescriptor[Double]("lastTempState", classOf[Double]))


  override def flatMap(in: SensorReading, collector: Collector[(String, Double, Double)]): Unit = {
    val lastTemp = lastTempState.value()
    val diff = (in.temperature - lastTemp).abs
    if (diff > d) {
      collector.collect((in.id, lastTemp, in.temperature))
      lastTempState.update(in.temperature)
    }
  }
}