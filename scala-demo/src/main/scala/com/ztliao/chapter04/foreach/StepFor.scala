package com.ztliao.chapter04.foreach

import scala.collection.immutable.Range

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:31 PM
  * @description:
  */
object StepFor {

  def main(args: Array[String]): Unit = {
    for (i <- 1 to 10) {
      println(" i = " + i)
    }

    for (i <- Range(1, 10, 2)) {
      println(" i = " + i)
    }

    for (i <- 1 to 10 if i % 2 == 1) {
      println(" i = " + i)
    }
  }
}
