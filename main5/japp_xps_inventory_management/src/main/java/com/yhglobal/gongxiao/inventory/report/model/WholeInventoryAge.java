package com.yhglobal.gongxiao.inventory.report.model;

import java.io.Serializable;

/**
 * 整体库龄
 */
public class WholeInventoryAge implements Serializable{
    private int stage;         //日期阶段
    private double sumOfmoney; //金额
    private double proportion; //金额占比

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public double getSumOfmoney() {
        return sumOfmoney;
    }

    public void setSumOfmoney(double sumOfmoney) {
        this.sumOfmoney = sumOfmoney;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }
}
