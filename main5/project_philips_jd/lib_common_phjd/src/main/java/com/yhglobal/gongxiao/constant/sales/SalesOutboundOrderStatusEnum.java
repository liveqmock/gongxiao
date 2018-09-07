package com.yhglobal.gongxiao.constant.sales;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 出库状态
 *
 * @author weizecheng
 * @date 2018/8/29 11:26
 */
public enum SalesOutboundOrderStatusEnum {

    /**
     * 出库中
     */
    IN_OUTBOUND(1, "出库中"),

    /**
     * 已出库
     */
    OUTBOUND_FINISHED(2, "已出库"),

    /**
     * 已签收
     */
    OUTBOUND_SIGNED(3, "已签收");

    private Integer status;

    private String information;


    SalesOutboundOrderStatusEnum(Integer status, String information) {
        this.status = status;
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private final static Map<Integer, SalesOutboundOrderStatusEnum> EnumMap = new ConcurrentHashMap<Integer, SalesOutboundOrderStatusEnum>();

    static {
        // 如需要多个初始化 则加入对应方法
        init();
    }

    private static void init() {
        for (SalesOutboundOrderStatusEnum purchaseStatus : SalesOutboundOrderStatusEnum.values()) {
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
    public static SalesOutboundOrderStatusEnum getEnum(Integer code) {
        return EnumMap.get(code);
    }

}
