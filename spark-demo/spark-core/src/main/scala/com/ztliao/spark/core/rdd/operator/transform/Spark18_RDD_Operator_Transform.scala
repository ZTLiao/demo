package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark18_RDD_Operator_Transform {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3),
      ("b", 4), ("b", 5), ("a", 6),
    ), 2)
    val aggRDD = rdd.aggregateByKey("")(
      _ + _, _ + _
    )
    aggRDD.collect().foreach(println)
    val newRDD = rdd.aggregateByKey((0, 0))(
      (t, v) => {
        (t._1 + v, t._2 + 1)
      },
      (t1, t2) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    )
    val resultRDD = newRDD.mapValues{
      case (num, cnt) => {
        num / cnt
      }
    }
    resultRDD.collect().foreach(v => println(v))
    sc.stop()
  }

}
