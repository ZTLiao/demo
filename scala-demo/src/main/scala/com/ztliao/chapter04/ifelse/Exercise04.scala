package com.ztliao.chapter04.ifelse

import scala.io.StdIn

/**
  * @author: liaozetao
  * @date: 2021/8/12 10:31 PM
  * @description:
  */
object Exercise04 {

  def main(args: Array[String]): Unit = {
    println("请输入运动员的成绩")
    val speed = StdIn.readDouble()
    if (speed <= 8) {
      println("请输入性别")
      val gender = StdIn.readChar()
      if (gender == '男') {
        println("man")
      } else {
        println("woman")
      }
    } else {
      println("你被淘汰.")
    }
  }
}
