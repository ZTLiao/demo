package com.ztliao.chapter06.method

/**
  * @author: liaozetao
  * @date: 2021/8/23 10:34 PM
  * @description:
  */
object MethodDemo02 {

  def main(args: Array[String]): Unit = {
    val m = new MethodExec
    m.printExec()
    m.witdh = 2.0
    m.len = 1.0
    m.area()
  }
}

class MethodExec {

  var len = 0.0
  var witdh = 0.0

  def printExec() {
    for (i <- 0 until 10) {
      for (j <- 0 until 10) {
        println("*")
      }
      println()
    }
  }

  def area(): Double = {
    (this.len * this.witdh).formatted("%.2f").toDouble
  }
}