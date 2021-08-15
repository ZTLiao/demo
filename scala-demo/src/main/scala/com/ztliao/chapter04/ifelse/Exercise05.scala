package com.ztliao.chapter04.ifelse

import scala.io.StdIn

/**
  * @author: liaozetao
  * @date: 2021/8/12 10:34 PM
  * @description:
  */
object Exercise05 {

  def main(args: Array[String]): Unit = {
    println("输入月份")
    val month = StdIn.readInt()
    val age = StdIn.readInt()
    val ticket = 60
    if(month >= 4 && month <= 10){
      if(age >= 18 && age <= 60){
        println("ticket = " + ticket)
      }else if(age < 18){
        println("ticket = " + ticket / 2)
      }else {
        println("ticket = " + ticket / 3)
      }
    }
  }
}
