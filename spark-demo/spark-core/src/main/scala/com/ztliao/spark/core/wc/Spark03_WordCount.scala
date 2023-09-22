package com.ztliao.spark.core.wc

import org.apache.spark.rdd.RDD
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
    wordcount1(sc)
    wordcount2(sc)
    wordcount3(sc)
    wordcount4(sc)
    wordcount5(sc)
    wordcount6(sc)
    wordcount7(sc)
    wordcount8(sc)
    wordcount9(sc)
    sc.stop()
  }

  def wordcount1(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val group = words.groupBy(word => word)
    val wordCount = group.mapValues(iter => iter.size)
    println(wordCount)
  }

  def wordcount2(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map((_, 1))
    val group = wordOne.groupByKey()
    val wordCount = group.mapValues(iter => iter.size)
    println(wordCount)
  }

  def wordcount3(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map((_, 1))
    val wordCount = wordOne.reduceByKey(_ + _)
    println(wordCount)
  }

  def wordcount4(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map((_, 1))
    val wordCount = wordOne.aggregateByKey(0)(_ + _, _ + _)
    println(wordCount)
  }

  def wordcount5(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map((_, 1))
    val wordCount = wordOne.foldByKey(0)(_ + _)
    println(wordCount)
  }

  def wordcount6(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map((_, 1))
    val wordCount = wordOne.combineByKey(
      v => v,
      (x: Int, y: Int) => x + y,
      (x: Int, y: Int) => x + y
    )
    println(wordCount)
  }

  def wordcount7(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordOne = words.map((_, 1))
    val wordCount = wordOne.countByKey()
    println(wordCount)
  }

  def wordcount8(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val wordCount = words.countByValue()
    println(wordCount)
  }

  def wordcount9(sc: SparkContext): Unit = {
    val rdd = sc.makeRDD(List("Hello Scala", "Hello Spark"))
    val words = rdd.flatMap(_.split(" "))
    val mapWord = words.map(
      word => {
        Map((word, 1))
      }
    )
    val wordCount = mapWord.reduce((map1, map2) => {
      map2.foreach{
        case (word, count) => {
          var newCount = map1.getOrElse(word, 0L) + count
          map1.updated(word, newCount)
        }
      }
      map1
    })
    println(wordCount)
  }
}
