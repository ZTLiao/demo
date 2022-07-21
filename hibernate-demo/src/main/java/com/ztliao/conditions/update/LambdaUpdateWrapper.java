package com.ztliao.conditions.update;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.ztliao.conditions.AbstractLambdaWrapper;
import com.ztliao.util.lambda.SFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: liaozetao
 * @date: 2022/6/6 10:16
 * @description:
 */
public class LambdaUpdateWrapper<T> extends AbstractLambdaWrapper<T, LambdaUpdateWrapper<T>> implements Update<LambdaUpdateWrapper<T>, SFunction<T, ?>> {

    private static final String SQL_SET = "[SQL_SET]";

    private final List<String> sqlSet;

    public LambdaUpdateWrapper() {
        this(null, null, true);
    }

    public LambdaUpdateWrapper(String sql) {
        this(sql, null, false);
    }

    public LambdaUpdateWrapper(String sql, Class<T> entityClass, boolean isHql) {
        super(sql, entityClass, isHql);
        this.sqlSet = new ArrayList<>();
    }

    @Override
    public String getHqlFormat() {
        return "update %s set " + SQL_SET + " where 1 = 1";
    }

    @Override
    protected LambdaUpdateWrapper<T> instance() {
        //1 = 1是为了避免基础sql工具类方法带and问题
        return new LambdaUpdateWrapper<>(ONE_EQ_ONE);
    }

    @Override
    public LambdaUpdateWrapper<T> set(boolean condition, SFunction<T, ?> column, Object val) {
        if (condition) {
            String columnName = columnToString(column);
            String sqlSet = columnName + " = '" + val + "'";
            if (val == null) {
                sqlSet = columnName + " = null";
            }
            this.sqlSet.add(sqlSet);
        }
        return typedThis;
    }

    @Override
    public LambdaUpdateWrapper<T> setSql(boolean condition, String sql) {
        if (condition) {
            //清空
            this.sqlSet.clear();
            this.sqlSet.add(sql);
        }
        return typedThis;
    }

    @Override
    public String getSqlSet() {
        if (this.sqlSet != null && !this.sqlSet.isEmpty()) {
            return CollectionUtil.join(this.sqlSet, StrUtil.COMMA);
        }
        return StrUtil.EMPTY;
    }

    @Override
    public LambdaUpdateWrapper<T> selectCount(String countSql) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LambdaUpdateWrapper<T> orderBy(String orderBy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LambdaUpdateWrapper<T> orderBy(boolean condition, boolean isHql, boolean isAsc, SFunction<T, ?>... columns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public LambdaUpdateWrapper<T> groupBy(boolean condition, SFunction<T, ?>... columns) {
        throw new UnsupportedOperationException();
    }

    public LambdaUpdateWrapper<T> select(SFunction<T, ?>... columns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSqlSegment() {
        if (this.sqlSet.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return super.getSqlSegment().replace(SQL_SET, getSqlSet());
    }

}
