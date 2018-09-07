package com.yhglobal.gongxiao.phjd.bean;

/**
 * 客户bean
 * @author yuping.lin
 */
public class CustomerBean {
    private String easDistributorCode; //EAS客户编码
    private String easDistributorName;   //客户名称
    private String account;            //客户账号
    private String password;           //密码
    private Long createdById;       //操作人id
    private String createdByName;     //操作人名称
    private String lastUpdateTime;    //最后修改时间
    private int easStatus;          //数据状态

    private Integer settlementMethod;   //结算方式
    private String customerCode;   //客户编码
    private Integer accountPeriod;   //账期
    private String dutyParagraph;  //税号
    private String mailbox;   //邮箱
    private String contactInformation;   //联系方式
    private String businessAddress;   //营业地址

    public Integer getSettlementMethod() {
        return settlementMethod;
    }

    public void setSettlementMethod(Integer settlementMethod) {
        this.settlementMethod = settlementMethod;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Integer getAccountPeriod() {
        return accountPeriod;
    }

    public void setAccountPeriod(Integer accountPeriod) {
        this.accountPeriod = accountPeriod;
    }

    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getEasDistributorCode() {
        return easDistributorCode;
    }

    public void setEasDistributorCode(String easDistributorCode) {
        this.easDistributorCode = easDistributorCode;
    }

    public String getEasDistributorName() {
        return easDistributorName;
    }

    public void setEasDistributorName(String easDistributorName) {
        this.easDistributorName = easDistributorName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public int getEasStatus() {
        return easStatus;
    }

    public void setEasStatus(int easStatus) {
        this.easStatus = easStatus;
    }

}
