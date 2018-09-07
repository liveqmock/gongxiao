package com.yhglobal.gongxiao.phjd.payment.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @Description: 代垫付款单明细
 * @author: LUOYI
 * @Date: Created in 15:26 2018/4/27
 */
public class YhglobalPrepaidInfoItem  implements Serializable {
    private Integer rowId;

    private Integer infoId;

    private Integer costType;

    private String currencyCode;

    private Long exchangeRate;

    private Double exchangeRateDouble;

    private Long applicationAmount;

    private Double applicationAmountDouble;

    private Integer invoiceType;

    private Long taxPoint;

    private Double taxPointDouble;

    private Long standardCurrencyAmount;

    private Double standardCurrencyAmountDouble;

    private Long taxSubsidy;

    private Double taxSubsidyDouble;

    private Integer isTaxSubsidy;

    private String reason;

    public Double getExchangeRateDouble() {
        return exchangeRateDouble;
    }

    public void setExchangeRateDouble(Double exchangeRateDouble) {
        this.exchangeRateDouble = exchangeRateDouble;
    }

    public Double getApplicationAmountDouble() {
        return applicationAmountDouble;
    }

    public void setApplicationAmountDouble(Double applicationAmountDouble) {
        this.applicationAmountDouble = applicationAmountDouble;
    }

    public Double getTaxPointDouble() {
        return taxPointDouble;
    }

    public void setTaxPointDouble(Double taxPointDouble) {
        this.taxPointDouble = taxPointDouble;
    }

    public Double getStandardCurrencyAmountDouble() {
        return standardCurrencyAmountDouble;
    }

    public void setStandardCurrencyAmountDouble(Double standardCurrencyAmountDouble) {
        this.standardCurrencyAmountDouble = standardCurrencyAmountDouble;
    }

    public Double getTaxSubsidyDouble() {
        return taxSubsidyDouble;
    }

    public void setTaxSubsidyDouble(Double taxSubsidyDouble) {
        this.taxSubsidyDouble = taxSubsidyDouble;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getCostType() {
        return costType;
    }

    public void setCostType(Integer costType) {
        this.costType = costType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public Long getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Long exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Long getApplicationAmount() {
        return applicationAmount;
    }

    public void setApplicationAmount(Long applicationAmount) {
        this.applicationAmount = applicationAmount;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Long getTaxPoint() {
        return taxPoint;
    }

    public void setTaxPoint(Long taxPoint) {
        this.taxPoint = taxPoint;
    }

    public Long getStandardCurrencyAmount() {
        return standardCurrencyAmount;
    }

    public void setStandardCurrencyAmount(Long standardCurrencyAmount) {
        this.standardCurrencyAmount = standardCurrencyAmount;
    }

    public Long getTaxSubsidy() {
        return taxSubsidy;
    }

    public void setTaxSubsidy(Long taxSubsidy) {
        this.taxSubsidy = taxSubsidy;
    }

    public Integer getIsTaxSubsidy() {
        return isTaxSubsidy;
    }

    public void setIsTaxSubsidy(Integer isTaxSubsidy) {
        this.isTaxSubsidy = isTaxSubsidy;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public static void main(String[] args){
        YhglobalPrepaidInfoItem item = new YhglobalPrepaidInfoItem();
        item.setCostType(1);
        item.setCurrencyCode("CNY");
        item.setTaxSubsidyDouble(1.0);
        item.setApplicationAmountDouble(1000.0);
        item.setInvoiceType(1);
        item.setIsTaxSubsidy(1);
        item.setStandardCurrencyAmountDouble(1000.0);
        item.setExchangeRateDouble(10.0);
        item.setReason("test");
        System.out.print(JSONObject.toJSON(item));
    }
}