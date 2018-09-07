package com.yhglobal.gongxiao.foudation.model;

/**
 * 过滤列信息
 *
 * @author: 陈浩
 **/
public class Filter {
    /**
     * 列名
     */
    private String fieldName;
    /**
     * 列值
     */
    private String fieldValue;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
