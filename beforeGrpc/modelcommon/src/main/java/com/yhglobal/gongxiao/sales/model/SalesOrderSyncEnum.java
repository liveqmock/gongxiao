package com.yhglobal.gongxiao.sales.model;

/**
 * @author 葛灿
 */
public enum  SalesOrderSyncEnum {
    /**
     * 无需同步
     */
    INIT(1, "无需同步"),
    /**
     * 未处理(销售单第一发货时,变为该状态,表示待同步到eas)
     */
    UNHANDLED(2, "未处理"),
    /**
     * 处理中
     */
    HANDLING(3, "处理中"),
    /**
     * 已处理
     */
    HANDLED(4, "已处理"),

    UNKNOWN(5,"未知")
    ;


    private int status;

    private String information;

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    SalesOrderSyncEnum(int status, String information) {
        this.status = status;
        this.information = information;
    }

    public int getStatus() {
        return status;
    }

    public static String getMessage(int code) {
        for (SalesOrderStatusEnum status : SalesOrderStatusEnum.values()) {
            if (status.getStatus() == code) {
                return status.getInformation();
            }
        }
        return null;
    }
}
