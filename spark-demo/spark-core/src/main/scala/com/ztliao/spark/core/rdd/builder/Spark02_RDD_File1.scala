package com.ztliao.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/19 10:09
 * @description:
 */
object Spark02_RDD_File1 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.wholeTextFiles("spark-demo/datas")
    rdd.collect().foreach(println)
    sc.stop()
  }
}
