package com.yhglobal.gongxiao.coupon.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅
 */
public enum TaxRateSsubsidyConstant {
    // 税差是否补贴 1 是 2 不是
    YES(1,"是"),
    NO(2,"否"),
    ;
    Integer code;
    String yesOrNo;

    public Integer getCode() {
        return code;
    }

    public String getYesOrNo() {
        return yesOrNo;
    }

    TaxRateSsubsidyConstant(Integer code, String yesOrNo) {
        this.code = code;
        this.yesOrNo = yesOrNo;
    }

    private volatile static Map<Integer, TaxRateSsubsidyConstant> mapping = null;

    public static TaxRateSsubsidyConstant getTaxRateSsubsidyConstantByCode(Integer code){
        if (mapping == null) {
            synchronized (TaxRateSsubsidyConstant.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (TaxRateSsubsidyConstant taxRateSsubsidyConstant : TaxRateSsubsidyConstant.values()) {
                        mapping.put(taxRateSsubsidyConstant.code, taxRateSsubsidyConstant);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
