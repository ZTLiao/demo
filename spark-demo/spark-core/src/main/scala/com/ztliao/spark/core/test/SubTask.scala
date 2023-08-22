package com.ztliao.spark.core.test

/**
 * @author: liaozetao
 * @date: 2023/8/14 21:38
 * @description:
 */
class SubTask extends Serializable {

  var datas: List[Int] = _

  var logic: (Int) =>Int = _

  def compute() = {
    datas.map(logic)
  }
}
