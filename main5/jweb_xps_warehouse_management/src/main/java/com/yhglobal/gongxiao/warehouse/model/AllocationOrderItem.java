package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 调拨单详情
 * @author liukai
 */
public class AllocationOrderItem implements Serializable {
    private int id;                             //主键id
    private String batchNo;                     //批次
    private String allocateNo;                  //调拨单号
    private String projectIdOut;                //调出项目
    private String projectIdEnter;              //调入项目
    private String gongxiaoOutboundOrderNo;     //出库单号
    private String gongxiaoInboundOrderNo;      //入库单号
    private String warehouseOutId;              //调出仓库id
    private String warehouseOut;                //调出仓库
    private String warehouseEnterId;            //调入仓库id
    private String warehouseEnter;              //调入仓库
    private String productId;                   //商品id
    private String productCode;                 //商品型号
    private String productName;                 //商品名称
    private String productUnit;                 //商品数量
    private int inventoryQuantity;           //可发库存
    private int alloteQuantity;              //调拨数量
    private int realInQuantity;                //实际调拨数量
    private int realOutQuantity;                //实际调拨数量
    private long guidPrice;                   //
    private long puchasePrice;                //
    private long costPrice;                   //
    private int inventoryStatus;                //库存类型
    private int status;                         //状态
    private int purchaseType;                   //采购类型
    private String entryId;                     //分路id
    private String materialCode;                //物料编码
    private Date createTime;                    //创建时间
    private Date lastUpdateTime;                //更新时间

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public int getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        this.purchaseType = purchaseType;
    }

    public long getGuidPrice() {
        return guidPrice;
    }

    public void setGuidPrice(long guidPrice) {
        this.guidPrice = guidPrice;
    }

    public long getPuchasePrice() {
        return puchasePrice;
    }

    public void setPuchasePrice(long puchasePrice) {
        this.puchasePrice = puchasePrice;
    }

    public long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(long costPrice) {
        this.costPrice = costPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getAllocateNo() {
        return allocateNo;
    }

    public void setAllocateNo(String allocateNo) {
        this.allocateNo = allocateNo;
    }

    public String getProjectIdOut() {
        return projectIdOut;
    }

    public void setProjectIdOut(String projectIdOut) {
        this.projectIdOut = projectIdOut;
    }

    public String getProjectIdEnter() {
        return projectIdEnter;
    }

    public void setProjectIdEnter(String projectIdEnter) {
        this.projectIdEnter = projectIdEnter;
    }

    public String getGongxiaoOutboundOrderNo() {
        return gongxiaoOutboundOrderNo;
    }

    public void setGongxiaoOutboundOrderNo(String gongxiaoOutboundOrderNo) {
        this.gongxiaoOutboundOrderNo = gongxiaoOutboundOrderNo;
    }

    public String getGongxiaoInboundOrderNo() {
        return gongxiaoInboundOrderNo;
    }

    public void setGongxiaoInboundOrderNo(String gongxiaoInboundOrderNo) {
        this.gongxiaoInboundOrderNo = gongxiaoInboundOrderNo;
    }

    public String getWarehouseOutId() {
        return warehouseOutId;
    }

    public void setWarehouseOutId(String warehouseOutId) {
        this.warehouseOutId = warehouseOutId;
    }

    public String getWarehouseEnterId() {
        return warehouseEnterId;
    }

    public void setWarehouseEnterId(String warehouseEnterId) {
        this.warehouseEnterId = warehouseEnterId;
    }

    public String getWarehouseOut() {
        return warehouseOut;
    }

    public void setWarehouseOut(String warehouseOut) {
        this.warehouseOut = warehouseOut;
    }

    public String getWarehouseEnter() {
        return warehouseEnter;
    }

    public void setWarehouseEnter(String warehouseEnter) {
        this.warehouseEnter = warehouseEnter;
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

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public int getAlloteQuantity() {
        return alloteQuantity;
    }

    public void setAlloteQuantity(int alloteQuantity) {
        this.alloteQuantity = alloteQuantity;
    }

    public int getRealInQuantity() {
        return realInQuantity;
    }

    public void setRealInQuantity(int realInQuantity) {
        this.realInQuantity = realInQuantity;
    }

    public int getRealOutQuantity() {
        return realOutQuantity;
    }

    public void setRealOutQuantity(int realOutQuantity) {
        this.realOutQuantity = realOutQuantity;
    }

    public int getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(int inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
