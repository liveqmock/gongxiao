package com.yhglobal.gongxiao.common;

import java.io.Serializable;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 18:15 2018/6/27
 */
public class WebResult<T>  implements Serializable {

    public static int RETURN_SUCCESS = 0;

    public static int RETURN_FAILURE = 1;

    public static String RETURN_FAILURE_MESSAGE = "未知系统异常";
    /**
     * 编码
     */
    private int code;

    /**
     * 是否操作成功
     */
    private boolean success;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回结果
     */
    private T result;

    public WebResult() {
        this(WebResult.RETURN_SUCCESS, null, null);
    }

    public WebResult(int code) {
        this(code, null, null);
    }

    public WebResult(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public WebResult(int code, T result) {
        this(code, null, result);
    }


    public WebResult(int code, String message, T result) {
        this.code = code;
        this.success = WebResult.RETURN_SUCCESS == code;
        this.message = message;
        this.result = result;
    }

    public WebResult(boolean isSuccess, String message, T result) {
        this((isSuccess ? WebResult.RETURN_SUCCESS : WebResult.RETURN_FAILURE), message, result);
    }

    /**
     * 新建结果对象
     *
     * @param isSuccess
     * @return
     */
    public static <T> WebResult<T> newResult(boolean isSuccess) {
        return new WebResult<T>(isSuccess, null, null);
    }

    /**
     * 新建结果对象
     *
     * @param code
     * @param result
     * @return
     */
    public static <T> WebResult<T> newResult(int code, T result) {
        return new WebResult<T>(code, result);
    }

    /**
     * 新建结果对象
     *
     * @param code
     * @return
     */
    public static <T> WebResult<T> newResult(int code) {
        return new WebResult<T>(code, "");
    }

    /**
     * 新建结果对象
     *
     * @param code
     * @param message
     * @param result
     * @return
     */
    public static <T> WebResult<T> newResult(int code, String message, T result) {
        return new WebResult<T>(code, message, result);
    }

    /**
     * 新建结果对象
     *
     * @param isSuccess
     * @param message
     * @param result
     * @return
     */
    public static <T> WebResult<T> newResult(boolean isSuccess, String message, T result) {
        return new WebResult<T>(isSuccess, message, result);
    }

    /**
     * 新建成功结果对象
     *
     * @return
     */
    public static <T> WebResult<T> newSuccessResult() {
        return newResult(true, null, null);
    }

    /**
     * 新建成功结果对象
     *
     * @param result
     * @return
     */
    public static <T> WebResult<T> newSuccessResult(T result) {
        return newResult(true, null, result);
    }

    /**
     * 新建失败结果对象
     *
     * @return
     */
    public static <T> WebResult<T> newFailureResult() {
        return new WebResult<T>(WebResult.RETURN_FAILURE, RETURN_FAILURE_MESSAGE, null);
    }

    /**
     * 新建失败结果对象
     *
     * @param code
     * @return
     */
    public static <T> WebResult<T> newFailureResult(int code) {
        return new WebResult<T>(code, null, null);
    }
    /**
     * 新建失败结果对象
     *
     * @param message
     * @return
     */
    public static <T> WebResult<T> newFailureResult(String message) {
        return new WebResult<T>(WebResult.RETURN_FAILURE, message, null);
    }
    /**
     * 新建失败结果对象
     *
     * @param code
     * @return
     */
    public static <T> WebResult<T> newFailureResult(int code, String message) {
        return new WebResult<T>(code, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
