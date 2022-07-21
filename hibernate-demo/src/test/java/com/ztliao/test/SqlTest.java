package com.ztliao.test;

import com.ztliao.conditions.query.QueryWrapper;
import com.ztliao.conditions.wrapper.Wrappers;
import org.junit.Test;

/**
 * @author: liaozetao
 * @date: 2022/7/21 21:42
 * @description:
 */
public class SqlTest {

    @Test
    public void test01() {
        QueryWrapper<String> queryWrapper = Wrappers.<String>query("select * from test where 1 = 1")
                .eq("value", "hello world");
        System.out.println(queryWrapper.getSqlSegment());
        System.out.println(queryWrapper.getConditions());
    }
}
