package com.ztliao.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/19 10:12
 * @description:
 */
object Spark01_RDD_Memory_Par1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    rdd.collect().foreach(println)
    rdd.saveAsTextFile("output")
    sc.stop()
  }
}
