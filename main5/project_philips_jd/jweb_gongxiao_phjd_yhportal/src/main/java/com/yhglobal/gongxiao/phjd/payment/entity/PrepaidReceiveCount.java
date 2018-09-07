package com.yhglobal.gongxiao.phjd.payment.entity;

import com.yhglobal.gongxiao.utils.NumberFormat;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 15:42 2018/7/17
 */
public class PrepaidReceiveCount {
    private Double receiveAmount;//应收金额
    private Double receiptAmount;//实收金额
    private Double toBeConfirmAmount;//未收金额
    private Double confirmAmount;//确认金额
    public String getReceiveAmountStr(){
        return NumberFormat.format(this.receiveAmount,"#,##0.00");
    }
    public String getReceiptAmountStr(){
        return NumberFormat.format(this.receiptAmount,"#,##0.00");
    }
    public String getToBeConfirmAmountStr(){
        return NumberFormat.format(this.toBeConfirmAmount,"#,##0.00");
    }
    public String getConfirmAmountStr(){
        return NumberFormat.format(this.confirmAmount,"#,##0.00");
    }
    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Double getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Double receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Double getToBeConfirmAmount() {
        return toBeConfirmAmount;
    }

    public void setToBeConfirmAmount(Double toBeConfirmAmount) {
        this.toBeConfirmAmount = toBeConfirmAmount;
    }

    public Double getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(Double confirmAmount) {
        this.confirmAmount = confirmAmount;
    }
}
