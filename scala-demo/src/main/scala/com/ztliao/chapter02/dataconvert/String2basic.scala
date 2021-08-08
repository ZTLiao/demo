package com.ztliao.chapter02.dataconvert

/**
  * @author: liaozetao
  * @date: 2021/8/8 6:33 PM
  * @description:
  */
object String2basic {

  def main(args: Array[String]): Unit = {
    val d1 = 1.2
    val s1 = d1 + ""
    val s2 = "12"
    val num1 = s2.toInt
    val num2 = s2.toByte

    printf("ok~\n")

    val s3 = "hello"
    printf("%d\n", s3.toInt)

    val s4 = "12.5"
    printf("%d\n", s4.toInt)
  }
}
