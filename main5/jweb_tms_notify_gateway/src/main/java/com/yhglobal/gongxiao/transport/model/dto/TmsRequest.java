package com.yhglobal.gongxiao.transport.model.dto;

import java.io.Serializable;

/**
 * @author: 葛灿
 */
public class TmsRequest implements Serializable {
    /**
     * 供销出库单号
     */
    private String custOrdNo;
    /**
     * TMS系统中的单号
     */
    private String tmsOrdNo;
    /**
     * 通知类型
     * 签收为sign
     * 回告为receipt
     */
    private String notificationType;
    /**
     * 通知类型为sign,对象类型为SignInfo
     * 通知类型为receipt,对象类型为List<SignedPictrueMsg>
     */
    private String data;
    /**
     * 备注
     */
    private String remark;

    public String getCustOrdNo() {
        return custOrdNo;
    }

    public void setCustOrdNo(String custOrdNo) {
        this.custOrdNo = custOrdNo;
    }

    public String getTmsOrdNo() {
        return tmsOrdNo;
    }

    public void setTmsOrdNo(String tmsOrdNo) {
        this.tmsOrdNo = tmsOrdNo;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
