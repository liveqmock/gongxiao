package com.yhglobal.gongxiao.grossProfit.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王帅
 */
public enum DifferenceAdjustPatternConstant {
    // 入损益 入预收

    GET_PROFIT_AND_LOSS((byte)1, "入损益"),

    GET_ADVANCE_RECEIPT((byte)2, "入预收"),

    ;

    byte code;

    String meaning;

    public byte getCode() {
        return code;
    }

    public String getMeaning() {
        return meaning;
    }

    DifferenceAdjustPatternConstant(byte code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }

    private volatile static Map<Byte, DifferenceAdjustPatternConstant> mapping = null;

    public static DifferenceAdjustPatternConstant getDifferenceAdjustPatternConstant(Byte code){
        if (mapping == null) {
            synchronized (DifferenceAdjustPatternConstant.class) {
                if (mapping == null) {
                    mapping = new HashMap<>();
                    for (DifferenceAdjustPatternConstant differenceAdjustPatternConstant : DifferenceAdjustPatternConstant.values()) {
                        mapping.put(differenceAdjustPatternConstant.code, differenceAdjustPatternConstant);
                    }
                }
            }
        }
        return mapping.get(code);
    }
}
