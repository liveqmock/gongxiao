package com.yhglobal.gongxiao.tms.dto;

/**
 * 签收接受的参数
 *
 * @author 葛灿
 */
public class TmsSignInfo {
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
    private String signedTime;
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

    public String getSignedTime() {
        return signedTime;
    }

    public void setSignedTime(String signedTime) {
        this.signedTime = signedTime;
    }
}
