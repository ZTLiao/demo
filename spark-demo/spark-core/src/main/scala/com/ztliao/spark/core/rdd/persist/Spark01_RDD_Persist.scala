package com.ztliao.spark.core.rdd.persist

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/21 13:19
 * @description:
 */
object Spark01_RDD_Persist {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("WordCount")
    val sc = new SparkContext(sparkConf)
    val list = List("Hello Scala", "Hello Spark")
    val rdd = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map((_, 1))
    val reduceRDD = mapRDD.reduceByKey(_ + _)
    reduceRDD.collect().foreach(println)
    println("======")
    val rdd1 = sc.makeRDD(list)
    val flatRDD1 = rdd1.flatMap(_.split(" "))
    val mapRDD1 = flatRDD1.map((_, 1))
    val groupRDD = mapRDD1.groupByKey()
    groupRDD.collect().foreach(println)
    sc.stop()
  }
}
