package com.yhglobal.gongxiao.coupon.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅
 */
public enum InvoiceTypeConstant {
    // 发票类型  1 增值税普通发票" 2 "赠品费用"
    NORMAL_VAT_Invoice(1,"增值税普通发票"),
    GIFTS_COST(2,"赠品费用"),
    ;
    Integer code;
    String invoiceType;

    public Integer getCode() {
        return code;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    InvoiceTypeConstant(Integer code, String invoiceType) {
        this.code = code;
        this.invoiceType = invoiceType;
    }

    private volatile static Map<Integer, InvoiceTypeConstant> mapping = null;

    public static InvoiceTypeConstant getInvoiceTypeConstantByCode(Integer code){
        if (mapping == null) {
            synchronized (InvoiceTypeConstant.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (InvoiceTypeConstant invoiceTypeConstant : InvoiceTypeConstant.values()) {
                        mapping.put(invoiceTypeConstant.code, invoiceTypeConstant);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
