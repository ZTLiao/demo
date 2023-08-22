package com.ztliao.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/19 11:26
 * @description:
 */
object Spark02_RDD_File_Par2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.textFile("spark-demo/datas/word.txt", 2)
    rdd.saveAsTextFile("output")
    sc.stop()
  }
}
