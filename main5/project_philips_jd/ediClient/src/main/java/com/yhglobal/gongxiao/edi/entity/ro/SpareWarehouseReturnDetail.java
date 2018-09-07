package com.yhglobal.gongxiao.edi.entity.ro;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author 葛灿
 */
public class SpareWarehouseReturnDetail {

    /**
     * 数据标识
     */
    @XmlElement(name = "DataLable")
    private String dataLable;
    /**
     * 单据号码
     */
    @XmlElement(name = "DocumentNumber")
    private String documentNumber;
    /**
     * 当前记录数
     */
    @XmlElement(name = "CurrentRecordNumber")
    private Integer currentRecordNumber;
    /**
     * 供货商商品ID
     */
    @XmlElement(name = "VendorProductID")
    private String vendorProductID;
    /**
     * 采购商商品ID
     */
    @XmlElement(name = "BuyerProductID")
    private String buyerProductID;
    /**
     * 商品编码
     */
    @XmlElement(name = "ProductCode")
    private String productCode;
    /**
     * 商品名称
     */
    @XmlElement(name = "ProductName")
    private String productName;
    /**
     * 退货数量
     */
    @XmlElement(name = "Quantity")
    private Integer quantity;
    /**
     * 收货数量
     */
    @XmlElement(name = "ReceivingQuantity")
    private Integer receivingQuantity;
    /**
     * 返回价格
     */
    @XmlElement(name = "ReturnPrice")
    private String returnPrice;

    @XmlTransient
    public String getReturnPrice() {
        return returnPrice;
    }

    @XmlTransient
    public String getDataLable() {
        return dataLable;
    }

    @XmlTransient
    public String getDocumentNumber() {
        return documentNumber;
    }

    @XmlTransient
    public Integer getCurrentRecordNumber() {
        return currentRecordNumber;
    }

    @XmlTransient
    public String getVendorProductID() {
        return vendorProductID;
    }

    @XmlTransient
    public String getBuyerProductID() {
        return buyerProductID;
    }

    @XmlTransient
    public String getProductCode() {
        return productCode;
    }

    @XmlTransient
    public String getProductName() {
        return productName;
    }

    @XmlTransient
    public Integer getQuantity() {
        return quantity;
    }

    @XmlTransient
    public Integer getReceivingQuantity() {
        return receivingQuantity;
    }


    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setCurrentRecordNumber(Integer currentRecordNumber) {
        this.currentRecordNumber = currentRecordNumber;
    }

    public void setVendorProductID(String vendorProductID) {
        this.vendorProductID = vendorProductID;
    }

    public void setBuyerProductID(String buyerProductID) {
        this.buyerProductID = buyerProductID;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setReceivingQuantity(Integer receivingQuantity) {
        this.receivingQuantity = receivingQuantity;
    }

    public void setReturnPrice(String returnPrice) {
        this.returnPrice = returnPrice;
    }
}
