package com.yhglobal.gongxiao.warehousemanagement.dto.wms.inventory;

import java.io.Serializable;

/**
 * 查询库存的结果明细
 * @author liukai
 */
public class InventoryResultDetailDto implements Serializable {
    private String ckid;                //仓库id
    private String warehouseCode;       //仓库编码  EAS_ + eas项目编码
    private String itemCode;            //商品编码
    private String inventoryType;       //库存品质
    private int quantity;               //数量
    private int lockQuantity;           //冻结数量
    private String productDate;         //商品生产日期
    private String expireDate;          //商品过期日期
    private int period;
    private String plant;
    private String projectCode;         //项目编码    eas项目编码
    private String eRPBatchCode;        //分销系统的批次号


    public String geteRPBatchCode() {
        return eRPBatchCode;
    }

    public void seteRPBatchCode(String eRPBatchCode) {
        this.eRPBatchCode = eRPBatchCode;
    }

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLockQuantity() {
        return lockQuantity;
    }

    public void setLockQuantity(int lockQuantity) {
        this.lockQuantity = lockQuantity;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}
