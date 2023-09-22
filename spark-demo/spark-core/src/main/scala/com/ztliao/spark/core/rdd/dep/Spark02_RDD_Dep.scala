package com.ztliao.spark.core.rdd.dep

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/18 13:18
 * @description:
 */
object Spark02_RDD_Dep {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val lines = sc.textFile("spark-demo/datas/word.txt")
    println(lines.dependencies)
    println("======")
    val words = lines.flatMap(_.split(" "))
    println(words.dependencies)
    println("======")
    val wordToOne = words.map(word => (word, 1))
    println(wordToOne.dependencies)
    println("======")
    val wordToSum = wordToOne.reduceByKey(_ + _)
    println(wordToSum.dependencies)
    println("======")
    wordToSum.collect().foreach(println)
    sc.stop()
  }
}
