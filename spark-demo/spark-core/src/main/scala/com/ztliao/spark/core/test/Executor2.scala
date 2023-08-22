package com.ztliao.spark.core.test

import java.io.ObjectInputStream
import java.net.ServerSocket

/**
 * @author: liaozetao
 * @date: 2023/8/13 13:50
 * @description:
 */
object Executor2 {

  def main(args: Array[String]): Unit = {
    var server1 = new ServerSocket(8888)
    println("server start,wait for data")
    val client1 = server1.accept()
    val in1 = client1.getInputStream
    val objIn1 = new ObjectInputStream(in1)
    val subTask1 = objIn1.readObject().asInstanceOf[SubTask]
    val ints1 = subTask1.compute()
    //val i = in.read()
    println("accept client send data : " + ints1)
    objIn1.close()
    client1.close()
    server1.close()
  }
}
