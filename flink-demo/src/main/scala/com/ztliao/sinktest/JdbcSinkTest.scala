/*
package com.ztliao.sinktest

import java.sql.{Connection, Driver, DriverManager, PreparedStatement}

import com.ztliao.apitest.SensorReading
import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.configuration.Configuration
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.scala._

/**
  * @author: liaozetao
  * @date: 2021/8/21 2:48 PM
  * @description:
  */
object JdbcSinkTest {

  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val inputPath = "/Users/liaozetao/IdeaProjects/demo/flink-demo/src/main/resources/sensor.txt"
    val inputStream = env.readTextFile(inputPath)
    val dataStream = inputStream.map(data => {
      val arr = data.split(",")
      SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
    })

    dataStream.addSink(new MyJdbcSinkFunc())
    env.execute("jdbc sink test")
  }
}

class MyJdbcSinkFunc() extends RichSinkFunction[SensorReading]{

  var conn: Connection = _
  var insertStmt: PreparedStatement = _
  var updateStmt: PreparedStatement = _

  override def open(parameters: Configuration): Unit = {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456")
    insertStmt = conn.prepareStatement("insert into sensor_temp(id, temp) values(?, ?)")
    updateStmt = conn.prepareStatement("update sensor_temp set temp = ? where id = ?")
  }

  override def invoke(in: SensorReading): Unit = {
    updateStmt.setDouble(1, in.temperature)
    updateStmt.setString(2, in.id)
    updateStmt.execute()
    if(updateStmt.getUpdateCount == 0){
      insertStmt.setDouble(1, in.temperature)
      insertStmt.setString(2, in.id)
      insertStmt.execute()
    }
  }

  override def close(): Unit = {
    conn.close()
  }
}
*/
