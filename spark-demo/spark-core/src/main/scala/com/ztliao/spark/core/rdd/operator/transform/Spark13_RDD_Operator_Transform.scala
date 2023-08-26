package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark10_RDD_Operator_Transform {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd1 = sc.makeRDD(List(1, 2, 3, 4))
    val rdd2 = sc.makeRDD(List(3, 4, 5, 6))
    val rdd7 = sc.makeRDD(List("3", "4", "5", "6"))
    val rdd3 = rdd1.intersection(rdd2)
    println(rdd3.collect().mkString(","))
    val rdd4 = rdd1.union(rdd2)
    println(rdd4.collect().mkString(","))
    val rdd5 = rdd1.subtract(rdd2)
    println(rdd5.collect().mkString(","))
    val rdd6 = rdd1.zip(rdd2)
    val rdd8 = rdd1.zip(rdd7)
    println(rdd6.collect().mkString(","))
    sc.stop()
  }

}
