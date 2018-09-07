package com.yhglobal.gongxiao.inventory.model;

import java.io.Serializable;

/**
 * 库存excelmodel
 * @author liukai
 */
public class InventoryExcelModel implements Serializable {
    private String material;                 //物料
    private String materialCode;             //物料编码
    private String productCode;              //商品编码
    private String productUnit;           //计量单位
    private String basicUnit;   //基本计量单位
    private int quantity;       //数量
    private int availabalQuantity;  //可用数量
    private int basicQuantity;      //基本数量
    private String batchNo;                  //批次
    private double availabalBasicQuantity;      //可用基本数量
    private String productTime;              //生产日期
    private String deadlineTime;             //到期日期
    private double helpQuantity;               //辅助数量
    private double availableHelpQuantity;       //可用辅助数量
    private String helpUnit;                 //辅助计量单位
    private String helpNatue;                //辅助属性
    private String warehouseName;            //仓库名称
    private int position;                 //库位 （对应我们的采购类型 货补，普通采购，赠品）
    private String inventoryType;            //库存类型
    private String inventoryStatus;       //库存状态
    private String customer;                 //客户
    private String supplier;                 //供应商
    private String project;                  //项目号
    private String traceNo;                  //跟踪号
    private String inventoryManagement;      //库存组织
    private int planReserveQuantity;         //预留数量
    private int PlanReserveBasicQuantity;    //预留基本数量
    private int PlanReserveHelQuantity;    //预留基本数量
    private String entryTime;    //入库时间
    private int giftStatus;     //是否赠品
    private int inventoryQuality;   //库存品质
    private int inventoryQuantity;  //库存数量
    private int occupyQuantity;     //销售占用数量
    private double guidePurchasePrice;  //采购指导价
    private double purchasePrice;       //采购价
    private double costPrice;                //成本价
    private double shouldRebate;                   //应收返利
    private double actualRebate;             //实收返利
    private double cashDiscount;          //现金折扣
    private double salesTotalAmount;          //销售总金额
    private String createTime;                 //最终出货时间

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getBasicUnit() {
        return basicUnit;
    }

    public void setBasicUnit(String basicUnit) {
        this.basicUnit = basicUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailabalQuantity() {
        return availabalQuantity;
    }

    public void setAvailabalQuantity(int availabalQuantity) {
        this.availabalQuantity = availabalQuantity;
    }

    public int getBasicQuantity() {
        return basicQuantity;
    }

    public void setBasicQuantity(int basicQuantity) {
        this.basicQuantity = basicQuantity;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public double getAvailabalBasicQuantity() {
        return availabalBasicQuantity;
    }

    public void setAvailabalBasicQuantity(double availabalBasicQuantity) {
        this.availabalBasicQuantity = availabalBasicQuantity;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public double getHelpQuantity() {
        return helpQuantity;
    }

    public void setHelpQuantity(double helpQuantity) {
        this.helpQuantity = helpQuantity;
    }

    public double getAvailableHelpQuantity() {
        return availableHelpQuantity;
    }

    public void setAvailableHelpQuantity(double availableHelpQuantity) {
        this.availableHelpQuantity = availableHelpQuantity;
    }

    public String getHelpUnit() {
        return helpUnit;
    }

    public void setHelpUnit(String helpUnit) {
        this.helpUnit = helpUnit;
    }

    public String getHelpNatue() {
        return helpNatue;
    }

    public void setHelpNatue(String helpNatue) {
        this.helpNatue = helpNatue;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getInventoryManagement() {
        return inventoryManagement;
    }

    public void setInventoryManagement(String inventoryManagement) {
        this.inventoryManagement = inventoryManagement;
    }

    public int getPlanReserveQuantity() {
        return planReserveQuantity;
    }

    public void setPlanReserveQuantity(int planReserveQuantity) {
        this.planReserveQuantity = planReserveQuantity;
    }

    public int getPlanReserveBasicQuantity() {
        return PlanReserveBasicQuantity;
    }

    public void setPlanReserveBasicQuantity(int planReserveBasicQuantity) {
        PlanReserveBasicQuantity = planReserveBasicQuantity;
    }

    public int getPlanReserveHelQuantity() {
        return PlanReserveHelQuantity;
    }

    public void setPlanReserveHelQuantity(int planReserveHelQuantity) {
        PlanReserveHelQuantity = planReserveHelQuantity;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public int getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(int giftStatus) {
        this.giftStatus = giftStatus;
    }

    public int getInventoryQuality() {
        return inventoryQuality;
    }

    public void setInventoryQuality(int inventoryQuality) {
        this.inventoryQuality = inventoryQuality;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public int getOccupyQuantity() {
        return occupyQuantity;
    }

    public void setOccupyQuantity(int occupyQuantity) {
        this.occupyQuantity = occupyQuantity;
    }

    public double getGuidePurchasePrice() {
        return guidePurchasePrice;
    }

    public void setGuidePurchasePrice(double guidePurchasePrice) {
        this.guidePurchasePrice = guidePurchasePrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getShouldRebate() {
        return shouldRebate;
    }

    public void setShouldRebate(double shouldRebate) {
        this.shouldRebate = shouldRebate;
    }

    public double getActualRebate() {
        return actualRebate;
    }

    public void setActualRebate(double actualRebate) {
        this.actualRebate = actualRebate;
    }

    public double getCashDiscount() {
        return cashDiscount;
    }

    public void setCashDiscount(double cashDiscount) {
        this.cashDiscount = cashDiscount;
    }

    public double getSalesTotalAmount() {
        return salesTotalAmount;
    }

    public void setSalesTotalAmount(double salesTotalAmount) {
        this.salesTotalAmount = salesTotalAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
