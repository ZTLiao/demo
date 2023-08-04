package com.ztliao.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        Date startTime = convertStrToDate(LocalDate.now().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)) + " 00:00:00", "yyyy-MM-dd");
        System.out.println(startTime);
    }

    public static Date convertStrToDate(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
