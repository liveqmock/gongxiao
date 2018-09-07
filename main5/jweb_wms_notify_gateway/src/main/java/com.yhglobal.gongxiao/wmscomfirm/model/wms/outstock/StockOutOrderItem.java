package com.yhglobal.gongxiao.wmscomfirm.model.wms.outstock;

import java.io.Serializable;
import java.util.Date;

public class StockOutOrderItem implements Serializable {
    private String itemNO;          //行号    可选
    private String itemCode;        //商品编码      必选
    private String itemName;        //商品名称      可选
    private int inventoryType;      //库存类型      必选
    private int itemQuantity;       //商品数量      必选
    private double itemPrice;       //销售价格      可选
    private boolean IsInsured;      //是否保价      必选
    private double actualPrice;     //商品实际价格    可选
    private Date produceDate;       //商品生产日期    可选
    private Date expireDate;        //商品过期日期    可选
    private String BatchCode;       //批次号          可选
    private String eRPBatchCode;    //wms实际以这个字段接收批次

    public String geteRPBatchCode() {
        return eRPBatchCode;
    }

    public void seteRPBatchCode(String eRPBatchCode) {
        this.eRPBatchCode = eRPBatchCode;
    }

    public StockOutOrderItem() {
    }

    public StockOutOrderItem(String itemNO, String itemCode, String itemName, int inventoryType, int itemQuantity, double itemPrice, boolean isInsured, double actualPrice, Date produceDate, Date expireDate, String batchCode) {
        this.itemNO = itemNO;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.inventoryType = inventoryType;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        IsInsured = isInsured;
        this.actualPrice = actualPrice;
        this.produceDate = produceDate;
        this.expireDate = expireDate;
        BatchCode = batchCode;
    }

    public String getItemNO() {
        return itemNO;
    }

    public void setItemNO(String itemNO) {
        this.itemNO = itemNO;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(int inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public boolean isInsured() {
        return IsInsured;
    }

    public void setInsured(boolean insured) {
        IsInsured = insured;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Date getProduceDate() {
        return produceDate;
    }

    public void setProduceDate(Date produceDate) {
        this.produceDate = produceDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getBatchCode() {
        return BatchCode;
    }

    public void setBatchCode(String batchCode) {
        BatchCode = batchCode;
    }
}
