package com.yhglobal.gongxiao.inventory.model.bo;


/**
 * 商品库存短概要：
 * (a) 等于某项目下 该商品所有仓库的数据汇总
 * (b) 不含各分仓库的库存信息
 */


public class ProductInventoryBriefSummary {

    String productCode;  //商品编码

    int onHandQuantity;  //当前在库总数量(含残次品 已销售但未出库等)

    int defectiveQuantity;  //残次品数量

    int onPurchaseOrderQuantity;  //已采购 但尚未发货的数量

    int onShippingQuantity;  //已采购 已发货在途尚未入库的数量

    int onSalesPlanQuantity;  //已销售 但尚未发货的数量


    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getOnHandQuantity() {
        return onHandQuantity;
    }

    public void setOnHandQuantity(int onHandQuantity) {
        this.onHandQuantity = onHandQuantity;
    }

    public int getDefectiveQuantity() {
        return defectiveQuantity;
    }

    public void setDefectiveQuantity(int defectiveQuantity) {
        this.defectiveQuantity = defectiveQuantity;
    }

    public int getOnPurchaseOrderQuantity() {
        return onPurchaseOrderQuantity;
    }

    public void setOnPurchaseOrderQuantity(int onPurchaseOrderQuantity) {
        this.onPurchaseOrderQuantity = onPurchaseOrderQuantity;
    }

    public int getOnShippingQuantity() {
        return onShippingQuantity;
    }

    public void setOnShippingQuantity(int onShippingQuantity) {
        this.onShippingQuantity = onShippingQuantity;
    }

    public int getOnSalesPlanQuantity() {
        return onSalesPlanQuantity;
    }

    public void setOnSalesPlanQuantity(int onSalesPlanQuantity) {
        this.onSalesPlanQuantity = onSalesPlanQuantity;
    }

}
