package com.ztliao.chapter04.mybreak

import util.control.Breaks._

/**
  * @author: liaozetao
  * @date: 2021/8/14 10:03 PM
  * @description:
  */
object WhileBreak {

  def main(args: Array[String]): Unit = {
    var n = 1

    breakable(
      while(n <= 20){
        n += 1
        if(n == 10){
          break()
        }
      }
    )

    breakable(
      for(i <- 1 to 100){
        if(i == 20){
          break()
        }
      }
    )

  }
}
