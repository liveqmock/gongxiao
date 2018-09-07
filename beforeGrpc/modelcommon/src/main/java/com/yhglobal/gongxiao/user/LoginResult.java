package com.yhglobal.gongxiao.user;

import java.io.Serializable;

/**
 * 登录结果模型
 *
 * @Author: 葛灿
 * @Date:2018/2/7--18:32
 */
public class LoginResult implements Serializable{
    /**
     * 是否登录成功,200为登录成功
     */
    private int codeNumber;
    /**
     * 错误信息
     */
    private String errorMessage;

    public int getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
