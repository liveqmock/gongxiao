package com.yhglobal.gongxiao.sales.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约发货
 *
 * @author  葛灿
 * @date 2018/3/1--18:32
 */
public class SalesScheduleDelivery implements Serializable {
    /**
     * 预约id
     */
    private Long scheduleId;
    /**
     * 所有的出库单号集合
     */
    private String scheduleOrderNo;
    /**
     * 预约状态
     */
    private Integer scheduleStatus;
    /**
     * 是否已同步到仓储模块
     */
    private boolean syncToGongxiaoWarehouseFlag;
    /**
     * 正在进行的出库单
     */
    private String onGoingOutboundOrderInfo;
    /**
     * 出库完成的出库单单
     */
    private String outboundedOrderInfo;
    /**
     * 已经签收的出库单
     */
    private String signedOrderInfo;
    /**
     * 销售单id
     */
    private String salesOrderId;
    /**
     * 仓库ID
     */
    private Long warehouseId;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 物流方式 0:不详 1:自提 2:第三方物流
     */
    private Integer shippingMode;
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
     * 货品名称(productName)、货品编码(productCode)、品牌(brand)、
     * 币种(currency)、挂牌价(retailPrice)、出货价(wholesalePrice)、
     * 订单数量(totalQuantity)、剩余发货数量(unhandledQuantity)、仓库可出数量(warehouseQuantity)
     */
    private String productInfo;
    /**
     * 总商品数量
     */
    private Integer totalQuantity;
    /**
     * 数据版本
     */
    private Long dataVersion;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    /**
     * 操作日记
     */
    private String tracelog;
    /**
     * 唯一号
     */
    private String uniqueNo;

    private String easOrderNo;

    private String easOrderId;



    public String getOutboundedOrderInfo() {
        return outboundedOrderInfo;
    }

    public void setOutboundedOrderInfo(String outboundedOrderInfo) {
        this.outboundedOrderInfo = outboundedOrderInfo;
    }

    public String getSignedOrderInfo() {
        return signedOrderInfo;
    }

    public void setSignedOrderInfo(String signedOrderInfo) {
        this.signedOrderInfo = signedOrderInfo;
    }

    public String getEasOrderNo() {
        return easOrderNo;
    }

    public void setEasOrderNo(String easOrderNo) {
        this.easOrderNo = easOrderNo;
    }

    public String getEasOrderId() {
        return easOrderId;
    }

    public void setEasOrderId(String easOrderId) {
        this.easOrderId = easOrderId;
    }

    public String getUniqueNo() {
        return uniqueNo;
    }

    public void setUniqueNo(String uniqueNo) {
        this.uniqueNo = uniqueNo;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Integer getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(Integer scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public boolean isSyncToGongxiaoWarehouseFlag() {
        return syncToGongxiaoWarehouseFlag;
    }

    public void setSyncToGongxiaoWarehouseFlag(boolean syncToGongxiaoWarehouseFlag) {
        this.syncToGongxiaoWarehouseFlag = syncToGongxiaoWarehouseFlag;
    }

    public String getOnGoingOutboundOrderInfo() {
        return onGoingOutboundOrderInfo;
    }

    public void setOnGoingOutboundOrderInfo(String onGoingOutboundOrderInfo) {
        this.onGoingOutboundOrderInfo = onGoingOutboundOrderInfo == null ? null : onGoingOutboundOrderInfo.trim();
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public Integer getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(Integer shippingMode) {
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

    public String getScheduleOrderNo() {
        return scheduleOrderNo;
    }

    public void setScheduleOrderNo(String scheduleOrderNo) {
        this.scheduleOrderNo = scheduleOrderNo;
    }
}