package com.ztliao.chapter05.myexception

/**
  * @author: liaozetao
  * @date: 2021/8/15 11:43 AM
  * @description:
  */
object ExceptionDemo01 {

  def main(args: Array[String]): Unit = {
    try{
      val i = 10 / 0
    }catch {
      case e: ArithmeticException => e.printStackTrace()
      case e: Exception => e.printStackTrace()
    }finally {
      println("finally")
    }
  }
}
