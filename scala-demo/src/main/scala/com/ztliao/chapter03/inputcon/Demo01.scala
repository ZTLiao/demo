package com.ztliao.chapter03.inputcon

import scala.io.StdIn

/**
  * @author: liaozetao
  * @date: 2021/8/9 10:29 PM
  * @description:
  */
object Demo01 {

  def hi(): Unit = {
    println("hi")
  }

  def main(args: Array[String]): Unit = {
    println("输入姓名")
    val name = StdIn.readLine()
    println("输入年龄")
    val age = StdIn.readLine()
    println("输入薪水")
    val sal = StdIn.readLine()
    printf("name = %s, age = %d, sal = %.2f", name, age, sal)
    Cat.sayHi()
    Cat.sayHello()
    hi
  }
}

object Cat extends AAA {
  def sayHi(): Unit = {
    println("cat miao miao ...")
  }
}

trait AAA {

  def sayHello(): Unit = {
    println("AAA Hello")
  }
}
