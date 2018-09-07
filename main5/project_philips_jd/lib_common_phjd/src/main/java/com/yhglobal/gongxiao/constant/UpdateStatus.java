package com.yhglobal.gongxiao.constant;

/**
 * 更新状态枚举
 *
 * @author weizecheng
 * @date 2018/8/28 14:51
 */
public enum  UpdateStatus {
    /**
     * 更新成功
     */
    UPDATE_SUCCESS(1, "更新成功"),

    /**
     * 更新失败
     */
    UPDATE_FAIL(0, "更新失败");

    private Integer status;

    private String message;

    UpdateStatus(Integer code, String message) {
        this.status = code;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
