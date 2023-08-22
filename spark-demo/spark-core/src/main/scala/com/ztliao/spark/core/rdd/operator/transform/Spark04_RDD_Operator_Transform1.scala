package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark04_RDD_Operator_Transform1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      "Hello Scala", "Hello Spark"
    ))
    val flatRDD = rdd.flatMap(s => {
      s.split(" ")
    })
    flatRDD.collect().foreach(println)
    sc.stop()
  }

}
