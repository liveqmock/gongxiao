package com.yhglobal.gongxiao.constant.purchase;

import com.yhglobal.gongxiao.constant.ErrorCode;

import java.util.HashMap;
import java.util.Map;

/**
 *  [采购单管理] > [新增采购单]   新增采购单时定义是否开票
 *
 * @author: 陈浩
 **/
public enum SupplierReceiptStatus {
    //采购
    NOT_RECEIPT((byte)1,"不开发票"),

    GIVE_RECEIPT((byte)2,"开发票"),

    ;
    private byte status;

    private String message;

    private volatile static Map<Byte, SupplierReceiptStatus> mapping = null;


    SupplierReceiptStatus(byte status, String message) {
        this.status = status;
        this.message = message;
    }

    public static SupplierReceiptStatus getStatusByCode(int code) {
        if (mapping == null) {
            synchronized (ErrorCode.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (SupplierReceiptStatus supplierReceiptStatus : SupplierReceiptStatus.values()) {
                        mapping.put(supplierReceiptStatus.status, supplierReceiptStatus);
                    }
                }
            }
        }
        return mapping.get(code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
