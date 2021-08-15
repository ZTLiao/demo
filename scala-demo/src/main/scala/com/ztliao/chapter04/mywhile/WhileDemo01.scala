package com.ztliao.chapter04.mywhile

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:48 PM
  * @description:
  */
object WhileDemo01 {

  def main(args: Array[String]): Unit = {
    var i = 0
    while(i < 10){
      println(" hello, world " + i)
      i += 1
    }
  }
}
