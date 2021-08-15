package com.ztliao.chapter04.ifelse

import scala.io._

/**
  * @author: liaozetao
  * @date: 2021/8/10 10:26 PM
  * @description:
  */
object Demo01 {

  def main(args: Array[String]): Unit = {
    println("输入年龄")
    val age = StdIn.readInt()
    if(age > 18){
      println("age > 18")
    }
    scala.io.StdIn
  }

}
