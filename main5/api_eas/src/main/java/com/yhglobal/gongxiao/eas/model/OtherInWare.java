package com.yhglobal.gongxiao.eas.model;

import java.io.Serializable;

/**
 * 其他入库
 *
 * @author: 陈浩
 **/
public class OtherInWare implements Serializable {

    private String projectId; //项目名

    private double number; //数量

    private double totalTaxAmount;//含税金额

    private String creatorId;//创建者

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
