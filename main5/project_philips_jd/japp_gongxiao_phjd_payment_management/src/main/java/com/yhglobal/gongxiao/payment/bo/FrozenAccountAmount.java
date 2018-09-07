package com.yhglobal.gongxiao.payment.bo;

import com.yhglobal.gongxiao.utils.NumberFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 王帅
 */
public class FrozenAccountAmount implements Serializable {

    private BigDecimal discountFrozenAccountAmount;

    private BigDecimal purchaseReservedFrozenAccountAmount;

    public BigDecimal getDiscountFrozenAccountAmount() {
        return discountFrozenAccountAmount;
    }

    public void setDiscountFrozenAccountAmount(BigDecimal discountFrozenAccountAmount) {
        this.discountFrozenAccountAmount = discountFrozenAccountAmount;
    }

    public BigDecimal getPurchaseReservedFrozenAccountAmount() {
        return purchaseReservedFrozenAccountAmount;
    }

    public void setPurchaseReservedFrozenAccountAmount(BigDecimal purchaseReservedFrozenAccountAmount) {
        this.purchaseReservedFrozenAccountAmount = purchaseReservedFrozenAccountAmount;
    }

    public String getDiscountFrozenAccountAmountStr() {
        return NumberFormat.format(this.discountFrozenAccountAmount.doubleValue(), "#,##0.00");
    }

    public String getPurchaseReservedFrozenAccountAmountStr() {
        return NumberFormat.format(this.purchaseReservedFrozenAccountAmount.doubleValue(), "#,##0.00");
    }
}
