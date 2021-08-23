package com.ztliao.chapter06.oop

/**
  * @author: liaozetao
  * @date: 2021/8/23 10:16 PM
  * @description:
  */
object PropertyDemo {

  def main(args: Array[String]): Unit = {
    val p1 = new Person
    println("p1 = " + p1)
    println("Name = " + p1.name)
    println("address = " + p1.address)

    val a = new A
    println(" a = " + a.var1)
    println(" a = " + a.var2)
    println(" a = " + a.var1)
    println(" a = " + a.var1)

    var worker1 = new Worker
    println(" worker1 = " + worker1)
    var worker2 = new Worker
    println(" worker2 = " + worker2)
  }
}

class Person {
  var age: Int = 10
  var sal = 9800.0
  var name = null
  var address: String = null
}

class A {
  var var1: String = _
  var var2: Byte = _
  var var3: Int = _
  var var4: Double = _
}

class Worker {
  var name: String = ""
}