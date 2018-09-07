package com.yhglobal.gongxiao.warehousemanagement.dto.tms;

import java.io.Serializable;

/**
 *  tms订单商品信息
 */
public class TmsStockInOrderItem implements Serializable {
    private String itemNo;          //行号
    private String itemCode;        //商品编码
    private String itemName;        //商品名称
    private int itemQuantity;    //商品数量
    private String itemVol;         //商品体积
    private double itemWgt;         //商品重量
    private String itemCarton;      //商品箱数
    private String sloc;            //物品品质
    private String mark;            //Mark
    private String plant;           //Plant
    private float decPrice;        //商品金额
    private String itemPo;          //ItemPo
    private String batchCode;       //批次号

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
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

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemVol() {
        return itemVol;
    }

    public void setItemVol(String itemVol) {
        this.itemVol = itemVol;
    }

    public double getItemWgt() {
        return itemWgt;
    }

    public void setItemWgt(double itemWgt) {
        this.itemWgt = itemWgt;
    }

    public String getItemCarton() {
        return itemCarton;
    }

    public void setItemCarton(String itemCarton) {
        this.itemCarton = itemCarton;
    }

    public String getSloc() {
        return sloc;
    }

    public void setSloc(String sloc) {
        this.sloc = sloc;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public float getDecPrice() {
        return decPrice;
    }

    public void setDecPrice(float decPrice) {
        this.decPrice = decPrice;
    }

    public String getItemPo() {
        return itemPo;
    }

    public void setItemPo(String itemPo) {
        this.itemPo = itemPo;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }
}
