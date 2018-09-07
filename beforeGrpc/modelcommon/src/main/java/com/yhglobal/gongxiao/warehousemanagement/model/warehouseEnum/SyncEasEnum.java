package com.yhglobal.gongxiao.warehousemanagement.model.warehouseEnum;

/**
 * 入库单同步到EAS的状态
 */
public enum SyncEasEnum {

    SYNC_EAS_FAIL(1,"同步eas失败"),
    SYNC_EAS_ING(2,"同步eas进行中"),
    SYNC_EAS_SUCCESS(3,"同步eas成功"),
    SYNC_EAS_EXCEPTION(4,"同步eas失败,需要人工处理");

    int status;
    String statusDesc;

    SyncEasEnum(int status, String statusDesc) {
        this.status = status;
        this.statusDesc = statusDesc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
