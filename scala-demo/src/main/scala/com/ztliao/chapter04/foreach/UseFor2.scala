package com.ztliao.chapter04.foreach

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:28 PM
  * @description:
  */
object UseFor2 {

  def main(args: Array[String]): Unit = {
    for(i <- 1 to 3; j = i * 2){
      println(" i = " + i + " j = " + j)
    }
    println("---------------")
    for{
      i <- 1 to 3
      j = i * 2
    }{
      println(" i = " + i + " j = " + j)
    }
  }
}
