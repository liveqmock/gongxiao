package com.yhglobal.gongxiao.warehousemanagement.dto.wms;

import java.io.Serializable;
import java.util.Date;

public class StockOutOrderConfirmItemDto implements Serializable {
    private int inventoryType;      //库存类型
    private int quantity;           //数量
    private String batchCode;       //批次号
    private Date dueDate;           //到货日期
    private int guanranteePeriod;   //保质期/天
    private String guanranteeUnit;  //保质期单位
    private String produceArea;     //生产地区
    private String produceCode;     //生产编码
    private Date poduceDate;        //生产日期
    private Date expireDate;        //过期日期
    private String eRPBatchCode;    //分销系统批次号

    public String geteRPBatchCode() {
        return eRPBatchCode;
    }

    public void seteRPBatchCode(String eRPBatchCode) {
        this.eRPBatchCode = eRPBatchCode;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getGuanranteePeriod() {
        return guanranteePeriod;
    }

    public void setGuanranteePeriod(int guanranteePeriod) {
        this.guanranteePeriod = guanranteePeriod;
    }

    public String getGuanranteeUnit() {
        return guanranteeUnit;
    }

    public void setGuanranteeUnit(String guanranteeUnit) {
        this.guanranteeUnit = guanranteeUnit;
    }

    public String getProduceArea() {
        return produceArea;
    }

    public void setProduceArea(String produceArea) {
        this.produceArea = produceArea;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public Date getPoduceDate() {
        return poduceDate;
    }

    public void setPoduceDate(Date poduceDate) {
        this.poduceDate = poduceDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
