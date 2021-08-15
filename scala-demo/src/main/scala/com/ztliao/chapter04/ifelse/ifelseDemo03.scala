package com.ztliao.chapter04.ifelse

import scala.io.StdIn

/**
  * @author: liaozetao
  * @date: 2021/8/12 10:16 PM
  * @description:
  */
object ifelseDemo03 {

  def main(args: Array[String]): Unit = {
    println("请输入成绩")
   val score = StdIn.readDouble()
    if(score == 100){
      println("iphone")
    }else if(score > 80 && score <= 90){
      println("ipad")
    }else{
      println("none")
    }
  }
}
