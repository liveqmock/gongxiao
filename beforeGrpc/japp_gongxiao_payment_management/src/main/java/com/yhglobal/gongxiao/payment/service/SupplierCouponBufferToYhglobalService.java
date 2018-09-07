package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.model.SupplierCouponBufferToYhglobal;

import java.util.Date;

/**
 * 供应商返利过账YH账户  service
 *
 * @author: 葛灿
 */
public interface SupplierCouponBufferToYhglobalService {


    /**
     * 修改供应商返利过账YH账户（插入流水）
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param couponAmount    修改金额
     * @param purchaseOrderNo 采购单号
     * @param transactionTime 交易时间
     * @param remark          备注
     * @param sourceFlowNo    来源流水号
     * @return String 流水号
     */
    String updateCouponBufferToYhglobal(RpcHeader rpcHeader, String currencyCode, long projectId, long couponAmount,
                                        String purchaseOrderNo, Date transactionTime, String remark, String sourceFlowNo);


}
