package com.ztliao.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: liaozetao
 * @date: 2022/9/18 15:43
 * @description:
 */
public class ThreadTest {

    public static void main(String[] args) {
        String key = "bibi_krypton_rank_5:";
        System.out.println("本季 : " + key + getKeyDate(Calendar.getInstance()));
        System.out.println("上季 : " + key + getLastQuarterlyKeyDate());
        System.out.println(getQuarterRankBeginDate(convertStrToDate("2022-9-21", "yyyy-MM-dd")));

    }

    /**
     * 转换字符串成日期对象
     * @param dateStr 日期字符串
     * @param format 格式，如：yy-MM-dd HH:mm:ss
     * @return
     */
    public static Date convertStrToDate(String dateStr,String format){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        }catch (Exception e) {

        }
        return null;
    }

    public static String getQuarterRankBeginDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        int choiceDate = 1;
        if (day <= 10) choiceDate = 1;
        else if (day > 10 && day <= 20) choiceDate = 11;
        else if (day > 20) choiceDate = 21;
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (choiceDate < 10 ? "0" + choiceDate : choiceDate);
    }

    public static String getKeyDate(Calendar calendar) {
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH) + 1;
        int day = calendar.get(calendar.DAY_OF_MONTH);
        int choiceDate = 1;
        if (day <= 10) choiceDate = 1;
        else if (day > 10 && day <= 20) choiceDate = 11;
        else if (day > 20) choiceDate = 21;
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (choiceDate < 10 ? "0" + choiceDate : choiceDate);
    }

    /**
     * 上一季度的时间
     */
    public static String getLastQuarterlyKeyDate() {
        Calendar calendar = Calendar.getInstance();
        String date = getLastQuarterDate();
        try {
            Date fewDays = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            calendar.setTime(fewDays);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getKeyDate(calendar);
    }

    /**
     * 获取上个季度的临界时间
     */
    public static String getLastQuarterDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(calendar.DAY_OF_MONTH);
        int lastMonth = calendar.get(calendar.MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //三月的第一个季度，由于上一季度的二月是28或29天 所以 要特殊处理
        if (lastMonth == 2 && day <= 10) {
            try {
                Date febDate = sdf.parse(calendar.get(calendar.YEAR) + "-" + calendar.get(calendar.MONTH) + "-21");
                calendar.setTime(febDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if (day > 30) {
                calendar.add(Calendar.DATE, -11);
            } else {
                calendar.add(Calendar.DATE, -10);
            }
        }
        return sdf.format(calendar.getTime());
    }


}
