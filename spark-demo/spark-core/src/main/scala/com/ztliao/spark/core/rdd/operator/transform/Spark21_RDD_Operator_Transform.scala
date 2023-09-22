package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark21_RDD_Operator_Transform {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))
    val rdd2 = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6)
    ))
    val joinRDD = rdd1.join(rdd2)
    joinRDD.collect().foreach(println)
    sc.stop()
  }

}
