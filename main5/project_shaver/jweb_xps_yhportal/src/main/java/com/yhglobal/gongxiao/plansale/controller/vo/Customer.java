package com.yhglobal.gongxiao.plansale.controller.vo;

import java.io.Serializable;

/**
 * 客户信息
 *
 * @author: 陈浩
 * @create: 2018-03-09 16:43
 **/
public class Customer implements Serializable{
    /**
     * 客户ID
     */
    private String customerId;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户账号
     */
    private String customerAccount;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount;
    }
}
