package com.ztliao.chapter02.dataconvert

/**
  * @author: liaozetao
  * @date: 2021/8/8 6:27 PM
  * @description:
  */
object Demo02 {

  def main(args: Array[String]): Unit = {
    val num1: Int = 10 * 3.5.toInt + 6 * 1.5.toInt
    val num2: Int = (10 * 3.5 + 6 * 1.5).toInt
    printf("%d\n", num1 + num2)
    val char1: Char = 1
    val num3 = 1
    //var char2: Char = num3
  }
}
