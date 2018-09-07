package com.yhglobal.gongxiao.phjd.bean;

import java.util.List;

/**
 * 货品
 * @author yuping.lin
 */
public class ProductBean {
    private int packageNumber;          //箱装数
    private int recordStatus;          //数据状态
    private String productName;          //货品名称
    private String productModel;          //型号
    private String easCode;          //eas物料编码
    private String wmsCode;          //wms物料编码

    private String goodsName;        //货品名称
    private String specificationMode;  //规格型号
    private String customerSKUCoding;        //客户SKU编码
    private String customerCommodityCoding;        //客户商品编码
    private Long purchasingGuidancePrice;        //采购指导价
    private Long taxRate;        //税率
    private String taxonomyTaxRate;        //税率分类编码
    private Long salesGuidancePrice;        //销售指导价
    private Long realSalesRebate;        //实销返点
    private Long pinBackPoint;        //应销返点
    private Long factorySendRebatePoint;        //厂送返利点
    private Long costPrice;        //成本价
    private Long exportPrice;        //出货价
    private Long salesSupportDiscounts;        //销售支持折扣
    private int generatingRebatesPurchasePrice;        //按采购价生成返利
    private int salelong;        //货品属性长
    private int salewide;        //货品属性宽
    private int salehigh;        //货品属性高
    private int saleweight;        //货品属性重量
    private int transportlong;        //运输长
    private int transportwide;        //运输宽
    private int transporthigh;        //运输高
    private int transportweight;        //运输重量
    private List<PurchaseLog> purchaseLogList;  //日志信息

    public Long getSalesGuidancePrice() {
        return salesGuidancePrice;
    }

    public void setSalesGuidancePrice(Long salesGuidancePrice) {
        this.salesGuidancePrice = salesGuidancePrice;
    }

    public Long getRealSalesRebate() {
        return realSalesRebate;
    }

    public void setRealSalesRebate(Long realSalesRebate) {
        this.realSalesRebate = realSalesRebate;
    }

    public Long getPinBackPoint() {
        return pinBackPoint;
    }

    public void setPinBackPoint(Long pinBackPoint) {
        this.pinBackPoint = pinBackPoint;
    }

    public Long getFactorySendRebatePoint() {
        return factorySendRebatePoint;
    }

    public void setFactorySendRebatePoint(Long factorySendRebatePoint) {
        this.factorySendRebatePoint = factorySendRebatePoint;
    }

    public Long getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public Long getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(Long exportPrice) {
        this.exportPrice = exportPrice;
    }

    public Long getSalesSupportDiscounts() {
        return salesSupportDiscounts;
    }

    public void setSalesSupportDiscounts(Long salesSupportDiscounts) {
        this.salesSupportDiscounts = salesSupportDiscounts;
    }

    public int getGeneratingRebatesPurchasePrice() {
        return generatingRebatesPurchasePrice;
    }

    public void setGeneratingRebatesPurchasePrice(int generatingRebatesPurchasePrice) {
        this.generatingRebatesPurchasePrice = generatingRebatesPurchasePrice;
    }

    public int getSalelong() {
        return salelong;
    }

    public void setSalelong(int salelong) {
        this.salelong = salelong;
    }

    public int getSalewide() {
        return salewide;
    }

    public void setSalewide(int salewide) {
        this.salewide = salewide;
    }

    public int getSalehigh() {
        return salehigh;
    }

    public void setSalehigh(int salehigh) {
        this.salehigh = salehigh;
    }

    public int getSaleweight() {
        return saleweight;
    }

    public void setSaleweight(int saleweight) {
        this.saleweight = saleweight;
    }

    public int getTransportlong() {
        return transportlong;
    }

    public void setTransportlong(int transportlong) {
        this.transportlong = transportlong;
    }

    public int getTransportwide() {
        return transportwide;
    }

    public void setTransportwide(int transportwide) {
        this.transportwide = transportwide;
    }

    public int getTransporthigh() {
        return transporthigh;
    }

    public void setTransporthigh(int transporthigh) {
        this.transporthigh = transporthigh;
    }

    public int getTransportweight() {
        return transportweight;
    }

    public void setTransportweight(int transportweight) {
        this.transportweight = transportweight;
    }

    public List<PurchaseLog> getPurchaseLogList() {
        return purchaseLogList;
    }

    public void setPurchaseLogList(List<PurchaseLog> purchaseLogList) {
        this.purchaseLogList = purchaseLogList;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpecificationMode() {
        return specificationMode;
    }

    public void setSpecificationMode(String specificationMode) {
        this.specificationMode = specificationMode;
    }

    public String getCustomerSKUCoding() {
        return customerSKUCoding;
    }

    public void setCustomerSKUCoding(String customerSKUCoding) {
        this.customerSKUCoding = customerSKUCoding;
    }

    public String getCustomerCommodityCoding() {
        return customerCommodityCoding;
    }

    public void setCustomerCommodityCoding(String customerCommodityCoding) {
        this.customerCommodityCoding = customerCommodityCoding;
    }

    public Long getPurchasingGuidancePrice() {
        return purchasingGuidancePrice;
    }

    public void setPurchasingGuidancePrice(Long purchasingGuidancePrice) {
        this.purchasingGuidancePrice = purchasingGuidancePrice;
    }

    public Long getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxonomyTaxRate() {
        return taxonomyTaxRate;
    }

    public void setTaxonomyTaxRate(String taxonomyTaxRate) {
        this.taxonomyTaxRate = taxonomyTaxRate;
    }



    public int getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(int packageNumber) {
        this.packageNumber = packageNumber;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getEasCode() {
        return easCode;
    }

    public void setEasCode(String easCode) {
        this.easCode = easCode;
    }

    public String getWmsCode() {
        return wmsCode;
    }

    public void setWmsCode(String wmsCode) {
        this.wmsCode = wmsCode;
    }

//    private String ;          //
//    private String ;          //
//    private String ;          //
//    private String ;          //
//    int64 productBusinessId=1;
//    int64 productBasicId=2;
//    string projectId=3;
//    string projectName=4;
//    //数据状态
//    int32 recordStatus=5;
//    int32 easSynStatus=6;
//    //货品名称
//    string productName=7;
//    //型号
//    string productModel=8;
//    //eas物料编码
//    string easCode=9;
//    //wms物料编码
//    string wmsCode=10;
//    //箱装数
//    int32 packageNumber =122;
//    string customerSKUCode=11;
//    string customerProductCode=12;
//    int64 purchaseGuidePrice=13;
//    int64 taxRate=14;
//    string taxCode=15;
//    int64 saleGuidePrice=16;
//    int64 actualSaleReturn=17;
//    int64 shouldSaleReturn=18;
//    int64 factorySendReturn=19;
//    int64 costPrice=20;
//    int64 outPrice=21;
//    int32 generateCoupon=22;
//    int64 createTime=23;
//    int64 lastUpdateTime=24;
//    string traceLog=25;
//    string easUnitCode=26;
}
