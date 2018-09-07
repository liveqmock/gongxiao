package com.yhglobal.gongxiao.purchase.temp;

/**
 * 产品信息
 *
 * @author: 陈浩
 * @create: 2018-03-16 16:14
 **/
public class ProductInfoTemp {
    /**
     * 产品编号
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 退货数量
     */
    private int returnNumber;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(int returnNumber) {
        this.returnNumber = returnNumber;
    }
}
