package com.yhglobal.gongxiao.purchase.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 计划收货单的订单号
 *
 * @author: 陈浩
 * @create: 2018-03-01 12:23
 **/
public class PlanInstockOrder implements Serializable {
    /**
     * 计划入库单的订单号
     */
    private String planInstockOrderNo;

    /**
     * 货品单号
     */
    private List<PlanInstockItemOrder> planInstockItemOrder;

    public String getPlanInstockOrderNo() {
        return planInstockOrderNo;
    }

    public void setPlanInstockOrderNo(String planInstockOrderNo) {
        this.planInstockOrderNo = planInstockOrderNo;
    }

    public List<PlanInstockItemOrder> getPlanInstockItemOrder() {
        return planInstockItemOrder;
    }

    public void setPlanInstockItemOrder(List<PlanInstockItemOrder> planInstockItemOrder) {
        this.planInstockItemOrder = planInstockItemOrder;
    }
}
