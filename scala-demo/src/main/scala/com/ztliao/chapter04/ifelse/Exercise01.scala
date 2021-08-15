package com.ztliao.chapter04.ifelse

/**
  * @author: liaozetao
  * @date: 2021/8/10 10:32 PM
  * @description:
  */
object Exercise01 {

  def main(args: Array[String]): Unit = {
    val num1 = 10
    val num2 = 2
    val sum = num1 + num2
    if(sum % 3 == 0 && sum % 5 == 0){
      println("能被3又能被5整除")
    }else{
      println("能被3又能被5整除 不成立")
    }

    val year = 2018
    if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0){
      println(s"${year}是闰年")
    }else{
      println(s"${year}不是闰年")
    }
  }
}
