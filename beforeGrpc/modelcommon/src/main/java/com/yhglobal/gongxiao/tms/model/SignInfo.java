package com.yhglobal.gongxiao.tms.model;

import java.util.Date;

/**
 * 签收接受的参数
 *
 * @author 葛灿
 */
public class SignInfo {
    /**
     * 数据库主键id
     */
    private long rowId;
    /**
     * 出库单号
     */
    private String custOrdNo;
    /**
     * TMS出库单号
     */
    private String tmsOrdNo;
    /**
     * 备注
     */
    private String remark;
    /**
     * 实际签收人
     */
    private String signedBy;
    /**
     * 签收时间维护人
     */
    private String postedBy;
    /**
     * 签收电话
     */
    private String signedPhone;
    /**
     * 签收时间
     */
    private Date signedTime;

    /**
     * 运输商
     */
    private String transporter;

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }

    private Date createTime;

    public String getTmsOrdNo() {
        return tmsOrdNo;
    }

    public void setTmsOrdNo(String tmsOrdNo) {
        this.tmsOrdNo = tmsOrdNo;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getCustOrdNo() {
        return custOrdNo;
    }

    public void setCustOrdNo(String custOrdNo) {
        this.custOrdNo = custOrdNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getSignedPhone() {
        return signedPhone;
    }

    public void setSignedPhone(String signedPhone) {
        this.signedPhone = signedPhone;
    }

    public Date getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(Date signedTime) {
        this.signedTime = signedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
