package com.yhglobal.gongxiao.id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeIdGenerator {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static int seq = 0;

    private static LocalDateTime lastLocalDateTime = null; //记录上次生成id的时刻

    //private static String idFormat = "%s%s%02d"; //格式为: "模块前缀 yyyyMMddHHmmss 两位自增序号"

    private static String idFormat = "XPS_%s_%s%s%02d"; //格式为: "项目前缀 模块前缀 yyyyMMddHHmmss 两位自增序号"

    public static synchronized String nextId(String projectPrefix, BizNumberType bizNumberType) {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();

        if (lastLocalDateTime == null) { //刚启动尚未产生过id
            lastLocalDateTime = currentLocalDateTime;
            return String.format(idFormat, projectPrefix, bizNumberType.prefix, currentLocalDateTime.format(formatter), seq);
        }

        long millis = ChronoUnit.MILLIS.between(lastLocalDateTime, currentLocalDateTime);
        if (millis > 1000) { //距离上次产生id已超过1秒
            lastLocalDateTime = currentLocalDateTime;
            seq = 0;
        } else if (millis >= 0) { //距离上次产生id小于1秒 则seq自增
            //注: seq没有用光的场景下 不要更新lastLocalDateTime
            seq ++;
            if (seq >= 100) { //需要等待到下一秒
                currentLocalDateTime = tilNextSecond(lastLocalDateTime.plusSeconds(1)); //等待直到 距离lastLocalDateTime往前走1秒
                lastLocalDateTime = currentLocalDateTime;
                seq = 0;
            }
        } else { //时间后移导致异常
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds: projectPrefix=%s", millis, projectPrefix));
        }

        return String.format(idFormat, projectPrefix, bizNumberType.prefix, currentLocalDateTime.format(formatter), seq);
    }

    //一直等待 直到时钟走到目标targetLocalDateTime
    //返回值为 跨过目标时间点瞬间 对应的LocalDateTime
    private static LocalDateTime tilNextSecond(LocalDateTime targetLocalDateTime) {
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        while (targetLocalDateTime.isAfter(currentLocalDateTime)) {
            currentLocalDateTime = LocalDateTime.now();
        }
        return currentLocalDateTime;
    }

    public static void main(String[] args) {
        String id = DateTimeIdGenerator.nextId("shaver", BizNumberType.PURCHASE_ORDER_NO);
        System.out.println(Thread.currentThread().getName() + ":" + id.length() + ":" + id);
    }

}
