package com.ztliao.conditions;

import java.io.Serializable;

/**
 * @author: liaozetao
 * @date: 2022/3/22 2:26 PM
 * @description:
 */
@FunctionalInterface
public interface ISqlSegment extends Serializable {

    String getSqlSegment();
}
