package com.yhglobal.gongxiao.warehouseapi.report;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class PsiModel implements Serializable {

    String [] time = new String[6];

    double[] inventoryTurnoverRate = new double[6];

    double[] purchaseAmount = new double[6];

    double[] saleAmount = new double [6] ;

    double[] inventoryAmount = new double[6];

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public double[] getInventoryTurnoverRate() {
        return inventoryTurnoverRate;
    }

    public void setInventoryTurnoverRate(double[] inventoryTurnoverRate) {
        this.inventoryTurnoverRate = inventoryTurnoverRate;
    }

    public double[] getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double[] purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public double[] getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(double[] saleAmount) {
        this.saleAmount = saleAmount;
    }

    public double[] getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(double[] inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }
}
