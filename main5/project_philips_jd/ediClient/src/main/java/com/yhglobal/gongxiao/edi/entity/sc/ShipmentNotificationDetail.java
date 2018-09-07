package com.yhglobal.gongxiao.edi.entity.sc;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author 葛灿
 */
public class ShipmentNotificationDetail {

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
    private String currentRecordNumber;
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
     * 商品代码
     */
    @XmlElement(name = "ProductCode")
    private String productCode;
    /**
     * 商品名称
     */
    @XmlElement(name = "ProductName")
    private String productName;
    /**
     * 定价
     */
    @XmlElement(name = "ListPrice")
    private String listPrice;
    /**
     * 数量
     */
    @XmlElement(name = "Quantity")
    private String quantity;
    /**实收数量	*/
    @XmlElement(name = "ReceivingQuantity")
    private String receivingQuantity;
    /**
     * 残损数量
     */
    @XmlElement(name = "DamagedQuantity")
    private String damagedQuantity;
    /**
     * 拒收数量
     */
    @XmlElement(name = "RefuseQuantity")
    private String refuseQuantity;
    /**
     * 采购依据
     */
    @XmlElement(name = "PurchasedBy")
    private String purchasedBy;
    /**
     * 销售单价
     */
    @XmlElement(name = "CostPrice")
    private String costPrice;
    /**
     * 折扣率
     */
    @XmlElement(name = "Discount")
    private String discount;
    /**
     * 包号
     */
    @XmlElement(name = "PackageNumber")
    private String packageNumber;
    /**
     * 差错说明
     */
    @XmlElement(name = "ErrorReason")
    private String errorReason;
    /**
     * 备注
     */
    @XmlElement(name = "Comments")
    private String comments;

    @XmlTransient
    public String getDataLable() {
        return dataLable;
    }
    @XmlTransient
    public String getDocumentNumber() {
        return documentNumber;
    }
    @XmlTransient
    public String getCurrentRecordNumber() {
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
    public String getListPrice() {
        return listPrice;
    }
    @XmlTransient
    public String getQuantity() {
        return quantity;
    }
    @XmlTransient
    public String getReceivingQuantity() {
        return receivingQuantity;
    }
    @XmlTransient
    public String getDamagedQuantity() {
        return damagedQuantity;
    }
    @XmlTransient
    public String getRefuseQuantity() {
        return refuseQuantity;
    }
    @XmlTransient
    public String getPurchasedBy() {
        return purchasedBy;
    }
    @XmlTransient
    public String getCostPrice() {
        return costPrice;
    }
    @XmlTransient
    public String getDiscount() {
        return discount;
    }
    @XmlTransient
    public String getPackageNumber() {
        return packageNumber;
    }
    @XmlTransient
    public String getErrorReason() {
        return errorReason;
    }
    @XmlTransient
    public String getComments() {
        return comments;
    }

    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public void setCurrentRecordNumber(String currentRecordNumber) {
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

    public void setListPrice(String listPrice) {
        this.listPrice = listPrice;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setReceivingQuantity(String receivingQuantity) {
        this.receivingQuantity = receivingQuantity;
    }

    public void setDamagedQuantity(String damagedQuantity) {
        this.damagedQuantity = damagedQuantity;
    }

    public void setRefuseQuantity(String refuseQuantity) {
        this.refuseQuantity = refuseQuantity;
    }

    public void setPurchasedBy(String purchasedBy) {
        this.purchasedBy = purchasedBy;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
