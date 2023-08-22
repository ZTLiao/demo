package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark01_RDD_Operator_Transform_Par {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4), 1)
    val mapRDD = rdd.map(num => {
      println("num1 : " + num)
      num
    })
    val mapRDD1 = mapRDD.map(num => {
      println("num2 : " + num)
      num
    })
    mapRDD1.collect().foreach(println)
    sc.stop()
  }

}
