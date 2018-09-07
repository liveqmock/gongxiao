package com.yhglobal.gongxiao.purchase.bo;

import java.io.Serializable;

/**
 * 项目信息
 *
 * @author: 陈浩
 * @create: 2018-02-27 14:36
 **/
public class ProjectInfo implements Serializable {
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 下达新采购单代垫使用率
     */
    private Double prepaidRate;
    /**
     * 下达新采购单返利使用率
     */
    private Double couponRate;
    /**
     * 生成返利比例
     */
    private double couponTotalRate;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Double getPrepaidRate() {
        return prepaidRate;
    }

    public void setPrepaidRate(Double prepaidRate) {
        this.prepaidRate = prepaidRate;
    }

    public Double getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(Double couponRate) {
        this.couponRate = couponRate;
    }

    public double getCouponTotalRate() {
        return couponTotalRate;
    }

    public void setCouponTotalRate(double couponTotalRate) {
        this.couponTotalRate = couponTotalRate;
    }
}
