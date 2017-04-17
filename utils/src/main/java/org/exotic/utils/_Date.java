package org.exotic.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author 高灶顺
 * @date 2016-11-15
 */
public class _Date {

    private static final String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 格式化日期 返回String
     * @param date 日期
     * @param format 日期格式 null:"yyyy-MM-dd HH:mm:ss"
     * @return String 格式后的日期
     */
    public static String formatReturnStr(Date date,String format) {
        if( format == null )
            format = DefaultFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }
    /**
     * 格式化日期 返回Date
     * @param date 日期
     * @param format 日期格式 null="yyyy-MM-dd HH:mm:ss"
     * @return Date 格式后的日期
     */
    public static Date formatReturnDate(Date date,String format) throws ParseException {
        if( format == null )
            format = DefaultFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(sdf.format(date));
    }
	/**
	 * 格式化日期 返回Date
	 * @param date 日期
	 * @param format 日期格式 null="yyyy-MM-dd HH:mm:ss"
	 * @return Date 格式后的日期
	 */
	public static Date formatReturnDate(String date,String format) throws ParseException {
		if( format == null )
			format = DefaultFormat;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(sdf.format(sdf.parse(date)));
	}
	/**
	 * 格式化日期 返回Date
	 * @param date 日期
	 * @param format 日期格式 null="yyyy-MM-dd HH:mm:ss"
	 * @return Date 格式后的日期
	 */
	public static String formatReturnStr(String date,String format) throws ParseException {
		if( format == null )
			format = DefaultFormat;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(sdf.parse(date));
	}
	/**
	 * 系统时间
	 * @param format null:"yyyy-MM-dd HH:mm:ss"
	 * @return 2016-11-15 23:08:55
	 */
	public static String getNowByFormat(String format) throws Exception{
		return formatReturnStr(new Date(),format);
	}
	/**
	 * 日期加减
	 * @param date 日期
	 * @param number 需要加减的天数
	 * @return Date 计算后的日期
	 * @throws ParseException 
	 */
	public static Date dateCount(Date date,int number) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, number);
		return calendar.getTime();
	}
	/**
	 * 测试
	 */
	public static void main(String[] args) throws Exception{
		Date date = new Date();
		System.out.println(formatReturnStr("2016-09-11 22:12:23",null));
	}
}
