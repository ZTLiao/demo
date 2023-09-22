package com.ztliao.spark.core.rdd.serial

import com.ztliao.spark.core.rdd.serial.Spark01_RDD_Serial.Search
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/14 13:27
 * @description:
 */
object Spark01_RDD_Serial {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(Array("hello word", "hello spark", "hive", "hadoop"))
    val search = new Search("h")
    search.getMatch1(rdd).collect().foreach(println)
    sc.stop()
  }

  class Search(query: String) extends Serializable {
     def isMatch(s: String): Boolean = {
       s.contains(query)
     }

    def getMatch1(rdd: RDD[String]): RDD[String] = {
      rdd.filter(x => x.contains(query))
    }

    def getMatch2(rdd: RDD[String]): RDD[String] = {
      rdd.filter(x => x.contains(query))
    }
  }
}
