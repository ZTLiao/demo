package com.ztliao.chapter03.arithoper

/**
  * @author: liaozetao
  * @date: 2021/8/8 7:05 PM
  * @description:
  */
object Demo01 {

  def main(args: Array[String]): Unit = {
    var r1: Int = 10 / 3
    println("r1 = " + r1)
    var r2: Double = 10 / 3
    printf("%d\n", r2)

    println(10 % 3)
    println(-10 % 3) // a % b = a - a / b * b
    println(-10 % -3)

    var num1 = 10
    num1 += 1
    num1 -= 1
    println(num1)
  }
}
