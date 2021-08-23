package com.ztliao.wc

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._

/**
  * @author: liaozetao
  * @date: 2021/8/17 8:48 PM
  * @description:
  */
object StreamWordCount {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(8)
    val paramTool: ParameterTool = ParameterTool.fromArgs(args)
    val host = paramTool.get("host")
    val port = paramTool.get("port").toInt
    val inputDataStream = env.socketTextStream(host, port)
    val resultDataStream: DataStream[(String, Int)] = inputDataStream
      .flatMap(_.split(" "))
      .filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0)
      .sum(1)
    resultDataStream.print().setParallelism(1)
    env.execute("stream word count")
  }
}
