package com.yhglobal.gongxiao.constant.sales;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 订单属性枚举类
 *
 * @author weizecheng
 * @date 2018/9/3 9:13
 */
public enum  SalesOrderAttributeEnum {

    /**
     * 新品订单属性
     */
    NEW_SALES_ORDER("N","新品订单"),
    /**
     * 自动补货订单属性
     */
    AUTOMATIC_REPLENISHMENT_ORDER("A","自动补货单"),
    /**
     * 手动补货订单属性
     */
    MANUAL_REPLENISHMENT_ORDER("M","手动补货单"),
    /**
     * 手工补货补给单
     */
    MANUAL_REPLENISHMENT_SUPPLY_ORDER("QM","手工补货补给单"),
    /**
     * 新品订单补给单
     */
    NEW_SUPPLY_SALES_ORDER("QN","新品订单补给单"),
    /**
     * 自动补货补给单
     */
    AUTOMATIC_REPLENISHMENT_SUPPLY_ORDER("QA","自动补货补给单"),
    /**
     * 问题区补单
     */
    PROBLEM_AREAS_REPLENISHMENT_ORDER("Q","问题区补单"),
    /**
     * 有单备货补给单
     */
    PROBLEM_AREAS_REPLENISHMENT_SUPPLY_ORDER("QR","有单备货补给单"),
    /**
     *加急订单(仅限图书)
     */
    urgent_sales_order("U","加急订单(仅限图书)");

    private String status;

    private String information;

    SalesOrderAttributeEnum(String status, String information) {
        this.status = status;
        this.information = information;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    private final static Map<String, SalesOrderAttributeEnum> EnumMap = new ConcurrentHashMap<String, SalesOrderAttributeEnum>();


    static {
        // 如需要多个初始化 则加入对应方法
        init();
    }

    private static void init() {
        for (SalesOrderAttributeEnum purchaseStatus : SalesOrderAttributeEnum.values()) {
            EnumMap.put(purchaseStatus.status, purchaseStatus);
        }
    }

    /**
     * 通过状态获取文字描述信息
     *
     * @param code 状态编码
     * @return 描述信息
     */
    public static String getMessage(String code) {
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
    public static SalesOrderAttributeEnum getEnum(String code) {
        return EnumMap.get(code);
    }


}
