package com.ztliao.chapter04.foreach

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:04 PM
  * @description:
  */
object ForDemo01 {

  def main(args: Array[String]): Unit = {
    val start = 1
    val end = 10
    for (i <- start to end) {
      println("您好， 世界" + i)
    }

    val list = List("hello", 10, 30, "Tom")
    for (item <- list) {
      println("item = " + item)
    }

  }
}
