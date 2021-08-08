package com.ztliao.chapter02.datatype

/**
  * @author: liaozetao
  * @date: 2021/8/8 6:01 PM
  * @description:
  */
object CharDemo {

  def main(args: Array[String]): Unit = {
    var char1: Char = 97
    printf("%s\n", char1)

    var char2: Char = '2'
    var num = 10 + char2
    printf("%c\n", num)
  }
}
