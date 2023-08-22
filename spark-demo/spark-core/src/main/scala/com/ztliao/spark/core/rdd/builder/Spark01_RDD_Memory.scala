package com.ztliao.spark.core.rdd.builder

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/8/19 09:40
 * @description:
 */
object Spark01_RDD_Memory {

  def main(args: Array[String]): Unit = {
    val sparkConf  = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val seq = Seq[Int](1,2,3,4)
    //val rdd = sc.parallelize(seq)
    val rdd = sc.makeRDD(seq)
    rdd.collect().foreach(println)
    sc.stop()
  }
}
