package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;
import java.util.Date;

public class OutboundOrderItem implements Serializable{
    private long rowId;                             //主键id
    private int inventoryType;                      //入库类型
    private int purchaseType;                       //0:普通采购 1:货补 2:赠品
    private String projectId;                       //项目id
    private String outboundOrderItemNo;             //出库单明细id
    private String batchNo;                         //批次号
    private String purchaseOrderNo;                 //采购单号
    private String salesOrderNo;                    //销售单号
    private Boolean itemStatus;                     //商品出库状态
    private String gongxiaoOutboundOrderNo;         //GX出库单号
    private String warehouseId;                     //仓库ID
    private String warehouseName;                   //仓库名称
    private String productId;                       //商品id
    private String productCode;                     //商品编码
    private String productName;                     //商品名称
    private String productUnit;                     //商品单位
    private int totalQuantity;                      //各种类商品数量总和
    private int outStockQuantity;                   //已出库的商品数量
    private int imperfectQuantity;                  //已出库的残次品数量
    private int canceledQuantity;                   //已取消的商品数量
    private int realOutStockQuantity;               //实际出库数量
    private int returnQuantity;                     //可退货数量
    private long salesGuidePrice;                   //销售指导价
    private long wholesalePrice;                    //出货价格
    private Long dataVersion;                       //数据版本
    private Date createTime;                        //创建时间
    private Date lastUpdateTime;                    //上次更新时间

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOutboundOrderItemNo() {
        return outboundOrderItemNo;
    }

    public void setOutboundOrderItemNo(String outboundOrderItemNo) {
        this.outboundOrderItemNo = outboundOrderItemNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public Boolean getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Boolean itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getGongxiaoOutboundOrderNo() {
        return gongxiaoOutboundOrderNo;
    }

    public void setGongxiaoOutboundOrderNo(String gongxiaoOutboundOrderNo) {
        this.gongxiaoOutboundOrderNo = gongxiaoOutboundOrderNo;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getOutStockQuantity() {
        return outStockQuantity;
    }

    public void setOutStockQuantity(int outStockQuantity) {
        this.outStockQuantity = outStockQuantity;
    }

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public int getCanceledQuantity() {
        return canceledQuantity;
    }

    public void setCanceledQuantity(int canceledQuantity) {
        this.canceledQuantity = canceledQuantity;
    }

    public int getRealOutStockQuantity() {
        return realOutStockQuantity;
    }

    public void setRealOutStockQuantity(int realOutStockQuantity) {
        this.realOutStockQuantity = realOutStockQuantity;
    }

    public int getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(int returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    public long getSalesGuidePrice() {
        return salesGuidePrice;
    }

    public void setSalesGuidePrice(long salesGuidePrice) {
        this.salesGuidePrice = salesGuidePrice;
    }

    public long getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(long wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
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
}
