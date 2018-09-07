package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * 入库信息
 *
 * @author: 陈浩
 * @create: 2018-03-15 15:01
 **/
public class PurchaseReturnInboundInfo implements Serializable {
    /**
     * 入库单号
     */
    private String inboundOrderNumber;
    /**
     * 入库状态 0 未入库 1 部分入库 2 已入库
     */
    private int inboundOrderStatus;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 入库时间
     */
    private Date inboundDate;
    /**
     * 入库仓库
     */
    private String warehouseName;

    public String getInboundOrderNumber() {
        return inboundOrderNumber;
    }

    public void setInboundOrderNumber(String inboundOrderNumber) {
        this.inboundOrderNumber = inboundOrderNumber;
    }

    public int getInboundOrderStatus() {
        return inboundOrderStatus;
    }

    public void setInboundOrderStatus(int inboundOrderStatus) {
        this.inboundOrderStatus = inboundOrderStatus;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(Date inboundDate) {
        this.inboundDate = inboundDate;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
