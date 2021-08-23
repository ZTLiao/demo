package com.ztliao.chapter06.oop

/**
  * @author: liaozetao
  * @date: 2021/8/23 10:23 PM
  * @description:
  */
object CreateObj {

  def main(args: Array[String]): Unit = {
    val emp = new Emp
    val emp2: Person = new Emp
  }
}

class Person{}

class Emp extends Person{}
