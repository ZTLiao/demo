package com.ztliao.chapter04.mycontinue

/**
  * @author: liaozetao
  * @date: 2021/8/15 9:10 AM
  * @description:
  */
object ContinueDemo {

  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10 if (i != 2 && i != 3)) {
      print(" i = " + i)
    }
    println("---------")
    for (i <- 1 to 10) {
      if (i != 2 && i != 3) {
        print(" i = " + i)
      }
    }
  }
}
