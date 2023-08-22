package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/21 13:18
 * @description:
 */
object Spark01_RDD_Operator_Transform_Part {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    rdd.saveAsTextFile("output")
    val mapRDD = rdd.map(_ * 2)
    mapRDD.saveAsTextFile("output1")
    sc.stop()
  }
}
