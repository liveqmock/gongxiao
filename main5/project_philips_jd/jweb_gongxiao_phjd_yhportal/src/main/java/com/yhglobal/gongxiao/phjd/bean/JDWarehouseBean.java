package com.yhglobal.gongxiao.phjd.bean;

import java.io.Serializable;

/**
 * 京东仓库地址管理
 *
 * @author yuping.lin
 */
public class JDWarehouseBean implements Serializable {
    private String warehouseID;   //仓库编码
    private String warehouse;   //仓库名称
    private String receiver;   //收货人
    private String receiverTel;   //联系方式
    private String receivingAddress;   //仓库地址
    private String distributionCenter;   //到站
    private String warehouseGLN;   //京东仓库编码
    private String createTime;   //创建时间

    public String getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
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

    public String getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(String distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public String getWarehouseGLN() {
        return warehouseGLN;
    }

    public void setWarehouseGLN(String warehouseGLN) {
        this.warehouseGLN = warehouseGLN;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
