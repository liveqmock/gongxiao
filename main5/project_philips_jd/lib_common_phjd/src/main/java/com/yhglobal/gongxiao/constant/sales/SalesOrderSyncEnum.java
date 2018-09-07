package com.yhglobal.gongxiao.constant.sales;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 同步类
 *
 * @author weizecheng
 * @date 2018/9/3 9:21
 */
public enum SalesOrderSyncEnum {
    /**
     * 无需同步
     */
    INIT(1, "无需同步"),
    /**
     * 未处理/待同步
     */
    UNHANDLED(2, "未处理"),
    /**
     * 同步进行中
     */
    HANDLING(3, "处理中"),
    /**
     * 同步完成
     */
    HANDLED(4, "已处理"),

    UNKNOWN(5, "未知");


    private Integer status;

    private String information;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    SalesOrderSyncEnum(Integer status, String information) {
        this.status = status;
        this.information = information;
    }

    public Integer getStatus() {
        return status;
    }

    private final static Map<Integer, SalesOrderSyncEnum> EnumMap = new ConcurrentHashMap<Integer, SalesOrderSyncEnum>();

    static {
        init();
    }

    private static void init() {
        for (SalesOrderSyncEnum purchaseStatus : SalesOrderSyncEnum.values()) {
            EnumMap.put(purchaseStatus.status, purchaseStatus);
        }
    }
    /**
     * 通过状态获取文字描述信息
     *
     * @param code 状态编码
     * @return 描述信息
     */
    public static String getMessage(Integer code) {
        if(EnumMap.containsKey(code)){
            return EnumMap.get(code).getInformation();
        }
        return null;
    }

    /**
     * 通过状态码获取枚举
     *
     * @param code 状态码
     * @return 枚举类
     */
    public static SalesOrderSyncEnum getEnum(Integer code) {
        return EnumMap.get(code);
    }

}
