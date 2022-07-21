package com.ztliao.conditions.interfaces;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: liaozetao
 * @date: 2022/3/23 9:47 AM
 * @description:
 */
public interface Parameters<Children, R> extends Serializable {

    default Children put(R column, Object val) {
        return put(true, column, val);
    }

    Children put(boolean condition, R column, Object val);

    default Children putAll(Map<String, Object> params) {
        return putAll(true, params);
    }

    Children putAll(boolean condition, Map<String, Object> params);
}
