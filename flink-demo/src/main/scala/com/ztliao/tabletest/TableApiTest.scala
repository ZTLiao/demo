package com.ztliao.tabletest

import com.ztliao.apitest.SensorReading
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.{DataTypes, EnvironmentSettings, Table, TableEnvironment}
import org.apache.flink.table.api.bridge.scala._
import org.apache.flink.table.descriptors._

/**
  * @author: liaozetao
  * @date: 2021/8/23 7:58 PM
  * @description:
  */
object TableApiTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    val tableEnv = StreamTableEnvironment.create(env)
    //    val settings = EnvironmentSettings.newInstance()
    //      .useOldPlanner()
    //      .inStreamingMode()
    //      .build()
    //    val oldStreamTableEnv = StreamTableEnvironment.create(env, settings)
    //    val batchEnv = ExecutionEnvironment.getExecutionEnvironment
    //    val oldBatchTableEnv = BatchTableEnvironment.create(batchEnv)
    //    val blinkStreamSettings = EnvironmentSettings.newInstance()
    //      .useBlinkPlanner()
    //      .inStreamingMode()
    //      .build()
    //    val blinkStreamTableEnv = StreamTableEnvironment.create(env, blinkStreamSettings)
    //    val blinkBatchSettings = EnvironmentSettings.newInstance()
    //      .useBlinkPlanner()
    //      .inBatchMode()
    //      .build()
    //    val blinkBatchTableEnv = TableEnvironment.create(blinkBatchSettings)

    val filePath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    tableEnv.connect(new FileSystem().path(filePath))
      .withFormat(new OldCsv())
      .withSchema(new Schema()
        .field("id", DataTypes.STRING())
        .field("timestamp", DataTypes.BIGINT())
        .field("temperature", DataTypes.DOUBLE()))
      .createTemporaryTable("inputTable")

    val inputTable: Table = tableEnv.from("inputTable")
    inputTable.toAppendStream[(String, Int, Double)].print()

    tableEnv.connect(new Kafka().version("0.11")
      .topic("sensor")
      .property("zookeeper.connect", "localhost:2181")
      .property("bootstrap.servers", "localhost:9092"))
      .withFormat(new Csv())
      .withSchema(new Schema()
        .field("id", DataTypes.STRING())
        .field("timestamp", DataTypes.BIGINT())
        .field("temperature", DataTypes.DOUBLE()))
      .createTemporaryTable("kafkaInputTable")

    val kafkaInputTable: Table = tableEnv.from("kafkaInputTable")
    kafkaInputTable.toAppendStream[(String, Int, Double)].print()

    val sensorTable = tableEnv.from("inputTable")
    val resultTable = sensorTable
      .select("id, temperature")
      .filter("id == sensor_1")

    val resultSqlTable = tableEnv.sqlQuery(
      """
        |select id, temperature
        |from inputTable
        |where id = 'sensor_1'
      """.stripMargin
    )

    val resultSqlInputTable: Table = tableEnv.from("resultSqlInputTable")
    resultTable.toAppendStream[(String, Long, Double)].print("result")
    resultSqlInputTable.toAppendStream[(String, Double)].print("sql")

    env.execute("table api test")
  }
}
