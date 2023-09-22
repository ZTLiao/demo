package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark17_RDD_Operator_Transform1 {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("b", 4)
    ), 2)
    rdd.aggregateByKey(10)(
      (x, y) => math.max(x, y),
      (x, y) => x + y
    ).collect().foreach(println)

    rdd.aggregateByKey(0)(
      (x, y) => x + y,
      (x, y) => x + y
    ).collect().foreach(println)
    rdd.aggregateByKey(0)(
      _ + _, _ + _
    ).collect().foreach(println)
    sc.stop()
  }

}
