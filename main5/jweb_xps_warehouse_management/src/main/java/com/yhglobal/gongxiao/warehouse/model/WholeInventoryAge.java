package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;

/**
 * 报表整体库龄
 */
public class WholeInventoryAge implements Serializable{
    private String[] stage  = new String[5];         //日期阶段
    private double[] sumOfmoney = new double[5];//金额
    private double[] proportion= new double[5]; //金额占比

    public String[] getStage() {
        return stage;
    }

    public void setStage(String[] stage) {
        this.stage = stage;
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
