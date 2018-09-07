package com.yhglobal.gongxiao.model;

import java.io.Serializable;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 20:59 2018/5/22
 */
public class RpcResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public RpcResult() {
        this(RpcResult.RETURN_SUCCESS, "", null);
    }

    public RpcResult(int code) {
        this(code, "", null);
    }

    public RpcResult(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public RpcResult(int code, T result) {
        this(code, "", result);
    }


    public RpcResult(int code, String message, T result) {
        this.code = code;
        this.success = RpcResult.RETURN_SUCCESS == code;
        this.message = message;
        this.result = result;
    }

    public RpcResult(boolean isSuccess, String message, T result) {
        this((isSuccess ? RpcResult.RETURN_SUCCESS : RpcResult.RETURN_FAILURE), message, result);
    }

    /**
     * 新建结果对象
     *
     * @param isSuccess
     * @return
     */
    public static <T> RpcResult<T> newResult(boolean isSuccess) {
        return new RpcResult<T>(isSuccess, "", null);
    }

    /**
     * 新建结果对象
     *
     * @param code
     * @param result
     * @return
     */
    public static <T> RpcResult<T> newResult(int code, T result) {
        return new RpcResult<T>(code, result);
    }

    /**
     * 新建结果对象
     *
     * @param code
     * @return
     */
    public static <T> RpcResult<T> newResult(int code) {
        return new RpcResult<T>(code, "");
    }

    /**
     * 新建结果对象
     *
     * @param code
     * @param message
     * @param result
     * @return
     */
    public static <T> RpcResult<T> newResult(int code, String message, T result) {
        return new RpcResult<T>(code, message, result);
    }

    /**
     * 新建结果对象
     *
     * @param isSuccess
     * @param message
     * @param result
     * @return
     */
    public static <T> RpcResult<T> newResult(boolean isSuccess, String message, T result) {
        return new RpcResult<T>(isSuccess, message, result);
    }

    /**
     * 新建成功结果对象
     *
     * @return
     */
    public static <T> RpcResult<T> newSuccessResult() {
        return newResult(true, "", null);
    }

    /**
     * 新建成功结果对象
     *
     * @param result
     * @return
     */
    public static <T> RpcResult<T> newSuccessResult(T result) {
        return newResult(true, "", result);
    }

    /**
     * 新建失败结果对象
     *
     * @return
     */
    public static <T> RpcResult<T> newFailureResult() {
        return new RpcResult<T>(RpcResult.RETURN_FAILURE, RETURN_FAILURE_MESSAGE, null);
    }

    /**
     * 新建失败结果对象
     *
     * @param code
     * @return
     */
    public static <T> RpcResult<T> newFailureResult(int code) {
        return new RpcResult<T>(code, null, null);
    }

    /**
     * 新建失败结果对象
     *
     * @param code
     * @return
     */
    public static <T> RpcResult<T> newFailureResult(int code, String message) {
        return new RpcResult<T>(code, message, null);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
