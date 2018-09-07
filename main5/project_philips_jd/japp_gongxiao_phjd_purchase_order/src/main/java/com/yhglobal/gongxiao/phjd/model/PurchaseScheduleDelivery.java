package com.yhglobal.gongxiao.phjd.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约收货
 */
public class PurchaseScheduleDelivery implements Serializable {
    private String tablePrefix;
    /**
     * 预约id
     */
    private Long scheduleId;
    /**
     * 预约状态   90正常完结 91有异常的完结 已处理  92有异常的完结 未处理
     */
    private Byte scheduleStatus;
    /**
     * 是否已同步到仓储模块
     */
    private Byte syncToGongxiaoWarehouseFlag;
    /**
     * 仓储模块那边给的回执
     */
    private String gongxiaoWarehouseInboundOrderNo;
    /**
     * 采购单id
     */
    private String  purchaseOrderNo;
    /**
     * 仓库ID
     */
    private String warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 物流方式 0:不详 1:自提 2:第三方物流
     */
    private Byte shippingMode;
    /**
     * 物流单号
     */
    private String logisticsOrderNo;
    /**
     * 物流公司名称
     */
    private String logisticsCompany;
    /**
     * 商品信息(JSON)
     */
    private String productInfo;
    /**
     * 总商品数量
     */
    private Integer totalQuantity;

    private Long dataVersion;

    private Date createTime;

    private Date lastUpdateTime;
    /**
     * 操作日记
     */
    private String tracelog;

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Byte getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(Byte scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public Byte getSyncToGongxiaoWarehouseFlag() {
        return syncToGongxiaoWarehouseFlag;
    }

    public void setSyncToGongxiaoWarehouseFlag(Byte syncToGongxiaoWarehouseFlag) {
        this.syncToGongxiaoWarehouseFlag = syncToGongxiaoWarehouseFlag;
    }

    public String getGongxiaoWarehouseInboundOrderNo() {
        return gongxiaoWarehouseInboundOrderNo;
    }

    public void setGongxiaoWarehouseInboundOrderNo(String gongxiaoWarehouseInboundOrderNo) {
        this.gongxiaoWarehouseInboundOrderNo = gongxiaoWarehouseInboundOrderNo == null ? null : gongxiaoWarehouseInboundOrderNo.trim();
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
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

    public Byte getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(Byte shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getLogisticsOrderNo() {
        return logisticsOrderNo;
    }

    public void setLogisticsOrderNo(String logisticsOrderNo) {
        this.logisticsOrderNo = logisticsOrderNo == null ? null : logisticsOrderNo.trim();
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany == null ? null : logisticsCompany.trim();
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo == null ? null : productInfo.trim();
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
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

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog == null ? null : tracelog.trim();
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }
}