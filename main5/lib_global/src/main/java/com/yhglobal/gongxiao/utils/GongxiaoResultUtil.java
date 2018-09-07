package com.yhglobal.gongxiao.utils;


import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;

public class GongxiaoResultUtil {

    /**
     * 根据传入的ErrorCode 创建对应的GongxiaoResult并返回
     */
    public static  GongxiaoResult createGongxiaoErrorResult(ErrorCode errorCode) {
        GongxiaoResult result = new GongxiaoResult();
        result.setReturnCode(errorCode.getErrorCode());
        result.setMessage(errorCode.getMessage());
        return result;
    }

    /**
     * 根据传入的错误码的code数值 创建对应的GongxiaoResult并返回
     */
    public static  GongxiaoResult createGongxiaoErrorResultByCode(Integer code) {
        GongxiaoResult result = new GongxiaoResult();
        ErrorCode errorCode = ErrorCode.getErrorCodeByCode(code);
        result.setReturnCode(errorCode.getErrorCode());
        result.setMessage(errorCode.getMessage());
        return result;
    }

}
