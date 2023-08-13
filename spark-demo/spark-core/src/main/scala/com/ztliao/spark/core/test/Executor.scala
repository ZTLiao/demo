package com.ztliao.spark.core.test

import java.net.ServerSocket

/**
 * @author: liaozetao
 * @date: 2023/8/13 13:50
 * @description:
 */
object Executor {

  def main(args: Array[String]): Unit = {
    var server = new ServerSocket(9999)
    println("server start,wait for data")
    val client = server.accept()
    val in = client.getInputStream
    val i = in.read()
    println("accept client send data : " + i)
    in.close()
    client.close()
    server.close()
  }
}
