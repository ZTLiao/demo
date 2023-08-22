package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark03_RDD_Operator_Transform {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)
    val mapiRDD = rdd.mapPartitionsWithIndex((index, iter) => {
      if (index == 1) {
        iter
      } else {
        Nil.iterator
      }
    })
    mapiRDD.collect().foreach(println)
    sc.stop()
  }

}
