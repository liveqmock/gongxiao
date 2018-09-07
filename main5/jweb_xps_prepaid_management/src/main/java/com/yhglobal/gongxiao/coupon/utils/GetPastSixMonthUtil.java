package com.yhglobal.gongxiao.coupon.utils;

import com.yhglobal.gongxiao.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 王帅
 */
public class GetPastSixMonthUtil {

    public static  List<Date> getSixMonthDate(){

        String month = null;
        String dateString;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        dateString = sdf.format(cal.getTime());
        List<Date> rqList = new ArrayList<>();
        //System.out.println("倒序前\n");
        for (int i = 0; i < 6; i++) {
            dateString = sdf.format(cal.getTime());

           // System.out.println("dateString"+dateString);
            // DateUtil.stringToDate(dateString.substring(0, 7),"yyyy-MM")
            rqList.add(DateUtil.stringToDate(dateString.substring(0, 7),"yyyy-MM"));
//xfzeList.add(xfze);
            cal.add(Calendar.MONTH, -1);
        }
        Collections.reverse(rqList);
        return rqList;

// 倒序
//        Collections.reverse(rqList);
//        System.out.println("倒序后\n");
//        for(int i=0;i<rqList.size();i++){
//            System.out.println("倒序后日期："+rqList.get(i));
//        }
    }
}
