package com.yhglobal.gongxiao.foundation.product.model;

import java.util.Date;

public class FoundationProductBusiness {
    private byte grossProfitValue;

    private Long productBusinessId;

    private Long productBasicId;

    private String projectId;

    private String projectName;

    private Byte recordStatus;

    private Byte easSynStatus;

    private String productName;

    private String productCode;

    private String easCode;

    private String easUnitCode;

    private String wmsCode;

    private String customerSKUCode;

    private String customerProductCode;

    private Long purchaseGuidePrice;

    private Long taxRate;

    private String taxCode;

    private Long saleGuidePrice;

    private Long actualSaleReturn;

    private Long shouldSaleReturn;

    private Long factorySendReturn;

    private Long costPrice;

    private Long outPrice;

    private Byte generateCoupon;

    private Date createTime;

    private Date lastUpdateTime;

    private String traceLog;

    public Long getProductBusinessId() {
        return productBusinessId;
    }

    public void setProductBusinessId(Long productBusinessId) {
        this.productBusinessId = productBusinessId;
    }

    public Long getProductBasicId() {
        return productBasicId;
    }

    public void setProductBasicId(Long productBasicId) {
        this.productBasicId = productBasicId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Byte getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Byte recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Byte getEasSynStatus() {
        return easSynStatus;
    }

    public void setEasSynStatus(Byte easSynStatus) {
        this.easSynStatus = easSynStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode == null ? null : easCode.trim();
    }

    public String getWmsCode() {
        return wmsCode;
    }

    public void setWmsCode(String wmsCode) {
        this.wmsCode = wmsCode == null ? null : wmsCode.trim();
    }

    public String getCustomerSKUCode() {
        return customerSKUCode;
    }

    public void setCustomerSKUCode(String customerSKUCode) {
        this.customerSKUCode = customerSKUCode == null ? null : customerSKUCode.trim();
    }

    public String getCustomerProductCode() {
        return customerProductCode;
    }

    public void setCustomerProductCode(String customerProductCode) {
        this.customerProductCode = customerProductCode == null ? null : customerProductCode.trim();
    }

    public Long getPurchaseGuidePrice() {
        return purchaseGuidePrice;
    }

    public void setPurchaseGuidePrice(Long purchaseGuidePrice) {
        this.purchaseGuidePrice = purchaseGuidePrice;
    }

    public Long getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode == null ? null : taxCode.trim();
    }

    public Long getSaleGuidePrice() {
        return saleGuidePrice;
    }

    public void setSaleGuidePrice(Long saleGuidePrice) {
        this.saleGuidePrice = saleGuidePrice;
    }

    public Long getActualSaleReturn() {
        return actualSaleReturn;
    }

    public void setActualSaleReturn(Long actualSaleReturn) {
        this.actualSaleReturn = actualSaleReturn;
    }

    public Long getShouldSaleReturn() {
        return shouldSaleReturn;
    }

    public void setShouldSaleReturn(Long shouldSaleReturn) {
        this.shouldSaleReturn = shouldSaleReturn;
    }

    public Long getFactorySendReturn() {
        return factorySendReturn;
    }

    public void setFactorySendReturn(Long factorySendReturn) {
        this.factorySendReturn = factorySendReturn;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(Long outPrice) {
        this.outPrice = outPrice;
    }

    public Byte getGenerateCoupon() {
        return generateCoupon;
    }

    public void setGenerateCoupon(Byte generateCoupon) {
        this.generateCoupon = generateCoupon;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(String traceLog) {
        this.traceLog = traceLog == null ? null : traceLog.trim();
    }

    public String getEasUnitCode() {
        return easUnitCode;
    }

    public void setEasUnitCode(String easUnitCode) {
        this.easUnitCode = easUnitCode;
    }

    public byte getGrossProfitValue() {
        return grossProfitValue;
    }

    public void setGrossProfitValue(byte grossProfitValue) {
        this.grossProfitValue = grossProfitValue;
    }
}