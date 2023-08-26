package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark10_RDD_Operator_Transform {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 5, 4, 3, 6, 2), 2)
    //val newRDD = rdd.coalesce(3, true)
    val newRDD = rdd.sortBy(_)
    newRDD.saveAsTextFile("output")
    sc.stop()
  }

}
