package com.ztliao.chapter04.mydowhile

/**
  * @author: liaozetao
  * @date: 2021/8/14 9:56 PM
  * @description:
  */
object Demo01 {

  def main(args: Array[String]): Unit = {
    var i = 0
    do{
      printf("hello, world %d\n", i)
      i += 1
    }while(i < 10)
  }
}
