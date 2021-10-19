package com.ztliao.chapter06

/**
  * @author: liaozetao
  * @date: 2021/8/26 8:47 PM
  * @description:
  */
object ConDemo01 {

  def main(args: Array[String]): Unit = {
    val p1 = new Person("jack", 20)
    println(p1)
  }
}


class Person(inName: String, inAge: Int) {
  var name: String = inName
  var age: Int = inAge
}