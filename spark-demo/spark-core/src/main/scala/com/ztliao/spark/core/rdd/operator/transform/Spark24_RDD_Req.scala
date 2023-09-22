package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark24_RDD_Req {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val dataRDD = sc.textFile("spark-demo/datas/agent.log")
    val mapRDD = dataRDD.map(
      line => {
        val datas = line.split(" ")
        ((datas(1), datas(4)), 1)
      }
    )
    val reduceRDD = mapRDD.reduceByKey(_ + _)
    val newMapRDD = reduceRDD.map {
      case ((prv, ad), sum) => {
        (prv, (ad, sum))
      }
    }
    val groupRDD = newMapRDD.groupByKey()
    val resultRDD = groupRDD.mapValues(
      iter => {
        iter.toList.sortBy(_._2)(Ordering.Int.reverse).take(3)
      }
    )
    resultRDD.collect().foreach(println)
    sc.stop()
  }

}
