package com.ztliao.conditions.query;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.ztliao.conditions.AbstractWrapper;
import com.ztliao.conditions.enums.SqlKeyword;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author: liaozetao
 * @date: 2022/3/23 9:10 AM
 * @description:
 */
public class JsonQueryWrapper<T> extends AbstractWrapper<T, String, JsonQueryWrapper<T>> {

    private JSONObject parameters;

    private boolean isManual = false;

    private JsonQueryWrapper() {
    }

    public JsonQueryWrapper(String sql, JSONObject parameters) {
        this(sql, null, parameters);
    }

    public JsonQueryWrapper(String sql, Class<T> entityClass, JSONObject parameters) {
        newString(sql);
        this.entityClass = entityClass;
        this.parameters = parameters;
        this.conditions = new HashMap<>();
        if (this.getEntityClass() != null) {
            parperHbmParam(this.getEntityClass(), this.parameters, this.sql, this.conditions);
        }
    }

    /**
     * 启用手动设置值(非json获取)
     *
     * @return
     */
    public JsonQueryWrapper<T> enabledManual() {
        this.isManual = true;
        return typedThis;
    }

    /**
     * 关闭手动设置值(非json获取)
     *
     * @return
     */
    public JsonQueryWrapper<T> disabledManual() {
        this.isManual = false;
        return typedThis;
    }

    public JsonQueryWrapper<T> peek(String column, String key) {
        return peek(column, key, null);
    }

    /**
     * 通过 key 将json数据获取出来作为查询条件 conditions
     *
     * @param column
     * @param key
     * @param clazz
     * @param <E>
     * @return
     */
    public <E> JsonQueryWrapper<T> peek(String column, String key, Class<E> clazz) {
        return peek(true, column, key, clazz);
    }

    public <E> JsonQueryWrapper<T> peek(boolean condition, String column, String key, Class<E> clazz) {
        if (condition) {
            if (clazz != null) {
                this.conditions.put(column, this.parameters.getObject(key, clazz));
            } else {
                this.conditions.put(column, this.parameters.get(key));
            }
        }
        return typedThis;
    }

    @Override
    protected JsonQueryWrapper<T> instance() {
        //1 = 1是为了避免基础sql工具类方法带and问题
        return new JsonQueryWrapper<>(ONE_EQ_ONE, parameters);
    }

    @Override
    protected String columnToString(String column) {
        return column;
    }

    @Override
    protected JsonQueryWrapper<T> doIt(boolean condition, String column, Object val, SqlKeyword sqlKeyword) {
        if (condition) {
            if (isManual) {
                super.doIt(true, column, val, sqlKeyword);
            } else {
                String key = (String) val;
                String operator = sqlKeyword.toLow();
                if (getEntityClass() != null) {
                    parperHbmParam(getEntityClass(), this.parameters, column, key, this.sql, this.conditions, operator);
                } else {
                    parperParam(this.parameters, column, key, this.sql, this.conditions, operator);
                }
            }
        }
        return typedThis;
    }

    @Override
    public JsonQueryWrapper<T> orderBy(boolean condition, boolean isHql, boolean isAsc, String... columns) {
        if (condition && columns != null && columns.length > 0) {
            String orderBy = Arrays.stream(columns).map(this::columnToString).map(v -> v + StrUtil.SPACE + (isAsc ? SqlKeyword.ASC.toLow() : SqlKeyword.DESC.toLow())).reduce((v1, v2) -> v1 + StrUtil.COMMA + v2).get();
            changeQuerySort(this.parameters, this.sql, orderBy, isHql);
        }
        return typedThis;
    }

    @Override
    public JsonQueryWrapper<T> selectCount(String countSql) {
        if (StrUtil.isEmpty(countSql)) {
            if (this.parameters == null || this.parameters.isEmpty()) {
                newString(getSqlCountString(this.sql).toString());
            } else {
                newString(getSqlCountString(this.sql, this.parameters).toString());
            }
        } else {
            newString(getSimpleSqlCountString(this.sql, countSql).toString());
        }
        return typedThis;
    }

    @Override
    public JsonQueryWrapper<T> inSql(boolean condition, String column, String inValue) {
        return super.doIt(condition, column, inValue, SqlKeyword.IN);
    }

    @Override
    public JsonQueryWrapper<T> notInSql(boolean condition, String column, String inValue) {
        return super.doIt(condition, column, inValue, SqlKeyword.NOT_IN);

    }

}
