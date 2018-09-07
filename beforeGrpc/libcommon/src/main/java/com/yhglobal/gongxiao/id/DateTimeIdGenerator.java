package com.yhglobal.gongxiao.id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DateTimeIdGenerator {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static Map<BizNumberType, Integer> seqMap = new ConcurrentHashMap<>(); //存放各种
    private static Integer seqStartValue = 0; //seq的起始值为0 注: 下一秒会是

    private static LocalDateTime lastLocalDateTime = null; //记录上次生成id的时刻

    private static String idFormat = "%s%s%02d"; //格式为: "前缀 yyyyMMddHHmmss 两位自增序号"

    public static String nextId(BizNumberType bizNumberType) {
        synchronized (bizNumberType) {
            LocalDateTime currentLocalDateTime = LocalDateTime.now();
            Integer seq = seqMap.get(bizNumberType);
            if (lastLocalDateTime == null || seq == null) { //刚启动尚未产生过id 或是该类型第一次产生id
                lastLocalDateTime = currentLocalDateTime;
                seqMap.put(bizNumberType, seqStartValue);
                return String.format(idFormat, bizNumberType.prefix, currentLocalDateTime.format(formatter), seqStartValue);
            }

            long millis = ChronoUnit.MILLIS.between(lastLocalDateTime, currentLocalDateTime);
            if (millis > 1000) { //距离上次产生id已超过1秒
                lastLocalDateTime = currentLocalDateTime;
                seqMap.put(bizNumberType, seqStartValue); //seq置回起始值
                return String.format(idFormat, bizNumberType.prefix, currentLocalDateTime.format(formatter), seqStartValue);
            } else if (millis >= 0) { //距离上次产生id小于1秒 则seq自增
                seq = seq + 1;
                if (seq < 100) { //注: seq没有用光时 不要更新lastLocalDateTime
                    seqMap.put(bizNumberType, seq); //seq置回起始值
                    return String.format(idFormat, bizNumberType.prefix, currentLocalDateTime.format(formatter), seq);
                } else { //需要等待到下一秒
                    currentLocalDateTime = tilNextSecond(lastLocalDateTime.plusSeconds(1)); //等待直到 距离lastLocalDateTime往前走1秒
                    lastLocalDateTime = currentLocalDateTime;
                    seqMap.put(bizNumberType, seqStartValue); //seq置回起始值
                    return String.format(idFormat, bizNumberType.prefix, currentLocalDateTime.format(formatter), seqStartValue);
                }
            } else { //时间后移导致异常
                throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", millis));
            }

        }
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
        for (int i=0; i<103; i++) {
            String id1 = DateTimeIdGenerator.nextId(BizNumberType.PURCHASE_ORDER_NO);
            System.out.println(Thread.currentThread().getName() + ":" + id1);
            String id2 = DateTimeIdGenerator.nextId(BizNumberType.PAYMENT_DISTRIBUTOR_CASH_FLOW);
            System.out.println(Thread.currentThread().getName() + ":" + id2);
        }
    }

}
