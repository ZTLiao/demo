package com.ztliao.spark.core.rdd.io

import com.ztliao.spark.core.rdd.part.Spark01_RDD_Part.MyPartitioner
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author: liaozetao
 * @date: 2023/9/27 13:19
 * @description:
 */
object Spark01_RDD_IO_Save {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf()
      .setMaster("local")
      .setAppName("WordCount")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(
      ("a", 1),
      ("b", 2),
      ("c", 3),
    ))
    rdd.saveAsTextFile("output1")
    rdd.saveAsObjectFile("output2")
    rdd.saveAsSequenceFile("output3")
    sc.stop()
  }
}
