package com.ztliao.chapter05.myexception

/**
  * @author: liaozetao
  * @date: 2021/8/15 12:05 PM
  * @description:
  */
object ThrowDemo {

  def main(args: Array[String]): Unit = {
    val res = test()
    println(res.toString)
  }

  def test(): Nothing ={
    throw new Exception("exception...")
  }
}
