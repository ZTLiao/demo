package com.ztliao.chapter04.foreach

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:23 PM
  * @description:
  */
object YieldFor {

  def main(args: Array[String]): Unit = {
    val res = for (i <- 1 to 10) yield {
      if (i % 2 == 0) {
        i
      } else {
        "不是偶数"
      }
    }
    println(res)
  }
}
