package com.ztliao.chapter03.arithoper

/**
  * @author: liaozetao
  * @date: 2021/8/8 7:09 PM
  * @description:
  */
object Exercise01 {

  def main(args: Array[String]): Unit = {
    val days = 97
    printf("days=%d, days=%d\n", days / 7, days % 7)
    val huashi = 232.5
    val sheshi = 5.0 / 9 * (huashi - 100)
    println("sheshi=%d\n", sheshi.formatted("%.3f"))
  }
}
