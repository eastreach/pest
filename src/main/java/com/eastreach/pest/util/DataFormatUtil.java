package com.eastreach.pest.util;

import com.google.common.collect.Sets;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据格式化工具
 **/
public class DataFormatUtil {

    /**
     * 数据格式校验
     */
    public static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean matchTelephoneFormat(String str) {
        return match("^[1][0-9]{10}$", str);
    }

    public static boolean matchDateFormat(String str) {
        return match("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))", str);
    }

    public static boolean matchDateTimeFormat(String str) {
        return match("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}", str);
    }

    /**
     * 判断指定日期是否在指定星期内
     */
    public static boolean matchWeek(DateTime dateTime, String weekList) {
        Set<Integer> setWeekDay = Sets.newHashSet();
        if (weekList.contains("一")) {
            setWeekDay.add(1);
        }
        if (weekList.contains("二")) {
            setWeekDay.add(2);
        }
        if (weekList.contains("三")) {
            setWeekDay.add(3);
        }
        if (weekList.contains("四")) {
            setWeekDay.add(4);
        }
        if (weekList.contains("五")) {
            setWeekDay.add(5);
        }
        if (weekList.contains("六")) {
            setWeekDay.add(6);
        }
        if (weekList.contains("日")) {
            setWeekDay.add(7);
        }
        return setWeekDay.contains(dateTime.getDayOfWeek());
    }

    /**
     * 时间格式解析
     */
    public static DateTime parseDateTime(String dateTime) {
        if (dateTime.contains(".")) {
            dateTime = dateTime.split(".")[0];
        }
        dateTime.replaceAll("/", "-");
        dateTime.replaceAll("T", " ");
        if (!dateTime.contains(" ")) {
            dateTime += " 00:00:00";
        }
        return DateTime.parse(dateTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
