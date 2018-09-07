package com.yhglobal.gongxiao.inventory.bo;

import java.io.Serializable;

/**
 * 只智能分仓返回的模型
 * @author liukai
 */
public class ProductToBeDelivered implements Serializable{
    String productCode; //商品编码

    int quantity; //待出库数量

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


class ProductInventory {
    String productCode; //商品编码
    int warehouseId; //仓库id
    String warehouseName; //仓库名称
    int quantity; //商品数量
    String lotJson; //批次json
}
