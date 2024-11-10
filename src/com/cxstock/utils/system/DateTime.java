package com.cxstock.utils.system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期转换
 * 
 * @author sitanwo
 * 
 */
public class DateTime {

	/**
	 * @param YYYY-MM-DD
	 *            的字符串
	 * @return YYYY-MM-DD 格式的日期
	 */
	public static Date toDate(String date) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = (Date) bartDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * @param YYYY-MM-DD
	 *            的字符串
	 * @return YYYY-MM-DD 格式的日期
	 */
	public static String toString(Date date) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String d = bartDateFormat.format(date);
		return d;
	}
	
	/**
	 * @author sitanwo
	 * @param date
	 *            Date对象
	 * @return YYYY年MM月DD日 格式的日期
	 */
	public static String toChineseString(Date date) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(date);
	}
	
	/**判定某日期是一年第几周：
	 * @param Date mydate  日期参数
	 * oracle数据库中如何查询
	 * select to_char(sysdate,'fmww') from dual; 
	 * select to_char(to_date('20071126','YYYYMMDD'),'fmww') from dual; 
	 * @return int 本年度第几周
	 */
	public static Long getWEEK_OF_YEAR(Date mydate) {
		  Calendar   c   =   Calendar.getInstance()   ;   
		  c.setTime(mydate);   
		  return  new Long(c.get(Calendar.WEEK_OF_YEAR));		
	}
	
	/**判定某日期是一年第几周：
	 * @param Date mydate  日期参数
	 * oracle数据库中如何查询
	 * select to_char(sysdate,'fmww') from dual; 
	 * select to_char(to_date('20071126','YYYYMMDD'),'fmww') from dual; 
	 * @return String 年度-第几周
	 */
	public static String getWEEK_OF_YEAR_STR(Date mydate) {
		  Calendar   c   =   Calendar.getInstance()   ;   
		  c.setTime(mydate);  
		  c.setFirstDayOfWeek(Calendar.MONDAY); //设置一星期的第一天是哪一天
		  int weekofyesr = c.get(Calendar.WEEK_OF_YEAR);
		  String yearnum = new SimpleDateFormat("yyyy").format(new Date());;
		  if(weekofyesr<10){
			  return yearnum+"-0"+weekofyesr;
		  }else{
			  return yearnum+"-"+weekofyesr;	
		  }
		 	
	}
	
	/**
	 * 日期天数加减
	 */
	@SuppressWarnings("static-access")
	public static String getNewDay(String date, int dateValue) { 
		Calendar calObj = Calendar.getInstance(); 
		SimpleDateFormat sfObj = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			calObj.setTime(sfObj.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		calObj.add(calObj.DATE, dateValue); 
		return sfObj.format(calObj.getTime()); 
	}
	
	/**
	 * 计算两个日期之间的周
	 */
	@SuppressWarnings("unchecked")
	public static List getWeeksBetweenDates(Date d1, Date d2) {
		List list = new ArrayList();
		Calendar dateFrom = Calendar.getInstance();
		Calendar dateTo = Calendar.getInstance();
		dateFrom.setTime(d1);
		dateTo.setTime(d2);
		dateFrom.setFirstDayOfWeek(Calendar.MONDAY);  //设置一星期的第一天是周一
		dateTo.setFirstDayOfWeek(Calendar.MONDAY);
		int yearFrom = dateFrom.get(Calendar.YEAR);  //开始日期的年
		int yearTo = dateTo.get(Calendar.YEAR);      //结束日期的年
		int weekFrom = dateFrom.get(Calendar.WEEK_OF_YEAR); //开始日期的周
		int weekTo = dateTo.get(Calendar.WEEK_OF_YEAR);     //结束日期的周
		if(weekFrom==1&&dateFrom.get(Calendar.MONTH)==Calendar.DECEMBER)//如果是第一周并且是12月则为下一年的周一
		{
			yearFrom++;
		}
		if(weekTo==1&&dateTo.get(Calendar.MONTH)==Calendar.DECEMBER)//12月
		{
			yearTo++;
		}
		if(weekFrom<10){
			list.add(yearFrom+"-0"+weekFrom);
		}else{
			list.add(yearFrom+"-"+weekFrom);	
		}
		while(!(yearFrom==yearTo&&weekFrom==weekTo)){
			dateFrom.add(Calendar.WEEK_OF_YEAR, 1);
			weekFrom = dateFrom.get(Calendar.WEEK_OF_YEAR);
			yearFrom = dateFrom.get(Calendar.YEAR);
			if(weekFrom==1&&dateFrom.get(Calendar.MONTH)==Calendar.DECEMBER)//12月
			{
				yearFrom++;
			}
			if(weekFrom<10){
				list.add(yearFrom+"-0"+weekFrom);
			}else{
				list.add(yearFrom+"-"+weekFrom);	
			}
		}		
		return list;
	}
    /**
     * 得到这个月有多少天
     * @param year
     * @param month
     * @return
     */
    public static Integer getDaysForThisMonth(int year,int month){
    	Calendar cal = Calendar.getInstance();   
    	cal.set(year, month-1, 1);
        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//本月多少天
    	return day;
    }
}
