package com.ztliao.wc

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.api.scala._

/**
  * @author: liaozetao
  * @date: 2021/8/16 10:08 PM
  * @description:
  */
object WorkCount {

  def main(args: Array[String]): Unit = {
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment
    val inputPath: String = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/hello.txt"
    val inputDataSet: DataSet[String] = env.readTextFile(inputPath)
    val resultDataSet: DataSet[(String, Int)] = inputDataSet
      .flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)
      .sum(1)
    resultDataSet.print()
  }

}
