package com.yhglobal.gongxiao.wmscomfirm.model.wms;

import com.yhglobal.gongxiao.wmscomfirm.model.wms.StockOutOrderConfirmItemDto;

import java.io.Serializable;
import java.util.List;

public class StockOutOrderConfirmOrderItemDto implements Serializable {
    private String itemNo;    //行号
    private String itemCode;    //商品编码
    private boolean isCompleted;    //是否成功
    private List<StockOutOrderConfirmItemDto> items;    //属性列表

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public List<StockOutOrderConfirmItemDto> getItems() {
        return items;
    }

    public void setItems(List<StockOutOrderConfirmItemDto> items) {
        this.items = items;
    }
}
