package com.yhglobal.gongxiao.constant.sales;
/**
 *商品销售状态
 *
 *@auther weizecheng
 *@date $date& &time&
 */
public enum  SalesOrderItemStatusEnum {
    /**
     * 正常销售状态
     */
    NORMAL_OUT(1,"正常出货"),
    /**
     * 商品缺货状态
     */
    STOCK_OUT(2,"缺货");

    private Integer status;

    private String information;


    SalesOrderItemStatusEnum(Integer status, String information) {
        this.status = status;
        this.information = information;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
