package com.ztliao.chapter04.foreach

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:16 PM
  * @description:
  */
object ForGuard {

  def main(args: Array[String]): Unit = {
    for(i <- 1 to 3 if i != 2){
      println(" i = " + i)
    }
  }
}
