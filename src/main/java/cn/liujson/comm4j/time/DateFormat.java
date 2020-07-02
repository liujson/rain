package cn.liujson.comm4j.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 日期格式化工具
 * (注：此类线程安全但效率较慢)
 * <p>
 * 日期格式字符含义
 * yyyy:年
 * MM:  月
 * dd:  日
 * hh:  1~12小时制（1-12）
 * HH:  24小时制（0-23）
 * mm:  分
 * ss:  秒
 * S:   毫秒
 * E:   星期几
 * D:   一年中的第几天
 * F:   一个月中的第几个星期（会吧这个月总共过的天数除以七）
 * w:   一年中的第几个星期
 * W:   一个月中的第几个星期（根据实际情况来计算）
 * a:   上下午标识
 * k:   和HH差不多，表示一天24小时制（1-24）
 * K:   和hh差不多，表示天天12小时制（0-11）
 * z:   表示时区
 * <p>
 * @author Liujs
 * @date 2019/9/16
 */
public class DateFormat {

    public static final String yyyy = "yyyy";
    public static final String MM = "MM";
    public static final String dd = "dd";
    public static final String hh = "hh";
    public static final String HH = "HH";
    public static final String mm = "mm";
    public static final String ss = "ss";
    public static final String S = "S";
    public static final String E = "E";
    public static final String D = "D";
    public static final String F = "F";
    public static final String w = "w";
    public static final String W = "W";
    public static final String a = "a";
    public static final String k = "k";
    public static final String K = "K";
    public static final String z = "z";


    /**
     * 格式化日期为指定格式模板的格式
     *
     * @param date    需要格式化的日期
     * @param pattern 日期格式模板
     * @return 格式化后的日期字符串
     */
    public static String format(final Date date, final String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 格式化日期为指定格式模板的格式
     *
     * @param timeMillis 日期时间的毫秒形式
     * @param pattern    日期格式模板
     * @return 格式化后的日期字符串
     */
    public static String format(final long timeMillis, final String pattern) {
        return format(new Date(timeMillis), pattern);
    }

    /**
     * 格式化当前时间为指定格式
     * @param pattern 目标格式
     * @return 格式化后的日期字符串
     */
    public static String format(final String pattern) {
        return format(Calendar.getInstance().getTime(), pattern);
    }

    /**
     * 字符串类型的日期转 Date
     *
     * @param source  日期源
     * @param pattern 日期源的日期格式模板
     * @return 转换成功返回日期 转换失败 返回 null
     */
    public static Date convert(String source, final String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(source);
        } catch (ParseException e) {
            return null;
        }
    }

}
