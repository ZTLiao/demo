package com.ztliao.chapter06.oop

/**
  * @author: liaozetao
  * @date: 2021/8/23 10:26 PM
  * @description:
  */
object MemState {

  def main(args: Array[String]): Unit = {
    val p2 = new Person2
    p2.age = 10
    p2.name = "test"
    println(" p2 = " + p2)
  }
}

class Person2{
  var name = ""
  var age: Int = _
}