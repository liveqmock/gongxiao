package com.yhglobal.gongxiao.warehouse.model;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/
public class PsiModel implements Serializable {

    String [] time = new String[6];

    String[] inventoryTurnoverRate = new String[6];

    String[] purchaseAmount = new String[6];

    String[] saleAmount = new String [6] ;

    String[] inventoryAmount = new String[6];

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public String[] getInventoryTurnoverRate() {
        return inventoryTurnoverRate;
    }

    public void setInventoryTurnoverRate(String[] inventoryTurnoverRate) {
        this.inventoryTurnoverRate = inventoryTurnoverRate;
    }

    public String[] getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(String[] purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String[] getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String[] saleAmount) {
        this.saleAmount = saleAmount;
    }

    public String[] getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(String[] inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }
}
