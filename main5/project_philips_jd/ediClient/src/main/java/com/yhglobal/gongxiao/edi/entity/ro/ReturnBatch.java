package com.yhglobal.gongxiao.edi.entity.ro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author 葛灿
 */
public class ReturnBatch {

    /**退货单价*/
    @XmlElement(name = "ReturnPrice")
    private String returnPrice;
    /**退货数量*/
    @XmlElement(name = "ReturnQuantity")
    private Integer returnQuantity;
    /**采购单价*/
    @XmlElement(name = "PurchasePrice")
    private String purchasePrice;
    /**采购单号*/
    @XmlElement(name = "PurchaseOrderCode")
    private String purchaseOrderCode;
    /**采购日期*/
    @XmlElement(name = "PurchaseDate")
    private String purchaseDate;
    /**备注*/
    @XmlElement(name = "Comments")
    private String comments;

    @XmlTransient
    public String getReturnPrice() {
        return returnPrice;
    }
    @XmlTransient
    public Integer getReturnQuantity() {
        return returnQuantity;
    }
    @XmlTransient
    public String getPurchasePrice() {
        return purchasePrice;
    }
    @XmlTransient
    public String getPurchaseOrderCode() {
        return purchaseOrderCode;
    }
    @XmlTransient
    public String getPurchaseDate() {
        return purchaseDate;
    }
    @XmlTransient
    public String getComments() {
        return comments;
    }

    public void setReturnPrice(String returnPrice) {
        this.returnPrice = returnPrice;
    }

    public void setReturnQuantity(Integer returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
