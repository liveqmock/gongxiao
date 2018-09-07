package com.yhglobal.gongxiao.sales.model;

import java.util.Date;

/**
 * 销售出库单商品明细
 *
 * @author 葛灿
 */
public class SalesOutboundOrderItem {
    /**主键id*/
    private long oid;
    /**出库单号*/
    private String outboundOrderNo;
    /**销售单号*/
    private String salesOrderNo;
    /**总数量*/
    private int quantity;
    /**毛利点位*/
    private long grossProfitValue;
    /**出货价*/
    private long wholesalePrice;
    /**销售指导价*/
    private long salesGuidePrice;
    /**采购价*/
    private long purchasePrice;
    /**越海折扣*/
    private long yhDiscountPercent;
    /**高卖金额单价(单价)*/
    private long sellHighAmount;
    /**生成代垫(单价)*/
    private long generatedPrepaid;
    /**应收毛利(单价)*/
    private long shouldReceiveGrossProfit;
    /**实收毛利(单价)*/
    private long receivedGrossProfit;
    /**主键id*/
    private Date createTime;

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getOutboundOrderNo() {
        return outboundOrderNo;
    }

    public void setOutboundOrderNo(String outboundOrderNo) {
        this.outboundOrderNo = outboundOrderNo;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getGrossProfitValue() {
        return grossProfitValue;
    }

    public void setGrossProfitValue(long grossProfitValue) {
        this.grossProfitValue = grossProfitValue;
    }

    public long getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(long wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public long getSalesGuidePrice() {
        return salesGuidePrice;
    }

    public void setSalesGuidePrice(long salesGuidePrice) {
        this.salesGuidePrice = salesGuidePrice;
    }

    public long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public long getYhDiscountPercent() {
        return yhDiscountPercent;
    }

    public void setYhDiscountPercent(long yhDiscountPercent) {
        this.yhDiscountPercent = yhDiscountPercent;
    }

    public long getSellHighAmount() {
        return sellHighAmount;
    }

    public void setSellHighAmount(long sellHighAmount) {
        this.sellHighAmount = sellHighAmount;
    }

    public long getGeneratedPrepaid() {
        return generatedPrepaid;
    }

    public void setGeneratedPrepaid(long generatedPrepaid) {
        this.generatedPrepaid = generatedPrepaid;
    }

    public long getShouldReceiveGrossProfit() {
        return shouldReceiveGrossProfit;
    }

    public void setShouldReceiveGrossProfit(long shouldReceiveGrossProfit) {
        this.shouldReceiveGrossProfit = shouldReceiveGrossProfit;
    }

    public long getReceivedGrossProfit() {
        return receivedGrossProfit;
    }

    public void setReceivedGrossProfit(long receivedGrossProfit) {
        this.receivedGrossProfit = receivedGrossProfit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
