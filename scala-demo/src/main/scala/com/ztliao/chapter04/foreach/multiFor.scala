package com.ztliao.chapter04.foreach

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:20 PM
  * @description:
  */
object multiFor {

  def main(args: Array[String]): Unit = {
    for(i <- 1 to 3; j <- 1 to 3){
      println(" i = " + i + " j = " + j)
    }

    for(i <- 1 to 3){
      for(j <- 1 to 3){
        println(" i = " + i + " j = " + j)
      }
    }
  }
}
