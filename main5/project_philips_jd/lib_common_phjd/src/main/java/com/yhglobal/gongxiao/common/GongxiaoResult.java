package com.yhglobal.gongxiao.common;

import java.io.Serializable;

/**
 * 供销平台通用返回模型
 *
 * @Author: 葛灿
 */
public class GongxiaoResult<T> implements Serializable {

    /**
     * 返回码 0为成功 非0表示失败 失败时要添加错误信息
     */
    private int returnCode;

    /**
     * 返回给页面的结果对象
     */
    private T data;

    /**
     * 错误信息
     */
    private String message;


    public GongxiaoResult(){
    }

    public GongxiaoResult(int returnCode, T data) {  // 成功的场景 用这个构造函数
        this.returnCode = returnCode;
        this.data = data;
    }
    public GongxiaoResult(int returnCode , String message, T data) {  // 成功的场景 用这个构造函数
        this.returnCode = returnCode;
        this.message = message;
        this.data = data;
    }
    public GongxiaoResult(int returnCode, String message) {  // 出错的场景 用这个构造函数
        this.returnCode = returnCode;
        this.message = message;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public static com.yhglobal.gongxiao.common.GongxiaoResult newResult(int returnCode, String message){
        return new com.yhglobal.gongxiao.common.GongxiaoResult(returnCode,message);
    }

}
