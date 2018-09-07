package com.yhglobal.gongxiao.warehouse.model.dto.wms.inventory;



import java.io.Serializable;
import java.util.List;

/**
 * 库存查询结果明细
 */
public class InventoryResult implements Serializable {
    private List<InventoryResultDetailDto> inventoryResults;
    private boolean success;        //是否成功
    private String message;         //提示信息

    public List<InventoryResultDetailDto> getInventoryResults() {
        return inventoryResults;
    }

    public void setInventoryResults(List<InventoryResultDetailDto> inventoryResults) {
        this.inventoryResults = inventoryResults;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
