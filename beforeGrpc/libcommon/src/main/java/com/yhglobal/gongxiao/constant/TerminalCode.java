package com.yhglobal.gongxiao.constant;

/**
 * 用户终端的类型
 */
public enum TerminalCode {

    ANDROID(1),
    iOS(2),
    WEB(9);

    TerminalCode(int code) {
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }
}
