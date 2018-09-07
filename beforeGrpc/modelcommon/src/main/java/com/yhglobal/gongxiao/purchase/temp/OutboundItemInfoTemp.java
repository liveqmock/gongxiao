package com.yhglobal.gongxiao.purchase.temp;

/**
 * 入库货品信息
 *
 * @author: 陈浩
 * @create: 2018-03-16 16:21
 **/
public class OutboundItemInfoTemp {
    /**
     * 出库明细单号
     */
    private String outboundItemNumber;
    /**
     * 产品型号
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

    public String getOutboundItemNumber() {
        return outboundItemNumber;
    }

    public void setOutboundItemNumber(String outboundItemNumber) {
        this.outboundItemNumber = outboundItemNumber;
    }

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
