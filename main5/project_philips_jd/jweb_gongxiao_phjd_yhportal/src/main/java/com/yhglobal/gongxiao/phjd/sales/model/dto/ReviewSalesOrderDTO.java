package com.yhglobal.gongxiao.phjd.sales.model.dto;

import java.io.Serializable;

/**
 * 审核订单DTO
 *
 * @auther weizecheng
 * @date 2018/8/24 17:03
 */
public class ReviewSalesOrderDTO implements Serializable {

    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 采购数量
     */
    private Integer count;

    /**
     * 拒绝原因
     */
    private String shortageReason;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getShortageReason() {
        return shortageReason;
    }

    public void setShortageReason(String shortageReason) {
        this.shortageReason = shortageReason;
    }


}
