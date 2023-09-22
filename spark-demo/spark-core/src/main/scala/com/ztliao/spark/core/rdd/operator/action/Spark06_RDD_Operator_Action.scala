package com.ztliao.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/6 13:50
 * @description:
 */
object Spark06_RDD_Operator_Action {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      1,2,3,4
    ))
    rdd.collect().foreach(println)
    println("====")
    rdd.foreach(println)
    sc.stop()
  }
}
