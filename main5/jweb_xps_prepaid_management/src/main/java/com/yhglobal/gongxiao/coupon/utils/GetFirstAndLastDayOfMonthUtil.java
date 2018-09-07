package com.yhglobal.gongxiao.coupon.utils;

import com.yhglobal.gongxiao.coupon.model.FirstAndLastDayOfMonth;
import com.yhglobal.gongxiao.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * @author 王帅
 */
public class GetFirstAndLastDayOfMonthUtil {

    public static FirstAndLastDayOfMonth getDay(String dateStr){ // yyyy-MM-dd 格式的日期

        Date date = DateUtil.stringToDate(dateStr);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        final int last = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, last);
        // System.out.println(DateUtil.dateToString(cal.getTime()));

        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        final int last2 = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal2.set(Calendar.DAY_OF_MONTH, last2);
        // System.out.println(DateUtil.dateToString(cal2.getTime()));
        FirstAndLastDayOfMonth day = new FirstAndLastDayOfMonth();
        day.setFirtDate(cal.getTime());
        day.setLastDate(cal2.getTime());

        return day;
    }
}
