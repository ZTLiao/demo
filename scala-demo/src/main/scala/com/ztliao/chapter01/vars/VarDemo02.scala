package com.ztliao.chapter01.vars

/**
  * @author: liaozetao
  * @date: 2021/8/8 5:18 PM
  * @description:
  */
object VarDemo02 {

  def main(args: Array[String]): Unit = {
    var num = 10
    println(num.isInstanceOf[Int])
    //num = 1.1
    var age = 10
    age = 30
    val num2 = 30
    //    num2 = 40
    val dog = new Dog
    //dog = new Dog
    dog.age = 90
    dog.name = "小火"

  }
}


class Dog {

  var age: Int = _
  var name: String = ""
}