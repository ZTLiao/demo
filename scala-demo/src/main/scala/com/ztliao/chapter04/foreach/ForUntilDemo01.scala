package com.ztliao.chapter04.foreach

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:08 PM
  * @description:
  */
object ForUntilDemo01 {

  def main(args: Array[String]): Unit = {
    val start = 1
    val end = 11
    for (i <- start until end) {
      println("hello, world" + i)
    }
  }
}
