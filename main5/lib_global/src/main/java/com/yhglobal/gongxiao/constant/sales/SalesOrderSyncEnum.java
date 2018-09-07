package com.yhglobal.gongxiao.constant.sales;

/**
 * @author 葛灿
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


    private int status;

    private String message;

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    SalesOrderSyncEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    /**
     * 通过状态获取文字描述信息
     *
     * @param code 状态编码
     * @return 描述信息
     */
    public static String getMessage(int code) {
        for (SalesOrderSyncEnum status : SalesOrderSyncEnum.values()) {
            if (status.getStatus() == code) {
                return status.getMessage();
            }
        }
        return null;
    }

    /**
     * 通过状态码获取枚举
     *
     * @param code 状态码
     * @return 枚举类
     */
    public static SalesOrderSyncEnum getEnum(int code) {
        for (SalesOrderSyncEnum status : SalesOrderSyncEnum.values()) {
            if (status.getStatus() == code) {
                return status;
            }
        }
        return null;
    }
}
