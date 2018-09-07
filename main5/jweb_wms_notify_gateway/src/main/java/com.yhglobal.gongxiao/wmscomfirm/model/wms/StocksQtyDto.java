package com.yhglobal.gongxiao.wmscomfirm.model.wms;

import java.io.Serializable;
import java.util.Date;

public class StocksQtyDto implements Serializable{
    private int inventoryType;      //库存类型(1:可销售库存 101:残次 102:机损 103:箱损 201:冻结库存 301:在途库存)
    private int quantity;           //数量
    private Date produceDate;       //商品生产日期，格式：yyyy-MM-dd
    private Date expireDate;        //商品过期日期，格式：yyyy-MM-dd
    private String eRPBatchCode;    //wms实际以这个字段接收批次

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
}
