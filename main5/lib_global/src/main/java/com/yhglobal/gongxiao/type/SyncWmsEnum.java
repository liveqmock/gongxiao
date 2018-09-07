package com.yhglobal.gongxiao.type;

/**
 * 同步到WMS的状态
 */
public enum SyncWmsEnum {
    SYNC_WMS_FAIL(1,"同步wms失败"),
    SYNC_WMS_ING(2,"同步wms进行中"),
    SYNC_WMS_SUCCESS(3,"同步wms成功"),
    SYNC_WMS_WAIT(4,"等待审核通过");

    int status;
    String statusDesc;

    SyncWmsEnum(int status, String statusDesc) {
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
