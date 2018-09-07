package com.yhglobal.gongxiao.constant.purchase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 采购订单枚举
 *
 * @author weizecheng
 * @date 2018/9/5 11:13
 */
public enum  PurchaseStatus {

    /**
     * 编辑状态
     */
    ORDER_IMPORT((byte)1,"编辑状态"),
    /**
     * 采购单提交
     */
    ORDER_SUBMIT((byte)2,"采购单提交"),
    /**
     * 已扣款
     */
    ORDER_CUT_PAYMENT((byte)3,"已扣款"),
    /**
     * 已发货,待入库
     */
    ALREADY_PLAN((byte)70,"已发货,待入库"),
    /**
     * 部分入库
     */
    PART_INBOUND((byte)80,"部分入库"),

    /**
     * 未成功推送给EAS
     */
    SYN_EAS_FAILURE((byte)1,"未成功推送给EAS"),
    /**
     * 成功推送给EAS
     */
    SYN_EAS_SUCCESS((byte)2,"成功推送给EAS"),

    //采退
    /**
     * 出库完成
     */
    PURCHASE_RETURN_COMPLETE((byte) 2,"出库完成"),

    /**
     * 已签收
     */
    PURCHASE_RETURN_SIGNED((byte)3,"已签收"),

    //流水
    /**
     * 正常完成
     */
    ORDER_COMPLETE((byte)90,"正常完成"),

    /**
     * 异常完成,已处理
     */
    EXCEPTION_COMPLETE_HANDLED((byte)91,"异常完成,已处理"),

    /**
     * 异常完成,未处理
     */
    EXCEPTION_COMPLETE_UNHANDLED((byte)92,"异常完成,未处理"),

    /**
     * 生成返利成功
     */
    GENERATE_COUPON((byte)95,"生成返利成功"),

    /**
     * 已取消
     */
    CANCEL((byte)99, "已取消"),
    ;

    private Byte status;

    private String message;

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    PurchaseStatus(Byte status, String message) {
        this.status = status;
        this.message = message;
    }
    /**
     * 存放的ConcurrentHashMap
     *
     * @author weizecheng
     * @date 2018/9/5 11:18
     */
    private final static Map<Byte, PurchaseStatus> EnumMap = new ConcurrentHashMap<Byte, PurchaseStatus>();

    // 初始化
    static {
        init();
    }

    private static void init() {
        for (PurchaseStatus purchaseStatus : PurchaseStatus.values()) {
            EnumMap.put(purchaseStatus.status, purchaseStatus);
        }
    }

    public static PurchaseStatus getPurchaseStatusByCode(Byte code) {
        if(EnumMap.containsKey(code)){
            return EnumMap.get(code);
        }
        return null;
    }

}
