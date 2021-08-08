package com.ztliao.chapter02.datatype

/**
  * @author: liaozetao
  * @date: 2021/8/8 6:08 PM
  * @description:
  */
object UnitNullNothingDemo {


  def main(args: Array[String]): Unit = {
    var res = sayHello
    printf("res=%s\n ", res)

    val dog: Dog = null
    //val char1: Char = null
    printf("ok\n")
  }

  def sayHello(): Unit = {

  }
}


class Dog{

}