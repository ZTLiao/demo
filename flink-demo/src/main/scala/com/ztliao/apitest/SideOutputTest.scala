package com.ztliao.apitest

import org.apache.flink.api.common.restartstrategy.RestartStrategies
import org.apache.flink.api.common.restartstrategy.RestartStrategies.RestartStrategyConfiguration
import org.apache.flink.api.common.time.Time
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala._
import org.apache.flink.util.Collector

/**
  * @author: liaozetao
  * @date: 2021/8/22 1:36 PM
  * @description:
  */
object SideOutputTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)

    env.setParallelism(1)
    env.setStateBackend(new FsStateBackend(""))
    env.enableCheckpointing(1000).getCheckpointConfig
      .setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    env.getCheckpointConfig.setCheckpointTimeout(3000)
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(2)
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500L)
    env.getCheckpointConfig.setPreferCheckpointForRecovery(true)
    env.getCheckpointConfig.setTolerableCheckpointFailureNumber(3)

    env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 60000L))
    env.setRestartStrategy(RestartStrategies.failureRateRestart(5, Time.seconds(1000L), Time.minutes(1)))

    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })
      .uid("1")

    val highTempStream = dataStream
      .process(new SplitTempProcessor(30.0))

    highTempStream.print()
    highTempStream.getSideOutput(new OutputTag[(String, Long, Double)]("low")).print("low")

    env.execute("side output test")
  }
}

class SplitTempProcessor(threshold: Double) extends ProcessFunction[SensorReading, SensorReading] {
  override def processElement(i: SensorReading, context: ProcessFunction[SensorReading, SensorReading]#Context, collector: Collector[SensorReading]): Unit = {
    if (i.temperature > threshold) {
      collector.collect(i)
    } else {
      context.output(new OutputTag[(String, Long, Double)]("low"), (i.id, i.timestamp, i.temperature))
    }
  }
}
