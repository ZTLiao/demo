package com.ztliao.tabletest

import com.ztliao.apitest.SensorReading
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.Table
import org.apache.flink.table.api.bridge.scala._

/**
  * @author: liaozetao
  * @date: 2021/8/22 5:33 PM
  * @description:
  */
object Example {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })

    val tableEnv = StreamTableEnvironment.create(env)

    val dataTable: Table = tableEnv.fromDataStream(dataStream)

    tableEnv.createTemporaryView("dataTable", dataTable)
    val sql: String = "select id, temperature from dataTable where id = 'sensor_1'"
    val resultSqlTable = tableEnv.sqlQuery(sql)
    val resultTable = dataTable.select("id, temperature")
      .filter("id == 'sensor_1'")
    resultTable.toAppendStream[(String, Double)].print("result")

    env.execute("table api example")
  }
}
