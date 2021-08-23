package com.ztliao.chapter06.oop

/**
  * @author: liaozetao
  * @date: 2021/8/16 10:25 PM
  * @description:
  */
object CatDemo {

  def main(args: Array[String]): Unit = {
    val cat = new Cat
    cat.name = "xiaobai"
    cat.age = 10
    cat.color = "white"
    println("ok...")
  }
}

class Cat{
  var name: String = ""
  var age: Int = _
  var color: String = _
}

class Dog{
  var name = "jack"
  var lover = new Fish
}

class Fish{}