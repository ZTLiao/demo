package com.ztliao.conditions.query;

import com.ztliao.conditions.AbstractWrapper;

import java.util.HashMap;

/**
 * @author: liaozetao
 * @date: 2022/3/29 9:06 AM
 * @description:
 */
public class QueryWrapper<T> extends AbstractWrapper<T, String, QueryWrapper<T>> {

    private QueryWrapper() {
    }

    public QueryWrapper(String sql) {
        newString(sql);
        this.conditions = new HashMap<>();
    }

    @Override
    protected QueryWrapper<T> instance() {
        //1 = 1是为了避免基础sql工具类方法带and问题
        return new QueryWrapper<>(ONE_EQ_ONE);
    }

    @Override
    protected String columnToString(String column) {
        return column;
    }
}
