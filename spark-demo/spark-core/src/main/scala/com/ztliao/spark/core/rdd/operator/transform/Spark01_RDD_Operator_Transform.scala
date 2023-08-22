package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark01_RDD_Operator_Transform {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4))
//    def mapFunction(num: Int): Int = {
//      num * 2
//    }
//    val mapRDD = rdd.map(mapFunction)
    val mapRDD = rdd.map(_ * 2)
    mapRDD.collect().foreach(println)
    sc.stop()
  }

}
