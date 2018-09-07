package com.yhglobal.gongxiao.phjd.sales.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * 销售订单地址表 与数据库一一对应
 *
 * @author weizecheng
 * @date 2018/8/21 17:52
 */
public class SalesOrderAddressDO implements Serializable {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 销售订单Id
     */
    private Long salesOrderId;

    /**
     * 物流方式 0:不详 1:自提 2:第三方物流
     */
    private Integer shippingMode;

    /**
     * 配送中心名称
     */
    private String distributionCenter;

    /**
     * 配送中心编码
     */
    private String distributionCenterId;

    /**
     * 仓库名称
     */
    private String warehouse;

    /**
     * 仓库编码
     */
    private String warehouseId;

    /**
     * 京东仓库的GLN编码
     */
    private String warehouseGln;

    /**
     * 仓库收货人
     */
    private String receiver;

    /**
     * 收货人联系方式
     */
    private String receiverTel;

    /**
     * 收货地址及单位
     */
    private String receivingAddress;

    /**
     * 指货物最终到达的城市或省。
     */
    private String arrived;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy年MM月dd日 HH:mm:ss")
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     * 版本号
     */
    private Long dataVersion;

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(Long salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public Integer getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(Integer shippingMode) {
        this.shippingMode = shippingMode;
    }

    public String getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(String distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public String getDistributionCenterId() {
        return distributionCenterId;
    }

    public void setDistributionCenterId(String distributionCenterId) {
        this.distributionCenterId = distributionCenterId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseGln() {
        return warehouseGln;
    }

    public void setWarehouseGln(String warehouseGln) {
        this.warehouseGln = warehouseGln;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getReceivingAddress() {
        return receivingAddress;
    }

    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }

    public String getArrived() {
        return arrived;
    }

    public void setArrived(String arrived) {
        this.arrived = arrived;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SalesOrderAddressDO{");
        sb.append("id=").append(id);
        sb.append(", salesOrderId=").append(salesOrderId);
        sb.append(", shippingMode=").append(shippingMode);
        sb.append(", distributionCenter='").append(distributionCenter).append('\'');
        sb.append(", distributionCenterId='").append(distributionCenterId).append('\'');
        sb.append(", warehouse='").append(warehouse).append('\'');
        sb.append(", warehouseId='").append(warehouseId).append('\'');
        sb.append(", warehouseGln='").append(warehouseGln).append('\'');
        sb.append(", receiver='").append(receiver).append('\'');
        sb.append(", receiverTel='").append(receiverTel).append('\'');
        sb.append(", receivingAddress='").append(receivingAddress).append('\'');
        sb.append(", arrived='").append(arrived).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", lastUpdateTime=").append(lastUpdateTime);
        sb.append(", dataVersion=").append(dataVersion);
        sb.append('}');
        return sb.toString();
    }
}
