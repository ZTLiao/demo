package com.ztliao.chapter05.func

/**
  * @author: liaozetao
  * @date: 2021/8/15 10:05 AM
  * @description:
  */
object FunDemo01 {

  def main(args: Array[String]): Unit = {
    getRes(10, 20, '+')
  }

  def getRes(n1: Int, n2: Int, oper: Char) = {
    if(oper == '+'){
      n1 + n2
    }else if(oper == '-'){
      n1 - n2
    }else {
      null
    }
  }
}
