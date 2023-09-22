package com.ztliao.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/6 13:50
 * @description:
 */
object Spark02_RDD_Operator_Action {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4))
    val i = rdd.reduce(_ + _)
    val ints = rdd.collect()
    println(i)
    println(ints.mkString(","))
    val cnt = rdd.count()
    println(cnt)
    val first = rdd.first()
    println(first)
    val take = rdd.take(3)
    println(take.mkString(","))
    val ints1 = rdd.takeOrdered(3)
    println(ints1.mkString(","))
    sc.stop()
  }
}
