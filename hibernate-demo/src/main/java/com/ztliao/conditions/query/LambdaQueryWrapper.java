package com.ztliao.conditions.query;

import com.ztliao.conditions.AbstractLambdaWrapper;

/**
 * @author: liaozetao
 * @date: 2022/3/29 1:16 PM
 * @description:
 */
public class LambdaQueryWrapper<T> extends AbstractLambdaWrapper<T, LambdaQueryWrapper<T>> {

    public LambdaQueryWrapper() {
        super();
    }

    public LambdaQueryWrapper(String sql) {
        super(sql);
    }

    public LambdaQueryWrapper(String sql, Class<T> entityClass, boolean isHql) {
        super(sql, entityClass, isHql);
    }

    @Override
    public String getHqlFormat() {
        return "from %s where 1 = 1";
    }

    @Override
    protected LambdaQueryWrapper<T> instance() {
        //1 = 1是为了避免基础sql工具类方法带and问题
        return new LambdaQueryWrapper<>(ONE_EQ_ONE);
    }

}
