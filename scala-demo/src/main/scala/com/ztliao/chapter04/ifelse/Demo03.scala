package com.ztliao.chapter04.ifelse

/**
  * @author: liaozetao
  * @date: 2021/8/12 10:23 PM
  * @description:
  */
object Demo03 {

  def main(args: Array[String]): Unit = {
    if(5 > 4){
      println("5 > 4")
    }

    val age = 70
    val res = if(age > 20){
      println("hello age > 20")
      9 + 10
      "yes ok"
    }else{
      7
    }
    println("res = " + res)
  }
}
