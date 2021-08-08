package com.ztliao.chapter01

/**
  * @author: liaozetao
  * @date: 2021/8/7 6:52 PM
  * @description:
  */
object LookSourceCode {

  def main(args: Array[String]): Unit = {
    var arr = new Array[Int](10)
    for(item <- arr){
      println(s"item = $item")
    }
  }
}
