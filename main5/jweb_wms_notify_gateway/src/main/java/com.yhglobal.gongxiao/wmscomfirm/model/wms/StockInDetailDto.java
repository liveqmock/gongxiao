package com.yhglobal.gongxiao.wmscomfirm.model.wms;


import java.io.Serializable;
import java.util.List;

public class StockInDetailDto implements Serializable{
    private String cargoCode;                   //货物编码
    private String ownerCode;                   //货主编码
    private Boolean isCompleted;                //是否入库完成
    private List<StocksQtyDto> stocksQty;       //入库数量

    public String getCargoCode() {
        return cargoCode;
    }

    public void setCargoCode(String cargoCode) {
        this.cargoCode = cargoCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean completed) {
        isCompleted = completed;
    }

    public List<StocksQtyDto> getStocksQty() {
        return stocksQty;
    }

    public void setStocksQty(List<StocksQtyDto> stocksQty) {
        this.stocksQty = stocksQty;
    }
}
