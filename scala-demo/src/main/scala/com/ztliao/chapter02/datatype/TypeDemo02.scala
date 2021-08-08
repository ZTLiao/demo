package com.ztliao.chapter02.datatype

/**
  * @author: liaozetao
  * @date: 2021/8/8 5:42 PM
  * @description:
  */
object TypeDemo02 {

  def main(args: Array[String]): Unit = {
    println(sayHello)

    var num = 1.2
    var num2 = 1.7f
    num2 = num.toFloat
  }

  def sayHello: Nothing = {
    throw new Exception("exception")
  }
}
