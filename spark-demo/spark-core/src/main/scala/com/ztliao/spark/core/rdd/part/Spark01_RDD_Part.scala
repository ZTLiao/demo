package com.ztliao.spark.core.rdd.part

import org.apache.spark.{Partitioner, SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/27 13:03
 * @description:
 */
object Spark01_RDD_Part {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("WordCount")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      ("nba", "xxxx"),
      ("cba", "xxxx"),
      ("wnba", "xxxx"),
      ("nba", "xxxx"),
    ))
    val partRDD = rdd.partitionBy(new MyPartitioner)
    partRDD.saveAsTextFile("output")
    sc.stop()
  }

  class MyPartitioner extends Partitioner {
    override def numPartitions: Int = 3

    override def getPartition(key: Any): Int = {
      key match {
        case "nba" => 0
        case "wnba" => 1
        case _ => 2
      }
    }
  }
}
