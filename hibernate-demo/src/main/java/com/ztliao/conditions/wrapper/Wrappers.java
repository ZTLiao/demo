package com.ztliao.conditions.wrapper;

import com.alibaba.fastjson.JSONObject;
import com.ztliao.conditions.query.JsonQueryWrapper;
import com.ztliao.conditions.query.LambdaQueryWrapper;
import com.ztliao.conditions.query.QueryWrapper;
import com.ztliao.conditions.update.LambdaUpdateWrapper;

/**
 * @author: liaozetao
 * @date: 2022/3/22 2:20 PM
 * @description:
 */
public final class Wrappers {

    public static <T> JsonQueryWrapper<T> jsonQuery(String sql, JSONObject parameters) {
        return new JsonQueryWrapper<>(sql, parameters);
    }

    public static <T> JsonQueryWrapper<T> jsonQuery(String sql, Class<T> entityClass, JSONObject parameters) {
        return new JsonQueryWrapper<>(sql, entityClass, parameters);
    }

    public static <T> QueryWrapper<T> query(String sql) {
        return new QueryWrapper<>(sql);
    }

    public static <T> LambdaQueryWrapper<T> lambdaQuery() {
        return new LambdaQueryWrapper<>();
    }

    public static <T> LambdaQueryWrapper<T> lambdaQuery(Class<T> entityClass) {
        return new LambdaQueryWrapper<>(null, entityClass, true);
    }

    public static <T> LambdaQueryWrapper<T> lambdaQuery(String sql) {
        return new LambdaQueryWrapper<>(sql);
    }

    public static <T> LambdaUpdateWrapper<T> lambdaUpdate() {
        return new LambdaUpdateWrapper<>();
    }

}
