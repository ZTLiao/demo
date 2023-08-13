package com.ztliao.spark.core.test

import java.net.Socket

/**
 * @author: liaozetao
 * @date: 2023/8/13 13:52
 * @description:
 */
object Driver {

  def main(args: Array[String]): Unit = {
    val client = new Socket("localhost", 9999)
    val out = client.getOutputStream
    out.write(2)
    out.flush()
    out.close()
    client.close()
  }
}
