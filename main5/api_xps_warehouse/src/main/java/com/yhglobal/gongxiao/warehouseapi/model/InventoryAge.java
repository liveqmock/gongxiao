package com.yhglobal.gongxiao.warehouseapi.model;

/**
 * 批次库存报表
 */
public class InventoryAge {

    private String status;               //批次状态
    private String batchNo;  //
    private String productCode;//
    private String productName;//
    private String warehouseName;//
    private int inboundQuantity;//入库数量
    private String purchaseOrderNo;//采购单号
    private String inboundOrderNo;//入库单号
    private long createTime;//入库日期
    private long receiveFinishTime;//收货完成日期
    private long purchaseGuidPrice;//采购指导价
    private long purchasePrice;//采购价
    private long costPrice;//成本价
    private long batchInboundAmount;//批次进货金额
    private int inventoryAge;//库存库龄
    private int restAmount;//剩余数量
    private String salesOrderNo;//销售订单号
    private String outboundOrderNo;//出库单号
    private long salesGuidPrice;//销售指导价
    private long outboundPrice;//出货价
    private long outboundQuantity;//出货数量
    private long batchOutboundAmount;//出货总金额
    private long customerUseCoupon;//客户使用返利
    private long customerUsePrepaid;//客户使用代垫
    private long outboundDate;//出货日期
    private long sightTime;//签收日期

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getInboundQuantity() {
        return inboundQuantity;
    }

    public void setInboundQuantity(int inboundQuantity) {
        this.inboundQuantity = inboundQuantity;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getInboundOrderNo() {
        return inboundOrderNo;
    }

    public void setInboundOrderNo(String inboundOrderNo) {
        this.inboundOrderNo = inboundOrderNo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getReceiveFinishTime() {
        return receiveFinishTime;
    }

    public void setReceiveFinishTime(long receiveFinishTime) {
        this.receiveFinishTime = receiveFinishTime;
    }

    public long getPurchaseGuidPrice() {
        return purchaseGuidPrice;
    }

    public void setPurchaseGuidPrice(long purchaseGuidPrice) {
        this.purchaseGuidPrice = purchaseGuidPrice;
    }

    public long getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(long purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
    }

    public long getBatchInboundAmount() {
        return batchInboundAmount;
    }

    public void setBatchInboundAmount(long batchInboundAmount) {
        this.batchInboundAmount = batchInboundAmount;
    }

    public int getInventoryAge() {
        return inventoryAge;
    }

    public void setInventoryAge(int inventoryAge) {
        this.inventoryAge = inventoryAge;
    }

    public int getRestAmount() {
        return restAmount;
    }

    public void setRestAmount(int restAmount) {
        this.restAmount = restAmount;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getOutboundOrderNo() {
        return outboundOrderNo;
    }

    public void setOutboundOrderNo(String outboundOrderNo) {
        this.outboundOrderNo = outboundOrderNo;
    }

    public long getSalesGuidPrice() {
        return salesGuidPrice;
    }

    public void setSalesGuidPrice(long salesGuidPrice) {
        this.salesGuidPrice = salesGuidPrice;
    }

    public long getOutboundPrice() {
        return outboundPrice;
    }

    public void setOutboundPrice(long outboundPrice) {
        this.outboundPrice = outboundPrice;
    }

    public long getOutboundQuantity() {
        return outboundQuantity;
    }

    public void setOutboundQuantity(long outboundQuantity) {
        this.outboundQuantity = outboundQuantity;
    }

    public long getBatchOutboundAmount() {
        return batchOutboundAmount;
    }

    public void setBatchOutboundAmount(long batchOutboundAmount) {
        this.batchOutboundAmount = batchOutboundAmount;
    }

    public long getCustomerUseCoupon() {
        return customerUseCoupon;
    }

    public void setCustomerUseCoupon(long customerUseCoupon) {
        this.customerUseCoupon = customerUseCoupon;
    }

    public long getCustomerUsePrepaid() {
        return customerUsePrepaid;
    }

    public void setCustomerUsePrepaid(long customerUsePrepaid) {
        this.customerUsePrepaid = customerUsePrepaid;
    }

    public long getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(long outboundDate) {
        this.outboundDate = outboundDate;
    }

    public long getSightTime() {
        return sightTime;
    }

    public void setSightTime(long sightTime) {
        this.sightTime = sightTime;
    }
}
