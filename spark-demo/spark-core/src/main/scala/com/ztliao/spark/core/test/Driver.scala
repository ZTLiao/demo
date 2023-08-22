package com.ztliao.spark.core.test

import java.io.ObjectOutputStream
import java.net.Socket

/**
 * @author: liaozetao
 * @date: 2023/8/13 13:52
 * @description:
 */
object Driver {

  def main(args: Array[String]): Unit = {
    val task = new Task()
    val client1 = new Socket("localhost", 9999)
    val out1 = client1.getOutputStream
    val objOut1 = new ObjectOutputStream(out1)
    val subTask1 = new SubTask()
    subTask1.datas = task.datas.take(2)
    subTask1.logic = task.logic
    objOut1.writeObject(subTask1)
    out1.flush()
    out1.close()
    client1.close()
    val client2 = new Socket("localhost", 8888)
    val out2 = client2.getOutputStream
    val objOut2 = new ObjectOutputStream(out2)
    val subTask2 = new SubTask()
    subTask2.datas = task.datas.takeRight(2)
    subTask2.logic = task.logic
    objOut2.writeObject(subTask2)
    out2.flush()
    out2.close()
    client2.close()
  }
}
