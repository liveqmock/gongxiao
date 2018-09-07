package com.yhglobal.gongxiao.warehouseapi.outbound;

/**
 *  wms入库通知单 确认信息 响应
 */
public class ConfirmWmsInboundRespond {
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
