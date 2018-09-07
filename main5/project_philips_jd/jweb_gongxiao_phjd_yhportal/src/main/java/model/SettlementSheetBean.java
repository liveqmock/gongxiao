package model;

/**
 * 京东结算单
 * @author yuping.lin
 */
public class SettlementSheetBean {
    private int projectId;         //项目ID
    private String JDSettlementNumber;         //京东结算单号
    private String applicationDate;         //申请日期
    private String reconciliationState;         //对账状态
    private String reconciliationTime;         //对账时间
    private String totalJDReceivable;         //京东应收合计
    private String JDTotal;         //京东应付合计
    private String JDShouldAggregate;         //京东应结合计
    private String JDOpeningAmount;         //京东开票金额
    private String taxDifference;         //税差
    private String supplierBody;         //供应商主体
    private String contractSubject;         //合同主体
    private String importTime;         //导入时间
    private String ticketOpening;         //开票状态
    private String dateTicketOpening;         //开票日期
    private String receivables;         //收款状态
    private String receiptDay;         //收款日

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getJDSettlementNumber() {
        return JDSettlementNumber;
    }

    public void setJDSettlementNumber(String JDSettlementNumber) {
        this.JDSettlementNumber = JDSettlementNumber;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getReconciliationState() {
        return reconciliationState;
    }

    public void setReconciliationState(String reconciliationState) {
        this.reconciliationState = reconciliationState;
    }

    public String getReconciliationTime() {
        return reconciliationTime;
    }

    public void setReconciliationTime(String reconciliationTime) {
        this.reconciliationTime = reconciliationTime;
    }

    public String getTotalJDReceivable() {
        return totalJDReceivable;
    }

    public void setTotalJDReceivable(String totalJDReceivable) {
        this.totalJDReceivable = totalJDReceivable;
    }

    public String getJDTotal() {
        return JDTotal;
    }

    public void setJDTotal(String JDTotal) {
        this.JDTotal = JDTotal;
    }

    public String getJDShouldAggregate() {
        return JDShouldAggregate;
    }

    public void setJDShouldAggregate(String JDShouldAggregate) {
        this.JDShouldAggregate = JDShouldAggregate;
    }

    public String getJDOpeningAmount() {
        return JDOpeningAmount;
    }

    public void setJDOpeningAmount(String JDOpeningAmount) {
        this.JDOpeningAmount = JDOpeningAmount;
    }

    public String getTaxDifference() {
        return taxDifference;
    }

    public void setTaxDifference(String taxDifference) {
        this.taxDifference = taxDifference;
    }

    public String getSupplierBody() {
        return supplierBody;
    }

    public void setSupplierBody(String supplierBody) {
        this.supplierBody = supplierBody;
    }

    public String getContractSubject() {
        return contractSubject;
    }

    public void setContractSubject(String contractSubject) {
        this.contractSubject = contractSubject;
    }

    public String getImportTime() {
        return importTime;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public String getTicketOpening() {
        return ticketOpening;
    }

    public void setTicketOpening(String ticketOpening) {
        this.ticketOpening = ticketOpening;
    }

    public String getDateTicketOpening() {
        return dateTicketOpening;
    }

    public void setDateTicketOpening(String dateTicketOpening) {
        this.dateTicketOpening = dateTicketOpening;
    }

    public String getReceivables() {
        return receivables;
    }

    public void setReceivables(String receivables) {
        this.receivables = receivables;
    }

    public String getReceiptDay() {
        return receiptDay;
    }

    public void setReceiptDay(String receiptDay) {
        this.receiptDay = receiptDay;
    }

}
