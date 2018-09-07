package com.yhglobal.gongxiao.warehousemanagement.dto.wms.cancel;

import com.yhglobal.gongxiao.warehousemanagement.dto.wms.Result;

import java.io.Serializable;

/**
 * 取消订单响应
 * @author liukai
 */
public class WmsCanselOrderResp implements Serializable{

    private boolean success;
    private Result result;
    private String error;
    private boolean unAuthorizedRequest;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isUnAuthorizedRequest() {
        return unAuthorizedRequest;
    }

    public void setUnAuthorizedRequest(boolean unAuthorizedRequest) {
        this.unAuthorizedRequest = unAuthorizedRequest;
    }
}
