package com.ztliao.tabletest

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.DataTypes
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.table.descriptors.{Csv, FileSystem, OldCsv, Schema}

/**
  * @author: liaozetao
  * @date: 2021/8/23 8:50 PM
  * @description:
  */
object FileOutputTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val tableEnv = StreamTableEnvironment.create(env)
    val filePath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    tableEnv.connect(new FileSystem().path(filePath))
      .withFormat(new OldCsv())
      .withSchema(new Schema()
        .field("id", DataTypes.STRING())
        .field("timestamp", DataTypes.BIGINT())
        .field("temperature", DataTypes.DOUBLE()))
      .createTemporaryTable("inputTable")

    val sensorTable = tableEnv.from("inputTable")
    val resultTable = sensorTable
      .select("id, temp")
      .filter("id == sensor_1")

    val aggTable = sensorTable
      .groupBy("id")
      .aggregate("id, id")

    val outputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/out.txt"
    tableEnv.connect(new FileSystem().path(outputPath))
      .withFormat(new Csv())
      .withSchema(new Schema()
        .field("id", DataTypes.STRING())
        .field("timestamp", DataTypes.BIGINT())
        .field("temperature", DataTypes.DOUBLE()))
      .createTemporaryTable("outputTable")

    resultTable.insertInto("outputTable")


    resultTable.toAppendStream[(String, Double)].print("result")

    env.execute("file output test")
  }
}
