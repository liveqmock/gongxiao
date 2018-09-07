package com.yhglobal.gongxiao.payment.account;

import java.io.Serializable;

/**
 * 获取账户余额
 *
 * @author: 陈浩
 * @create: 2018-02-05 10:07
 **/
public class ProjectAccountModel implements Serializable {
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 返利余额
     */
    private long couponAmount;
    /**
     * 代垫余额
     */
    private long prepaidAmount;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public long getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(long couponAmount) {
        this.couponAmount = couponAmount;
    }

    public long getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(long prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }
}
