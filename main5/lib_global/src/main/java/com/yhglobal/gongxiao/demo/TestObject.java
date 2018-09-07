package com.yhglobal.gongxiao.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

/**
 * @author 葛灿
 */
public class TestObject {

    /**可以通过该注解指定json中,该字段的key值*/
    @JSONField(name = "testNumber")
    private BigDecimal number;

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public static void main(String[] args) {
        TestObject testObject = new TestObject();
        testObject.setNumber(new BigDecimal("1.123456789"));
        MyFilter myFilter = new MyFilter("#0.00");
        String json = JSON.toJSONString(testObject, myFilter);
        System.out.println(json);
    }
}
