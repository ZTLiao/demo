package com.ztliao.chapter04.foreach

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:18 PM
  * @description:
  */
object ForVar {

  def main(args: Array[String]): Unit = {
    for(i <- 1 to 3; j = 4 - i){
      println("j = " + j)
    }
  }
}
