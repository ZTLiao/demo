package com.ztliao.spark.core.rdd.operator.transform

import org.apache.spark.{SparkConf, SparkContext}

import java.text.SimpleDateFormat

/**
 * @author: liaozetao
 * @date: 2023/8/20 13:05
 * @description:
 */
object Spark06_RDD_Operator_Transform_Test {

  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.textFile("spark-demo/datas/apache.log")
    val timeRDD = rdd.map(line => {
      val datas = line.split(" ")
      val times = datas(3)
      val sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
      val date = sdf.parse(times)
      val sdf1 = new SimpleDateFormat("HH")
      val hour = sdf1.format(date)
      (hour, 1)
    }).groupBy(_._1)
    timeRDD.map{
      case (hour, iter) => {
        (hour, iter.size)
      }
    }.collect().foreach(println)
    sc.stop()
  }

}
