package com.yhglobal.gongxiao.purchase.dto;

import java.io.Serializable;

/**
 * 计划入库单的货品
 *
 * @author: 陈浩
 * @create: 2018-03-01 12:25
 **/
public class PlanInstockItemOrder implements Serializable {
    /**
     * 货品编码
     */
    private String productCode;
    /**
     * 该货品的计划入库单号
     */
    private String itemInstockOrderNo;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getItemInstockOrderNo() {
        return itemInstockOrderNo;
    }

    public void setItemInstockOrderNo(String itemInstockOrderNo) {
        this.itemInstockOrderNo = itemInstockOrderNo;
    }
}
