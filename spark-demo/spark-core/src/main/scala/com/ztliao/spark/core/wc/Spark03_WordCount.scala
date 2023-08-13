package com.ztliao.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/9 21:45
 * @description:
 */
object Spark03_WordCount {

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
    val wordToCount = wordToOne.reduceByKey((x, y) => x + y)
    val array = wordToCount.collect()
    array.foreach(println)
    sc.stop()
  }
}
