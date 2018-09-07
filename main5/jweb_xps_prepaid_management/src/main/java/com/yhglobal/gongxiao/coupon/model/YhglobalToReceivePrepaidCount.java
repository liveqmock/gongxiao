package com.yhglobal.gongxiao.coupon.model;

import com.yhglobal.gongxiao.coupon.constant.NumberFormat;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @Description: 越海代垫应收实收汇总
 * @author: LUOYI
 * @Date: Created in 10:11 2018/4/26s
 */
@Service
public class YhglobalToReceivePrepaidCount implements Serializable {
    private Long receiveAmount;//应收金额
    private Long receiptAmount;//实收金额
    private Long toBeConfirmAmount;//未收金额
    private Long confirmAmount;//确认金额
    private Double receiveAmountDouble;//应收金额
    private Double receiptAmountDouble;//实收金额
    private Double toBeConfirmAmountDouble;//未收金额
    private Double confirmAmountDouble;//确认金额

    public String getReceiveAmountStr(){
        return NumberFormat.format(this.receiveAmountDouble,"#,##0.00");
    }
    public String getReceiptAmountStr(){
        return NumberFormat.format(this.receiptAmountDouble,"#,##0.00");
    }
    public String getToBeConfirmAmountStr(){
        return NumberFormat.format(this.toBeConfirmAmountDouble,"#,##0.00");
    }
    public String getConfirmAmountStr(){
        return NumberFormat.format(this.confirmAmountDouble,"#,##0.00");
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

    public Double getToBeConfirmAmountDouble() {
        return toBeConfirmAmountDouble;
    }

    public void setToBeConfirmAmountDouble(Double toBeConfirmAmountDouble) {
        this.toBeConfirmAmountDouble = toBeConfirmAmountDouble;
    }

    public Double getConfirmAmountDouble() {
        return confirmAmountDouble;
    }

    public void setConfirmAmountDouble(Double confirmAmountDouble) {
        this.confirmAmountDouble = confirmAmountDouble;
    }

    public Double getReceiveAmountDouble() {
        return receiveAmountDouble;
    }

    public void setReceiveAmountDouble(Double receiveAmountDouble) {
        this.receiveAmountDouble = receiveAmountDouble;
    }

    public Double getReceiptAmountDouble() {
        return receiptAmountDouble;
    }

    public void setReceiptAmountDouble(Double receiptAmountDouble) {
        this.receiptAmountDouble = receiptAmountDouble;
    }

    public Long getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Long receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Long getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Long receiptAmount) {
        this.receiptAmount = receiptAmount;
    }
}
