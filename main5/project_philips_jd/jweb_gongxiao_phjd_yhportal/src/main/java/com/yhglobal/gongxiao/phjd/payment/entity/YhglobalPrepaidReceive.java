package com.yhglobal.gongxiao.phjd.payment.entity;

import com.yhglobal.gongxiao.constant.payment.ReceiveStatus;
import com.yhglobal.gongxiao.utils.NumberFormat;

/**
 * @Description: 越海应收代垫
 * @author: LUOYI
 * @Date: Created in 15:06 2018/7/17
 */
public class YhglobalPrepaidReceive {
    private Integer receiveStatus;
    private String orderId;
    private String currencyCode;
    private Double receiveAmount;
    private Double toBeConfirmAmount;

    public String getReceiveStatusStr(){
        return ReceiveStatus.getMessageByCode(this.receiveStatus);
    }
    private String getReceiveAmountStr(){
        return NumberFormat.format(this.receiveAmount,"#,##0.00");
    }
    public Integer getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Integer receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Double getToBeConfirmAmount() {
        return toBeConfirmAmount;
    }

    public void setToBeConfirmAmount(Double toBeConfirmAmount) {
        this.toBeConfirmAmount = toBeConfirmAmount;
    }
}
