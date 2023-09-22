package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark13_RDD_Operator_Transform1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd1 = sc.makeRDD(List(1, 2, 3, 4, 5, 6), 2)
    val rdd2 = sc.makeRDD(List(3, 4, 5, 6), 4)
    val rdd6 = rdd1.zip(rdd2)
    println(rdd6.collect().mkString(","))
    sc.stop()
  }

}
