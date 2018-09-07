package com.yhglobal.gongxiao.coupon.model;


import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 越海代垫确认
 * @author: LUOYI
 * @Date: Created in 10:11 2018/4/26
 */
public class YhglobalPrepaidLedgerWriteoffRecord  implements Serializable {

    String tablePrefix;
    private Long writeoffId;// 核销id
    private Long receiveId;//应收ID;
    private Long confirmAmount;//确认金额;
    private Double confirmAmountDouble;
    private Long receiptAmount;//实收金额;
    private Double receiptAmountDouble;
    private String currencyCode;//币种编码;
    private String flowCode;//流水号;
    private Long projectId;//项目ID
    private Date useDate;//使用日期
    private Integer accountType;//帐户类型
    private String accountTypeStr;
    private  String useCurrencyCode;//使用币种
    private Integer isRollback;//是否回滚
    private String philipNo;//philip单据号

    private long createdById;
    private Long dataVersion;
    private String tracelog;
    private Date createTime;
    private Date lastUpdateTime;
    private String createdByName;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getAccountTypeStr() {
        return accountTypeStr;
    }

    public void setAccountTypeStr(String accountTypeStr) {
        this.accountTypeStr = accountTypeStr;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public Long getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(Long dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getTracelog() {
        return tracelog;
    }

    public void setTracelog(String tracelog) {
        this.tracelog = tracelog;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
    public Integer getIsRollback() {
        return isRollback;
    }

    public String getPhilipNo() {
        return philipNo;
    }

    public void setPhilipNo(String philipNo) {
        this.philipNo = philipNo;
    }

    public void setIsRollback(Integer isRollback) {
        this.isRollback = isRollback;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getUseCurrencyCode() {
        return useCurrencyCode;
    }

    public void setUseCurrencyCode(String useCurrencyCode) {
        this.useCurrencyCode = useCurrencyCode;
    }

    public Double getConfirmAmountDouble() {
        return confirmAmountDouble;
    }

    public void setConfirmAmountDouble(Double confirmAmountDouble) {
        this.confirmAmountDouble = confirmAmountDouble;
    }

    public Double getReceiptAmountDouble() {
        return receiptAmountDouble;
    }

    public void setReceiptAmountDouble(Double receiptAmountDouble) {
        this.receiptAmountDouble = receiptAmountDouble;
    }

    public Long getWriteoffId() {
        return writeoffId;
    }

    public void setWriteoffId(Long writeoffId) {
        this.writeoffId = writeoffId;
    }

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public Long getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(Long confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public Long getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Long receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }
}
