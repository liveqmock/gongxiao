package com.yhglobal.gongxiao.payment.dto;

import java.io.Serializable;

/**
 * 采购时的流水集合
 * <p>
 * 包含:
 * 1、过账ad返利流水
 * 2、过账ad代垫流水
 * 3、yh占用返利流水
 * 4、yh占用代垫流水
 *
 * @author: 葛灿
 */
public class PurchaseFlowCollections implements Serializable {
    /**
     * 过账ad返利流水
     */
    private String couponFlowNoToDistributor;
    /**
     * 过账ad代垫流水
     */
    private String prepaidFlowNoToDistributor;
    /**
     * yh占用返利流水
     */
    private String couponFlowNoToYhglobal;
    /**
     * yh占用代垫流水
     */
    private String prepaidFlowNoToYhglobal;

    public String getCouponFlowNoToDistributor() {
        return couponFlowNoToDistributor;
    }

    public void setCouponFlowNoToDistributor(String couponFlowNoToDistributor) {
        this.couponFlowNoToDistributor = couponFlowNoToDistributor;
    }

    public String getPrepaidFlowNoToDistributor() {
        return prepaidFlowNoToDistributor;
    }

    public void setPrepaidFlowNoToDistributor(String prepaidFlowNoToDistributor) {
        this.prepaidFlowNoToDistributor = prepaidFlowNoToDistributor;
    }

    public String getCouponFlowNoToYhglobal() {
        return couponFlowNoToYhglobal;
    }

    public void setCouponFlowNoToYhglobal(String couponFlowNoToYhglobal) {
        this.couponFlowNoToYhglobal = couponFlowNoToYhglobal;
    }

    public String getPrepaidFlowNoToYhglobal() {
        return prepaidFlowNoToYhglobal;
    }

    public void setPrepaidFlowNoToYhglobal(String prepaidFlowNoToYhglobal) {
        this.prepaidFlowNoToYhglobal = prepaidFlowNoToYhglobal;
    }
}
