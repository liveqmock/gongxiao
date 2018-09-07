package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;

/**
 * 项目余额
 *
 * @author: 陈浩
 * @create: 2018-02-28 16:26
 **/
public class ProjectAccountAmount implements Serializable {
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 返利余额
     */
    private String couponMoney;
    /**
     * 代垫余额
     */
    private String prepaidAmount;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(String prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }
}
