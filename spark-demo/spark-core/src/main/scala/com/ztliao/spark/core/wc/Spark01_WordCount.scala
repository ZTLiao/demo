package com.ztliao.spark.core.wc

import org.apache.spark.{SparkConf, SparkContext}

object Spark01_WordCount {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("WordCount")
    val sc = new SparkContext(sparkConf)
    val lines = sc.textFile("spark-demo/datas")
    val words = lines.flatMap(_.split(" "))
    val wordGroup = words.groupBy(word => word)
    val wordToCount = wordGroup.map {
      case (word, list) => {
        (word, list.size)
      }
    }
    val array = wordToCount.collect()
    array.foreach(println)
    sc.stop()
  }
}