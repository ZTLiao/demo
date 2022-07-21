package com.ztliao.conditions;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: liaozetao
 * @date: 2022/3/23 9:05 AM
 * @description:
 */
public interface ICondition extends Serializable {

    Map<String, Object> getConditions();
}
