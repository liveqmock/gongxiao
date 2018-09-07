package com.yhglobal.gongxiao.warehouseapi.inbound;

/**
 *  wms入库通知单 确认信息 响应
 */
public class ConfirmWmsOutboundRespond {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
