package com.ztliao.chapter05.`lazy`

/**
  * @author: liaozetao
  * @date: 2021/8/15 11:29 AM
  * @description:
  */
object LazyDemo01 {

  def main(args: Array[String]): Unit = {
    lazy val res = sum(10, 20)
  }

  def sum(n1: Int, n2: Int): Int = {
    println("sum...")
    n1 + n2
  }
}
