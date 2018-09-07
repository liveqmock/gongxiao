package com.yhglobal.gongxiao.tmsapi.order.create;


/**
 * 订单商品信息
 *
 * 注: 为了和TMS的字段名称一样，这里字段都用大写开始
 */
public class StockInOrderItem {

    String itemNo; //可选 行号

    String itemCode; //必选 商品编码

    String itemName; //可选 商品名称

    Integer itemQuantity; //必选 商品数量

    Float itemVol; //可选 商品体积

    Float itemWgt; //必选 商品重量

    Integer itemCarton; //可选 商品箱数

    Integer sloc; //可选 物品品质

    String mark; //可选 Mark

    String plant; //可选 Plant

    Float decPrice; //可选 商品金额

    String itemPo; //可选 ItemPo

    String batchCode; //可选 批次号

    public StockInOrderItem(String itemNo, String itemCode, String itemName, Integer itemQuantity, Float itemVol, Float itemWgt, String batchCode) {
        this.itemNo = itemNo;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemVol = itemVol;
        this.itemWgt = itemWgt;
        this.batchCode = batchCode;
    }

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

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Float getItemVol() {
        return itemVol;
    }

    public void setItemVol(Float itemVol) {
        this.itemVol = itemVol;
    }

    public Float getItemWgt() {
        return itemWgt;
    }

    public void setItemWgt(Float itemWgt) {
        this.itemWgt = itemWgt;
    }

    public Integer getItemCarton() {
        return itemCarton;
    }

    public void setItemCarton(Integer itemCarton) {
        this.itemCarton = itemCarton;
    }

    public Integer getSloc() {
        return sloc;
    }

    public void setSloc(Integer sloc) {
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

    public Float getDecPrice() {
        return decPrice;
    }

    public void setDecPrice(Float decPrice) {
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
