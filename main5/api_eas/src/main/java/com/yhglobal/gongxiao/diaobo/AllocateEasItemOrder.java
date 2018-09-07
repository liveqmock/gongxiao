package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;

/**
 * 调拨单明细通知EAS
 */
public class AllocateEasItemOrder implements Serializable {

    private String allocateNo;
    private String productCode;
    private int seq;  //必传
    private double asscoefficient;   //必传
    private double associateQty;   //必传
    private double assistQty;    //必传
    private String materialID;  //必传
    private String unitID;     //必传
    private String baseUnitID;  //必传
    private String issueWarehouseID;  //必传
    private String receiptWarehouseID;  //必传
    private String lot;  //必传
    private double qty;     //必传
    private double baseQty;         //必传
    private String receiptPlanDate;//必传
    private double price;    //必传
    private int isPresent;  //必传
    private double taxPrice;    //必传
    private double taxAmount;  //必传
    private double pruinvoiceAmount;   //必传
    private double saleinvoiceAmount;  //必传
    private double totalAccountPayable;     //必传
    private double totalAccountReceivable;  //必传
    private String issueLocationID;  //必传
    private String receiptLocationID;  //必传
    private String issueStorageOrgUnitID;  //必传
    private String issueCompanyOrgUnitID;    //必传
    private String receiveStorageOrgUnitID;  //必传
    private String receiveCompanyOrgUnitID;  //必传
    private double balancecostPrice;   //必传
    private double balancecostRate;      //必传
    private double actualPrice;  //必传
    private int discountType;     //必传
    private double discountRate;     //必传
    private double actualTaxPrice;   //必传
    private double localAmount;   //必传
    private double localTaxAmount;  //必传
    private double discountAmoumt;   //必传
    private String mfg;//必传
    private String exp;  //必传
    private String issuePlanDate;  //必传
    private String storeTypeID;    //必传
    private int ismrpcal;  //必传
    private int issueQty;  //必传

    public int getIssueQty() {
        return issueQty;
    }

    public void setIssueQty(int issueQty) {
        this.issueQty = issueQty;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getAllocateNo() {
        return allocateNo;
    }

    public void setAllocateNo(String allocateNo) {
        this.allocateNo = allocateNo;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public double getAsscoefficient() {
        return asscoefficient;
    }

    public void setAsscoefficient(double asscoefficient) {
        this.asscoefficient = asscoefficient;
    }

    public double getAssociateQty() {
        return associateQty;
    }

    public void setAssociateQty(double associateQty) {
        this.associateQty = associateQty;
    }

    public double getAssistQty() {
        return assistQty;
    }

    public void setAssistQty(double assistQty) {
        this.assistQty = assistQty;
    }

    public String getMaterialID() {
        return materialID;
    }

    public void setMaterialID(String materialID) {
        this.materialID = materialID;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getBaseUnitID() {
        return baseUnitID;
    }

    public void setBaseUnitID(String baseUnitID) {
        this.baseUnitID = baseUnitID;
    }

    public String getIssueWarehouseID() {
        return issueWarehouseID;
    }

    public void setIssueWarehouseID(String issueWarehouseID) {
        this.issueWarehouseID = issueWarehouseID;
    }

    public String getReceiptWarehouseID() {
        return receiptWarehouseID;
    }

    public void setReceiptWarehouseID(String receiptWarehouseID) {
        this.receiptWarehouseID = receiptWarehouseID;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getBaseQty() {
        return baseQty;
    }

    public void setBaseQty(double baseQty) {
        this.baseQty = baseQty;
    }

    public String getReceiptPlanDate() {
        return receiptPlanDate;
    }

    public void setReceiptPlanDate(String receiptPlanDate) {
        this.receiptPlanDate = receiptPlanDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }

    public double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getPruinvoiceAmount() {
        return pruinvoiceAmount;
    }

    public void setPruinvoiceAmount(double pruinvoiceAmount) {
        this.pruinvoiceAmount = pruinvoiceAmount;
    }

    public double getSaleinvoiceAmount() {
        return saleinvoiceAmount;
    }

    public void setSaleinvoiceAmount(double saleinvoiceAmount) {
        this.saleinvoiceAmount = saleinvoiceAmount;
    }

    public double getTotalAccountPayable() {
        return totalAccountPayable;
    }

    public void setTotalAccountPayable(double totalAccountPayable) {
        this.totalAccountPayable = totalAccountPayable;
    }

    public double getTotalAccountReceivable() {
        return totalAccountReceivable;
    }

    public void setTotalAccountReceivable(double totalAccountReceivable) {
        this.totalAccountReceivable = totalAccountReceivable;
    }

    public String getIssueLocationID() {
        return issueLocationID;
    }

    public void setIssueLocationID(String issueLocationID) {
        this.issueLocationID = issueLocationID;
    }

    public String getReceiptLocationID() {
        return receiptLocationID;
    }

    public void setReceiptLocationID(String receiptLocationID) {
        this.receiptLocationID = receiptLocationID;
    }

    public String getIssueStorageOrgUnitID() {
        return issueStorageOrgUnitID;
    }

    public void setIssueStorageOrgUnitID(String issueStorageOrgUnitID) {
        this.issueStorageOrgUnitID = issueStorageOrgUnitID;
    }

    public String getIssueCompanyOrgUnitID() {
        return issueCompanyOrgUnitID;
    }

    public void setIssueCompanyOrgUnitID(String issueCompanyOrgUnitID) {
        this.issueCompanyOrgUnitID = issueCompanyOrgUnitID;
    }

    public String getReceiveStorageOrgUnitID() {
        return receiveStorageOrgUnitID;
    }

    public void setReceiveStorageOrgUnitID(String receiveStorageOrgUnitID) {
        this.receiveStorageOrgUnitID = receiveStorageOrgUnitID;
    }

    public String getReceiveCompanyOrgUnitID() {
        return receiveCompanyOrgUnitID;
    }

    public void setReceiveCompanyOrgUnitID(String receiveCompanyOrgUnitID) {
        this.receiveCompanyOrgUnitID = receiveCompanyOrgUnitID;
    }

    public double getBalancecostPrice() {
        return balancecostPrice;
    }

    public void setBalancecostPrice(double balancecostPrice) {
        this.balancecostPrice = balancecostPrice;
    }

    public double getBalancecostRate() {
        return balancecostRate;
    }

    public void setBalancecostRate(double balancecostRate) {
        this.balancecostRate = balancecostRate;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getDiscountType() {
        return discountType;
    }

    public void setDiscountType(int discountType) {
        this.discountType = discountType;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getActualTaxPrice() {
        return actualTaxPrice;
    }

    public void setActualTaxPrice(double actualTaxPrice) {
        this.actualTaxPrice = actualTaxPrice;
    }

    public double getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(double localAmount) {
        this.localAmount = localAmount;
    }

    public double getLocalTaxAmount() {
        return localTaxAmount;
    }

    public void setLocalTaxAmount(double localTaxAmount) {
        this.localTaxAmount = localTaxAmount;
    }

    public double getDiscountAmoumt() {
        return discountAmoumt;
    }

    public void setDiscountAmoumt(double discountAmoumt) {
        this.discountAmoumt = discountAmoumt;
    }

    public String getMfg() {
        return mfg;
    }

    public void setMfg(String mfg) {
        this.mfg = mfg;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getIssuePlanDate() {
        return issuePlanDate;
    }

    public void setIssuePlanDate(String issuePlanDate) {
        this.issuePlanDate = issuePlanDate;
    }

    public String getStoreTypeID() {
        return storeTypeID;
    }

    public void setStoreTypeID(String storeTypeID) {
        this.storeTypeID = storeTypeID;
    }

    public int getIsmrpcal() {
        return ismrpcal;
    }

    public void setIsmrpcal(int ismrpcal) {
        this.ismrpcal = ismrpcal;
    }
}
