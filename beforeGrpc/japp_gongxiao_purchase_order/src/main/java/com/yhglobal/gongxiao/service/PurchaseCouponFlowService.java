package com.yhglobal.gongxiao.service;

import com.yhglobal.gongxiao.model.PurchaseCouponFlow;

import java.util.List;

/**
 * 采购返利流水
 *
 * @author: 陈浩
 **/
public interface PurchaseCouponFlowService {
    /**
     * 插入采购返利流水
     * @param flow
     * @return
     */
    int insertCouponFlow(PurchaseCouponFlow flow);

    List<PurchaseCouponFlow> selectCouponFlowByProject(String projectId);
}
