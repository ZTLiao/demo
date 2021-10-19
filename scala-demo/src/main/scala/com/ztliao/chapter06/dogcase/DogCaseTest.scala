package com.ztliao.chapter06.dogcase

/**
  * @author: liaozetao
  * @date: 2021/8/26 8:38 PM
  * @description:
  */
object DogCaseTest {

  def main(args: Array[String]): Unit = {
    val dog = new Dog
    dog.say()
  }
}

class Dog{

  var name = ""
  var age = 0
  var weight = 0
  def say(): String = {
    this.name + "" + this.age
  }
}
