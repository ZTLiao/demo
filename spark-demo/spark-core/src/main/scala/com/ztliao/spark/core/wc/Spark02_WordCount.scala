package com.ztliao.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/9 13:56
 * @description:
 */
object Spark02_WordCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("WordCount")
    val sc = new SparkContext(sparkConf)
    val lines = sc.textFile("spark-demo/datas")
    val words = lines.flatMap(_.split(" "))
    val wordToOne = words.map {
      word => (word, 1)
    }
    val wordGroup = wordToOne.groupBy(
      t => t._1
    )

    val wordToCount = wordGroup.map {
      case (word, list) => {
        list.reduce(
          (t1, t2) => {
            (t1._1, t1._2 + t2._2)
          }
        )
      }
    }
    val array = wordToCount.collect()
    array.foreach(println)
    sc.stop()
  }
}
