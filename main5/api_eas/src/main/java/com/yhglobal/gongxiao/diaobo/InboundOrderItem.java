package com.yhglobal.gongxiao.diaobo;

import java.io.Serializable;
import java.util.Date;

public class InboundOrderItem implements Serializable{
    private Long rowId;                     //主键id
    private int inventoryType;              //入库类型
    private String projectId;               //项目id
    private String inboundOrderItemNo;          //入库单明细单号
    private String batchNo;                   //批次号
    private String purchaseOrderNo;           //采购单号
    private Boolean itemStatus;                   //入库单状态(是否完成)
    private String gongxiaoInboundOrderNo;   //入库单号
    private String warehouseId;              //仓库ID
    private String warehouseName;            //仓库名称
    private String productId;                //商品id
    private String productCode;             //商品编码
    private String productName;             //商品名称
    private String productUnit;             //商品单位
    private int totalQuantity;              //预约入库数量
    private int inTransitQuantity;          //在途商品数量
    private int inStockQuantity;            //已入库的商品数量
    private int imperfectQuantity;          //已入库的残次品数量
    private int rejectedQuantity;           //被拒收未入库的商品数量(残次品)
    private int realInStockQuantity;        //实际入库数量
    private String wmsInboundRecord;        //wms入库记录(JSON)
    private long purchasePrice;             //采购价格
    private long costPrice;                 //成本价格
    private Long dataVersion;               //数据版本
    private Date createTime;                //创建时间
    private Date lastUpdateTime;

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getInboundOrderItemNo() {
        return inboundOrderItemNo;
    }

    public void setInboundOrderItemNo(String inboundOrderItemNo) {
        this.inboundOrderItemNo = inboundOrderItemNo;
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

    public Boolean getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(Boolean itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
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

    public int getInTransitQuantity() {
        return inTransitQuantity;
    }

    public void setInTransitQuantity(int inTransitQuantity) {
        this.inTransitQuantity = inTransitQuantity;
    }

    public int getInStockQuantity() {
        return inStockQuantity;
    }

    public void setInStockQuantity(int inStockQuantity) {
        this.inStockQuantity = inStockQuantity;
    }

    public int getImperfectQuantity() {
        return imperfectQuantity;
    }

    public void setImperfectQuantity(int imperfectQuantity) {
        this.imperfectQuantity = imperfectQuantity;
    }

    public int getRejectedQuantity() {
        return rejectedQuantity;
    }

    public void setRejectedQuantity(int rejectedQuantity) {
        this.rejectedQuantity = rejectedQuantity;
    }

    public int getRealInStockQuantity() {
        return realInStockQuantity;
    }

    public void setRealInStockQuantity(int realInStockQuantity) {
        this.realInStockQuantity = realInStockQuantity;
    }

    public String getWmsInboundRecord() {
        return wmsInboundRecord;
    }

    public void setWmsInboundRecord(String wmsInboundRecord) {
        this.wmsInboundRecord = wmsInboundRecord;
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
