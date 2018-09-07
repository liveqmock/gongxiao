package com.yhglobal.gongxiao.eas;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class PurchaseEasInboundModel implements Serializable {
    private PurchaseBasicOrderInbound purchaseBasicOrderInbound ;
    private List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList ;
    private int errorCode ;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public PurchaseBasicOrderInbound getPurchaseBasicOrderInbound() {
        return purchaseBasicOrderInbound;
    }

    public void setPurchaseBasicOrderInbound(PurchaseBasicOrderInbound purchaseBasicOrderInbound) {
        this.purchaseBasicOrderInbound = purchaseBasicOrderInbound;
    }

    public List<PurchaseBasicOrderItemInbound> getPurchaseBasicOrderItemInboundList() {
        return purchaseBasicOrderItemInboundList;
    }

    public void setPurchaseBasicOrderItemInboundList(List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList) {
        this.purchaseBasicOrderItemInboundList = purchaseBasicOrderItemInboundList;
    }
}
