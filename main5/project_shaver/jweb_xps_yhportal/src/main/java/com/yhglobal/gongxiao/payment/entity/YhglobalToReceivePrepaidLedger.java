package com.yhglobal.gongxiao.payment.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yhglobal.gongxiao.constant.payment.AccountType;
import com.yhglobal.gongxiao.constant.payment.ReceiveStatus;
import com.yhglobal.gongxiao.utils.NumberFormat;


import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 越海代垫应收
 * @author: LUOYI
 * @Date: Created in 10:11 2018/4/26s
 */
public class YhglobalToReceivePrepaidLedger implements Serializable {

    String tablePrefix;
    private  Long receiveId;//应收id
    private String orderId;//订单号
    private Long projectId;//项目ID
    private String projectName;//项目名称
    private Integer supplierId;//供应商ID
    private String supplierName;//供应商名称
    private String salesContractNo;//销售合同号
    private Long receiveAmount;//应收金额
    private Double receiveAmountDouble;
    private String currencyCode;//币种编码
    private int receiveStatus;//应收状态
    private Long toBeConfirmAmount;//待确认金额
    private Double toBeConfirmAmountDouble;
    private Long confirmAmount;//确认金额
    private Double confirmAmountDouble;
    private Long receiptAmount;//实收金额
    private Double receiptAmountDouble;
    private Date dateInto;//转入日期
    private String differenceAmountAdjustPattern;//差额调整方式
    private String confirmRecord;//确认记录 确认表ID json串
    private String confirmCurrencyCode;//确认币种
    @JSONField(format="yyyy年MM月dd日 HH:mm")
    private Date confirmTime;//确认时间
    private Integer accountType;//帐户类型
    private String flowCode;//流水号
    @JSONField(format="yyyy年MM月dd日")
    private Date useDate;//使用日期
    @JSONField(format="yyyy年MM月dd日")
    private Date createTime;
    private long createdById;
    private Long dataVersion;
    private String tracelog;
    private Date lastUpdateTime;
    private String createdByName;

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getReceiveAmountStr(){
        return NumberFormat.format(this.receiveAmountDouble,"#,##0.00");
    }
    public String getReceiptAmountStr(){
        return NumberFormat.format(this.receiptAmountDouble,"#,##0.00");
    }

    public String getConfirmAmountStr(){
        return NumberFormat.format(this.confirmAmountDouble,"#,##0.00");
    }
    public String getToBeConfirmAmountStr() {
        return NumberFormat.format(this.toBeConfirmAmountDouble,"#,##0.00");
    }
    public String getReceiveStatusStr() {
        return ReceiveStatus.getMessageByCode(this.receiveStatus);
    }
    public String getAccountTypeStr() {
        if (this.accountType != null) {
            return AccountType.getAccountTypeByCode(this.accountType).getMessage();
        }else {
            return null;
        }
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getFlowCode() {
        return flowCode;
    }

    public void setFlowCode(String flowCode) {
        this.flowCode = flowCode;
    }

    public String getConfirmCurrencyCode() {
        return confirmCurrencyCode;
    }

    public void setConfirmCurrencyCode(String confirmCurrencyCode) {
        this.confirmCurrencyCode = confirmCurrencyCode;
    }

    public Double getToBeConfirmAmountDouble() {
        return toBeConfirmAmountDouble;
    }

    public void setToBeConfirmAmountDouble(Double toBeConfirmAmountDouble) {
        this.toBeConfirmAmountDouble = toBeConfirmAmountDouble;
    }

    public Double getReceiveAmountDouble() {
        return receiveAmountDouble;
    }

    public void setReceiveAmountDouble(Double receiveAmountDouble) {
        this.receiveAmountDouble = receiveAmountDouble;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public Long getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Long receiveId) {
        this.receiveId = receiveId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSalesContractNo() {
        return salesContractNo;
    }

    public void setSalesContractNo(String salesContractNo) {
        this.salesContractNo = salesContractNo;
    }

    public Long getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Long receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Long getToBeConfirmAmount() {
        return toBeConfirmAmount;
    }

    public void setToBeConfirmAmount(Long toBeConfirmAmount) {
        this.toBeConfirmAmount = toBeConfirmAmount;
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

    public Date getDateInto() {
        return dateInto;
    }

    public void setDateInto(Date dateInto) {
        this.dateInto = dateInto;
    }

    public String getDifferenceAmountAdjustPattern() {
        return differenceAmountAdjustPattern;
    }

    public void setDifferenceAmountAdjustPattern(String differenceAmountAdjustPattern) {
        this.differenceAmountAdjustPattern = differenceAmountAdjustPattern;
    }

    public String getConfirmRecord() {
        return confirmRecord;
    }

    public void setConfirmRecord(String confirmRecord) {
        this.confirmRecord = confirmRecord;
    }

}
