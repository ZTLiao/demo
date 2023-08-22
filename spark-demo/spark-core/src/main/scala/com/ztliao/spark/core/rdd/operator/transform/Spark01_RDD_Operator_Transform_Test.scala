package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark01_RDD_Operator_Transform_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.textFile("spark-demo/datas/apache.log")
    val mapRDD = rdd.map(line => {
      val datas = line.split(" ")
      datas(6)
    })
    mapRDD.collect().foreach(println)
    sc.stop()
  }

}
