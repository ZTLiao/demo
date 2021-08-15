package com.ztliao.chapter05.fundetails

/**
  * @author: liaozetao
  * @date: 2021/8/15 11:17 AM
  * @description:
  */
class VarParameters {

  def main(args: Array[String]): Unit = {
    sum(1, 2, 3, 3)
  }

  def sum(n1: Int, args: Int*): Int ={
    println("args.length = " + args.length)
    var sum = n1
    for(item <- args){
      sum += item
    }
    sum
  }

  def test() = {

  }

}
