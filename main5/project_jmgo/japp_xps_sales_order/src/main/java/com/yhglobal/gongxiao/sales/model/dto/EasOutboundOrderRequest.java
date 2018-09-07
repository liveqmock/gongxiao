package com.yhglobal.gongxiao.sales.model.dto;

import com.yhglobal.gongxiao.eas.model.SaleOutOrder;
import com.yhglobal.gongxiao.eas.model.SaleOutOrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * eas销售出库单请求
 * @author 葛灿
 */
public class EasOutboundOrderRequest implements Serializable {

    private int errorCode;
    private SaleOutOrder easSalesOutboundOrder;
    private List<SaleOutOrderItem> easSalesOutboundItems;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public SaleOutOrder getEasSalesOutboundOrder() {
        return easSalesOutboundOrder;
    }

    public void setEasSalesOutboundOrder(SaleOutOrder easSalesOutboundOrder) {
        this.easSalesOutboundOrder = easSalesOutboundOrder;
    }

    public List<SaleOutOrderItem> getEasSalesOutboundItems() {
        return easSalesOutboundItems;
    }

    public void setEasSalesOutboundItems(List<SaleOutOrderItem> easSalesOutboundItems) {
        this.easSalesOutboundItems = easSalesOutboundItems;
    }
}
