package com.yhglobal.gongxiao.util;

import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;

public class GongxiaoResultUtil {

    /**
     * 根据传入的ErrorCode 创建对应的GongxiaoResult并返回
     */
    public static <T> GongxiaoResult<T> createGongxiaoErrorResult(ErrorCode errorCode) {
        GongxiaoResult<T> result = new GongxiaoResult<>();
        result.setReturnCode(errorCode.getErrorCode());
        result.setMessage(errorCode.getMessage());
        return result;
    }

    /**
     * 根据传入的错误码的code数值 创建对应的GongxiaoResult并返回
     */
    public static <T> GongxiaoResult<T> createGongxiaoErrorResultByCode(Integer code) {
        GongxiaoResult<T> result = new GongxiaoResult<>();
        ErrorCode errorCode = ErrorCode.getErrorCodeByCode(code);
        result.setReturnCode(errorCode.getErrorCode());
        result.setMessage(errorCode.getMessage());
        return result;
    }

}
