package com.ztliao.chapter03.assignoper

/**
  * @author: liaozetao
  * @date: 2021/8/8 7:34 PM
  * @description:
  */
object Demo01 {

  def main(args: Array[String]): Unit = {
    var num = 2
    num <<= 2
    num >>= 1
    println("num = " + num)

    val res = {
     if(num > 1) "hello, ok" else 100
    }
    println(" res = " = res)
    var a = 10
    var b = 20
    a = a + b
    b = a - b
    a = a - b
    println(" a = %d, b = %d\n", a, b)
  }
}
