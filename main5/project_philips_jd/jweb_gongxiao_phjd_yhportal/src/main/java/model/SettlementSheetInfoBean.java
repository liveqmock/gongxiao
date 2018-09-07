package model;

/**
 * 京东结算单详情
 * @author yuping.lin
 */
public class SettlementSheetInfoBean {
    private int projectId;         //项目ID
    private String reconciliationState;         //对账状态
    private String typesDocumentsPayable;         //应付单据类型
    private String documentNumber;         //单据编号
    private String businessSingleNumber;         //业务单号
    private String jdPurchaseOrderNo;         //京东采购单号
    private String amountOfMoney;         //金额
    private String projectName;         //项目名称
    private String businessOccurrenceTime;         //业务发生时间
    private String buyer;         //采购员
    private String remarks;         //备注
    private String taxDifference;         //税差
    private String allowancePayable;         //应收代垫金额
    private String reconciliationModel;         //对账模式
    private String documentStatus;         //单据状态
    private String describe;         //描述

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getReconciliationState() {
        return reconciliationState;
    }

    public void setReconciliationState(String reconciliationState) {
        this.reconciliationState = reconciliationState;
    }

    public String getTypesDocumentsPayable() {
        return typesDocumentsPayable;
    }

    public void setTypesDocumentsPayable(String typesDocumentsPayable) {
        this.typesDocumentsPayable = typesDocumentsPayable;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBusinessSingleNumber() {
        return businessSingleNumber;
    }

    public void setBusinessSingleNumber(String businessSingleNumber) {
        this.businessSingleNumber = businessSingleNumber;
    }

    public String getJdPurchaseOrderNo() {
        return jdPurchaseOrderNo;
    }

    public void setJdPurchaseOrderNo(String jdPurchaseOrderNo) {
        this.jdPurchaseOrderNo = jdPurchaseOrderNo;
    }

    public String getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(String amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBusinessOccurrenceTime() {
        return businessOccurrenceTime;
    }

    public void setBusinessOccurrenceTime(String businessOccurrenceTime) {
        this.businessOccurrenceTime = businessOccurrenceTime;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTaxDifference() {
        return taxDifference;
    }

    public void setTaxDifference(String taxDifference) {
        this.taxDifference = taxDifference;
    }

    public String getAllowancePayable() {
        return allowancePayable;
    }

    public void setAllowancePayable(String allowancePayable) {
        this.allowancePayable = allowancePayable;
    }

    public String getReconciliationModel() {
        return reconciliationModel;
    }

    public void setReconciliationModel(String reconciliationModel) {
        this.reconciliationModel = reconciliationModel;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSettlementSheet() {
        return settlementSheet;
    }

    public void setSettlementSheet(String settlementSheet) {
        this.settlementSheet = settlementSheet;
    }

    private String settlementSheet;         //结算单

}
