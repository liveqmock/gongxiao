package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;

/**
 * 报表库龄大于180
 */
public class ProductInventory180Age implements Serializable {
    private String [] productCode; //商品型号
    private double [] sumOfmoney; //金额
    private double [] proportion; //金额占比

    public String[] getProductCode() {
        return productCode;
    }

    public void setProductCode(String[] productCode) {
        this.productCode = productCode;
    }

    public double[] getSumOfmoney() {
        return sumOfmoney;
    }

    public void setSumOfmoney(double[] sumOfmoney) {
        this.sumOfmoney = sumOfmoney;
    }

    public double[] getProportion() {
        return proportion;
    }

    public void setProportion(double[] proportion) {
        this.proportion = proportion;
    }
}
