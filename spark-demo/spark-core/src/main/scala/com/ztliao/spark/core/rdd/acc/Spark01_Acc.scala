package com.ztliao.spark.core.rdd.acc

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/27 13:29
 * @description:
 */
object Spark01_Acc {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("Acc")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4))
    val i = rdd.reduce(_ + _)
    println(i)
    var sum = 0
    rdd.foreach(num => {
      sum += num
    })
    println("sum = " + sum)
    sc.stop()
  }
}
