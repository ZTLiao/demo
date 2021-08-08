package com.ztliao.chapter01

/**
  * @author: liaozetao
  * @date: 2021/8/7 6:47 PM
  * @description:
  */
object PrintDemo {

  def main(args: Array[String]): Unit = {
    var str1: String = "hello"
    var str2: String = " world!"
    println(str1 + str2)
    var name: String = "tom"
    var age: Int = 10
    var sal: Float = 10.67f
    var height: Double = 180.15
    printf("name=%s age=%d sal=%.2f height=%.3f\n", name, age, sal, height)
    println(s"information : name=$name\n age=$age\n sal=$sal\n")
    println(s"information : name=${name}\n age=${age + 10}\n sal=${sal * 100}\n")
  }
}
