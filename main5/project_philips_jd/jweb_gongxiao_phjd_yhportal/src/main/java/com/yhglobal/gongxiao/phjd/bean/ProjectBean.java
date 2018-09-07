package com.yhglobal.gongxiao.phjd.bean;

import java.util.Date;

/**
 * 项目管理bean
 * @author yuping.lin
 */
public class ProjectBean {
    //eas方项目编码
    private String easProjectCode;
    //项目名称
    private String projectName;
    //公司主体
    private String company;
    //月返
    private Long monthCouponRate;
   //季返
    private Long quarterCouponRate;
    //年返
    private Long annualCouponRate;
    //项目编码'
    private Long projectCode;
    //供应商ID,以逗号分隔
    private Long supplierId;
    private String supplierName;
    //前返利金额
    private Long beforeCouponAmount;
    //后返利金额
    private Long afterCouponAmount;
    //代垫使用比例
    private Long proportionOfSubstitute;
    //返利使用比例
    private Long proportionOfRebateUse;
    //操作人
    private String operator;
    //最后修改时间
    private Long lastModificationTime;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Long getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Long lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public String getEasProjectCode() {
        return easProjectCode;
    }

    public void setEasProjectCode(String easProjectCode) {
        this.easProjectCode = easProjectCode;
    }
    public Long getProportionOfSubstitute() {
        return proportionOfSubstitute;
    }

    public void setProportionOfSubstitute(Long proportionOfSubstitute) {
        this.proportionOfSubstitute = proportionOfSubstitute;
    }

    public Long getProportionOfRebateUse() {
        return proportionOfRebateUse;
    }

    public void setProportionOfRebateUse(Long proportionOfRebateUse) {
        this.proportionOfRebateUse = proportionOfRebateUse;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Long getMonthCouponRate() {
        return monthCouponRate;
    }

    public void setMonthCouponRate(Long monthCouponRate) {
        this.monthCouponRate = monthCouponRate;
    }

    public Long getQuarterCouponRate() {
        return quarterCouponRate;
    }

    public void setQuarterCouponRate(Long quarterCouponRate) {
        this.quarterCouponRate = quarterCouponRate;
    }

    public Long getAnnualCouponRate() {
        return annualCouponRate;
    }

    public void setAnnualCouponRate(Long annualCouponRate) {
        this.annualCouponRate = annualCouponRate;
    }

    public Long getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Long projectCode) {
        this.projectCode = projectCode;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getBeforeCouponAmount() {
        return beforeCouponAmount;
    }

    public void setBeforeCouponAmount(Long beforeCouponAmount) {
        this.beforeCouponAmount = beforeCouponAmount;
    }

    public Long getAfterCouponAmount() {
        return afterCouponAmount;
    }

    public void setAfterCouponAmount(Long afterCouponAmount) {
        this.afterCouponAmount = afterCouponAmount;
    }

}
