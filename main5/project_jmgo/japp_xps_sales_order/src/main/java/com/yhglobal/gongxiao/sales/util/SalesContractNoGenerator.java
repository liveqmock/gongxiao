package com.yhglobal.gongxiao.sales.util;

import io.grpc.internal.AtomicBackoff;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 葛灿
 */
public class SalesContractNoGenerator {
    // 流水号
    private static AtomicInteger count = null;
    // 上次生成合同号的时间 yyyyMMdd
    private static String yearMonthDay = null;

    /**
     * 生成销售合同号
     *
     * @param projectCode     项目编码
     * @param distributorCode 分销商编码
     * @param date            年月日字符串 格式为yyyyMMdd
     * @return 销售合同号 项目编码-客户编码-年月日+流水号
     */
    public static String getContractNo(String projectCode, String distributorCode, String date) {
//        // 初始化参数
//        if (lastUpdateTime == null) {
//            lastUpdateTime = new Date();
//        }
//        // 如果不是同一天,则重置mark为0
//        Date now = new Date();
//        boolean sameDay = DateUtils.isSameDay(lastUpdateTime, now);
//        lastUpdateTime = now;
//        if (!sameDay) {
//            count = 0;
//        }
        // 初始化参数
        if (yearMonthDay == null) {
            count = new AtomicInteger(0);
            yearMonthDay = date;
        }
        // 如果是同一天,流水号加一,否则流水号为0
        boolean sameDay = yearMonthDay.equals(date);
        if (!sameDay) {
            count = new AtomicInteger(0);
            yearMonthDay = date;
        }
        int index = count.getAndIncrement();
//        return projectCode + "-" + distributorCode + "-" + date + "-" + index;
        return date + "-" + index;
    }
}
