package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark04_RDD_Operator_Transform2 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      List(1,2), 3, List(4, 5)
    ))
    val flatRDD = rdd.flatMap {
      case list: List[_] => list
      case dat => List(dat)
    }
    flatRDD.collect().foreach(println)
    sc.stop()
  }

}
