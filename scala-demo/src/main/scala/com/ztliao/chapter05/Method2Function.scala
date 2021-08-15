package com.ztliao.chapter05

/**
  * @author: liaozetao
  * @date: 2021/8/15 9:43 AM
  * @description:
  */
object Method2Function {

  def main(args: Array[String]): Unit = {
    val dog = new Dog
    println(dog.sum(10, 20))
    val f1 = dog.sum _
    println(" f1 = " + f1)
    println(" f1 = " + f1(10, 20))

    val f2 = (n1: Int, n2: Int) => {
      n1 + n2
    }
  }


}

class Dog {
  def sum(n1: Int, n2: Int): Unit = {
    n1 + n2
  }
}
