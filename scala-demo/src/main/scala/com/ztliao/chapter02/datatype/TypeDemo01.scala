package com.ztliao.chapter02.datatype

/**
  * @author: liaozetao
  * @date: 2021/8/8 5:32 PM
  * @description:
  */
object TypeDemo01 {

  def main(args: Array[String]): Unit = {
    var num1: Int = 10
    println(num1.toDouble + "\t" + num1.toString + 100.toDouble)
    sayHi
    sayHi()

    var isPass = true
    println(isPass.toString)
  }

  def sayHi(): Unit = {
    println("say hi")
  }
}
