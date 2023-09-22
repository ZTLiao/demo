package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark16_RDD_Operator_Transform {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("b", 4)
    ))
    val groupRDD = rdd.groupByKey()
    groupRDD.collect().foreach(println)
    val groupRDD1 = rdd.groupBy(_._1)
    sc.stop()
  }

}
