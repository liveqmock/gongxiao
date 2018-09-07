package com.yhglobal.gongxiao.purchase.dto;

import java.io.Serializable;

/**
 * 采购回写
 *
 * @author: 陈浩
 * @create: 2018-02-26 16:54
 **/
public class PurchaseWriteBack implements Serializable{
    /**
     * 采购单号
     */
    private String purchaseOrderNo;
    /**
     * 采购单货品id
     */
    private long purchaseItemId;
    /**
     *入库商品数量
     */
    private int inStockQuantity;

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }
}
