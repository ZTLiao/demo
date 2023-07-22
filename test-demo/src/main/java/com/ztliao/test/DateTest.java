package com.ztliao.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author: liaozetao
 * @date: 2023/7/18 11:10
 * @description:
 */
public class DateTest {

    public static void main(String[] args) {
        DateTime versionTime = DateUtil.parse("2023-07-19 00:00:00", DatePattern.NORM_DATETIME_PATTERN);
        DateTime dateTime = DateUtil.date(new Date());
        System.out.println(dateTime.isAfter(versionTime));
    }
}
