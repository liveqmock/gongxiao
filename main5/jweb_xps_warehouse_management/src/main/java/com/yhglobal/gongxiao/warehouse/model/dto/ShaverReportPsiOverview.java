package com.yhglobal.gongxiao.warehouse.model.dto;

public class ShaverReportPsiOverview {
    private Long id;

    private Long yearMonth;

    private Long purchaseAmount;

    private Long saleAmount;

    private Long inventoryAmount;

    private Long inventoryTurnoverRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Long yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Long getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(Long purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Long getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Long saleAmount) {
        this.saleAmount = saleAmount;
    }

    public Long getInventoryAmount() {
        return inventoryAmount;
    }

    public void setInventoryAmount(Long inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }

    public Long getInventoryTurnoverRate() {
        return inventoryTurnoverRate;
    }

    public void setInventoryTurnoverRate(Long inventoryTurnoverRate) {
        this.inventoryTurnoverRate = inventoryTurnoverRate;
    }
}