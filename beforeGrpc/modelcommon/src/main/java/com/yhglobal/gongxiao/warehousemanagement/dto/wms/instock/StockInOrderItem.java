package com.yhglobal.gongxiao.warehousemanagement.dto.wms.instock;



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
    private String batchCode;       //wms批次号
    private String custCode;        //
    private String eRPBatchCode;    //分销系统批次号

    public StockInOrderItem() {
    }

    public StockInOrderItem(String itemNo, String itemCode, String itemName, int inventoryType, int itemQuantity, float itemPrice, float tagPrice, float purchasePrice, Date produceDate, Date expireDate, String itemVersion, String batchCode, String custCode) {
        this.itemNo = itemNo;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.inventoryType = inventoryType;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.tagPrice = tagPrice;
        this.purchasePrice = purchasePrice;
        this.produceDate = produceDate;
        this.expireDate = expireDate;
        this.itemVersion = itemVersion;
        this.batchCode = batchCode;
        this.custCode = custCode;
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
