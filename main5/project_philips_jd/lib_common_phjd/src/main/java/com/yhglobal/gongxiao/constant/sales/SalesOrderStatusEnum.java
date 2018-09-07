package com.yhglobal.gongxiao.constant.sales;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 销售订单状态枚举
 *
 * @author weizecheng
 * @date 2018/8/23 14:32
 */
public enum  SalesOrderStatusEnum {
    /**
     * 初始化/待审核
     */
    INIT(1, "待审核"),
    /**
     * 已审核/待收款
     */
    CHECKED(2, "待收款"),
    /**
     * 已收款/待下发仓库
     */
    PAID(3, "待下发仓库"),
    /**
     * 已下发仓库/待仓库处理
     */
    INFORMED(4, "待仓库处理"),
    /**
     * 已出库/待签收
     */
    OUTBOUND(5, "待签收"),
    /**
     * 已签收
     */
    SIGNED(6, "已签收"),
    /**
     * 已取消
     */
    CANCELED(8, "已取消"),
    /**
     * 已驳回
     */
    REJECTED(9, "已驳回");


    private Integer status;

    private String information;

    /**
     * 采用ConcurrentHashMap进行存储
     *
     * @author weizecheng
     * @date 2018/8/31 20:33
     */
    private final static Map<Integer, SalesOrderStatusEnum> EnumMap = new ConcurrentHashMap<Integer, SalesOrderStatusEnum>();

    static {
        init();
    }

    private static void init() {
        for (SalesOrderStatusEnum purchaseStatus : SalesOrderStatusEnum.values()) {
            EnumMap.put(purchaseStatus.status, purchaseStatus);
        }
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    SalesOrderStatusEnum(Integer status, String information) {
        this.status = status;
        this.information = information;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    public static SalesOrderStatusEnum getEnum(Integer code) {
        return EnumMap.get(code);
    }

}
