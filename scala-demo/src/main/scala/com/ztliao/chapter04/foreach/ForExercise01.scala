package com.ztliao.chapter04.foreach

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:36 PM
  * @description:
  */
object ForExercise01 {

  def main(args: Array[String]): Unit = {
    val start = 1
    val end = 100
    var count = 0
    var sum = 0
    for (i <- start to end) {
      if (i % 9 == 0) {
        count += 1
        sum += 1
      }
    }
    printf("count=%d, sum=%d\n", count, sum)
    val num = 6
    for (i <- 1 to num) {
      printf("%d + %d = %d\n", i, (num - i), num)
    }

    val list = List(1, 2)

  }
}
