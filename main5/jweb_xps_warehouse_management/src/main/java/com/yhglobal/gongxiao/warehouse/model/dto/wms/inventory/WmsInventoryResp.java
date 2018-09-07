package com.yhglobal.gongxiao.warehouse.model.dto.wms.inventory;


import java.io.Serializable;

/**
 * 库存查询结果
 * @author liukai
 */
public class WmsInventoryResp implements Serializable {
    private InventoryResult result;   //库存明细
    private boolean success;        //是否成功
    private String error;           //
    private boolean unAuthorizedRequest;

    public WmsInventoryResp() {
    }

    public WmsInventoryResp(InventoryResult result, boolean success) {
        this.result = result;
        this.success = success;
    }

    public WmsInventoryResp(InventoryResult result, boolean success, String error, boolean unAuthorizedRequest) {
        this.result = result;
        this.success = success;
        this.error = error;
        this.unAuthorizedRequest = unAuthorizedRequest;
    }

    public InventoryResult getResult() {
        return result;
    }

    public void setResult(InventoryResult result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
