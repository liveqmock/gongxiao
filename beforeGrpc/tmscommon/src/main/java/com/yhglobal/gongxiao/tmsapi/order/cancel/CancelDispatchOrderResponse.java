package com.yhglobal.gongxiao.tmsapi.order.cancel;

/**
 * TMS系统 对取消运单请求的回复
 */

public class CancelDispatchOrderResponse {

    public boolean success;

    public String message;

    public String errorMsg;

    public String data;

    @Override
    public String toString() {
        return "CancelDispatchOrderResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
