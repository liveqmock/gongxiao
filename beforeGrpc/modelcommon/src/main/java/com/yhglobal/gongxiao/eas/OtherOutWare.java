package com.yhglobal.gongxiao.eas;

import java.io.Serializable;

/**
 * 其他出库订单信息
 *
 * @author: 陈浩
 **/
public class OtherOutWare implements Serializable {

    private String projectId;

    private int number;    //出库数量

    private double totalTaxAmount; //总金额

    private String creatorId;

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }
}
