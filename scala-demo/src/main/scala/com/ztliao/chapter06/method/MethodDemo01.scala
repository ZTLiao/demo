package com.ztliao.chapter06.method

/**
  * @author: liaozetao
  * @date: 2021/8/23 10:32 PM
  * @description:
  */
object MethodDemo01 {

  def main(args: Array[String]): Unit = {
    val dog = new Dog
    dog.say()
  }
}

class Dog{

  private val sal: Double = 0D
  var food: String = _
  def say(): Unit ={
    println("say hi.")
  }
}
