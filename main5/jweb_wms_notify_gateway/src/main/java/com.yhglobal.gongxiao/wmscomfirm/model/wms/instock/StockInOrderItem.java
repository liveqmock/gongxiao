package com.yhglobal.gongxiao.wmscomfirm.model.wms.instock;



import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
@XmlRootElement(name="stockInOrderItem")
public class StockInOrderItem implements Serializable {
    private String itemNo;          //行号
    private String itemCode;        //商品编码  必选
    private String itemName;        //商品名称
    private int inventoryType;      //库存类型  必选
    private int itemQuantity;       //商品数量  必选
    private float itemPrice;        //销售价格
    private float tagPrice;         //吊牌价
    private float purchasePrice;    //采购价格
    private Date produceDate;       //商品生产日期
    private Date expireDate;        //商品过期日期
    private String itemVersion;     //商品版本
    private String batchCode;       //批次号
    private String custCode;        //
    private String eRPBatchCode;    //wms实际以这个字段接收批次

    public StockInOrderItem() {
    }

    public String geteRPBatchCode() {
        return eRPBatchCode;
    }

    public void seteRPBatchCode(String eRPBatchCode) {
        this.eRPBatchCode = eRPBatchCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
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

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public float getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(float tagPrice) {
        this.tagPrice = tagPrice;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public String getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(String itemVersion) {
        this.itemVersion = itemVersion;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }
}
