package com.ztliao.apitest

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.KeyedProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.util.Collector

/**
  * @author: liaozetao
  * @date: 2021/8/22 12:43 PM
  * @description:
  */
object ProcessFunctionTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })
    /*.keyBy(_.id)
      .process(new MyKeyedProcessFunction())*/

    val warningStream = dataStream
      .keyBy(_.id)
      .process(new TempIncreWaring(10000L))

    warningStream.print()

    env.execute("process function test")
  }
}

class MyKeyedProcessFunction extends KeyedProcessFunction[String, SensorReading, String] {

  var valueState: ValueState[Double] = _

  override def open(parameters: Configuration): Unit = {
    valueState = getRuntimeContext.getState(new ValueStateDescriptor[Double]("valuestate", classOf[Double]))
  }

  override def processElement(i: SensorReading, context: KeyedProcessFunction[String, SensorReading, String]#Context, collector: Collector[String]): Unit = {
    context.getCurrentKey
    context.timestamp()
    context.timerService().currentWatermark()
    context.timerService().registerEventTimeTimer(context.timestamp() + 6000L)
  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {

  }
}

class TempIncreWaring(interval: Long) extends KeyedProcessFunction[String, SensorReading, String] {

  var lastTempState: ValueState[Double] = _

  lazy val timerTsState = getRuntimeContext.getState(new ValueStateDescriptor[Long]("timerTsState", classOf[Long]))

  override def open(parameters: Configuration): Unit = {
    lastTempState = getRuntimeContext.getState(new ValueStateDescriptor[Double]("lastTempState", classOf[Double]))
  }

  override def processElement(i: SensorReading, context: KeyedProcessFunction[String, SensorReading, String]#Context, collector: Collector[String]): Unit = {
    val lastTemp = lastTempState.value()
    val timerTs = timerTsState.value()

    lastTempState.update(i.temperature)

    if (i.temperature > lastTemp && timerTs == 0) {
      val ts = context.timerService().currentProcessingTime() + interval
      context.timerService().registerProcessingTimeTimer(ts)
      timerTsState.update(ts)
    } else if (i.temperature < lastTemp) {
      context.timerService().deleteProcessingTimeTimer(timerTs)
      timerTsState.clear()
    }
  }

  override def onTimer(timestamp: Long, ctx: KeyedProcessFunction[String, SensorReading, String]#OnTimerContext, out: Collector[String]): Unit = {
    out.collect("传感器" + ctx.getCurrentKey + "的温度连续" + interval / 1000 + "秒连续上升")
    timerTsState.clear()
  }
}
