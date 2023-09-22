package com.ztliao.spark.core.rdd.operator.action

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/6 13:50
 * @description:
 */
object Spark05_RDD_Operator_Action {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 1), ("a", 3)
    ))
    rdd.saveAsTextFile("output")
    rdd.saveAsObjectFile("output1")
    rdd.saveAsSequenceFile("output2")
    sc.stop()
  }
}
