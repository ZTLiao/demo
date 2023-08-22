package com.ztliao.spark.core.test

/**
 * @author: liaozetao
 * @date: 2023/8/14 13:15
 * @description:
 */
class Task extends Serializable {

  var datas: List[Int] = _

  var logic: (Int) => Int = _


}
