package com.yhglobal.gongxiao.model;

import java.util.Date;

public class PurchaseWmsInboundRecord {
    private Long rowId;
    /**
     * 入库类型: 1:采购进货
     */
    private Byte inboundType;
    /**
     * 采购单号
     */
    private String purchaseOrderNo;
    /**
     * 入库单号
     */
    private String gongxiaoInboundOrderNo;
    /**
     * 入库单明细id
     */
    private Long inboundOrderItemId;
    /**
     * WMS那边记录的入库单号
     */
    private String wmsInboundOrderNo;
    /**
     * 仓库ID
     */
    private String warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品单位
     */
    private String productUnit;
    /**
     * 已入库的商品数量
     */
    private Integer inStockQuantity;
    /**
     * 已入库的残次品数量
     */
    private Integer imperfectQuantity;
    /**
     * 被拒收未入库的商品数量
     */
    private Integer rejectedQuantity;

    private Date createTime;

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public Byte getInboundType() {
        return inboundType;
    }

    public void setInboundType(Byte inboundType) {
        this.inboundType = inboundType;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo == null ? null : purchaseOrderNo.trim();
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo == null ? null : gongxiaoInboundOrderNo.trim();
    }

    public Long getInboundOrderItemId() {
        return inboundOrderItemId;
    }

    public void setInboundOrderItemId(Long inboundOrderItemId) {
        this.inboundOrderItemId = inboundOrderItemId;
    }

    public String getWmsInboundOrderNo() {
        return wmsInboundOrderNo;
    }

    public void setWmsInboundOrderNo(String wmsInboundOrderNo) {
        this.wmsInboundOrderNo = wmsInboundOrderNo == null ? null : wmsInboundOrderNo.trim();
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId == null ? null : warehouseId.trim();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    public Integer getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(Integer inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public Integer getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(Integer imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public Integer getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(Integer rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}