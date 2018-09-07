package com.yhglobal.gongxiao.edi.entity.po;

import com.yhglobal.gongxiao.edi.entity.common.TransferSubject;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

/**
 * @author 葛灿
 */
@XmlRootElement(name = "ProductCirculationData")
public class PurchaseOrder {
    /**
     * 传送标题
     */
    @XmlElement(name = "TransferSubject")
    private TransferSubject transferSubject;
    /**
     * 采购单据总目
     */
    @XmlElement(name = "PurchaseOrderSummary")
    private PurchaseOrderSummary purchaseOrderSummary;
    /**
     * 采购单据细目
     */
    @XmlElement(name = "PurchaseOrderDetail")
    private List<PurchaseOrderDetail> purchaseOrderDetails;

    @XmlTransient
    public TransferSubject getTransferSubject() {
        return transferSubject;
    }
    @XmlTransient
    public PurchaseOrderSummary getPurchaseOrderSummary() {
        return purchaseOrderSummary;
    }
    @XmlTransient
    public List<PurchaseOrderDetail> getPurchaseOrderDetails() {
        return purchaseOrderDetails;
    }

    public void setTransferSubject(TransferSubject transferSubject) {
        this.transferSubject = transferSubject;
    }

    public void setPurchaseOrderSummary(PurchaseOrderSummary purchaseOrderSummary) {
        this.purchaseOrderSummary = purchaseOrderSummary;
    }

    public void setPurchaseOrderDetails(List<PurchaseOrderDetail> purchaseOrderDetails) {
        this.purchaseOrderDetails = purchaseOrderDetails;
    }
}
