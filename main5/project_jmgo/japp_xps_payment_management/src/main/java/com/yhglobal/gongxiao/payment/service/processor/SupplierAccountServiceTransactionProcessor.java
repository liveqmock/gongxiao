package com.yhglobal.gongxiao.payment.service.processor;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.payment.dto.PurchaseFlowCollections;

import java.util.Date;

/**
 * 供应商账户实物类
 *
 * @author: 葛灿
 */
public interface SupplierAccountServiceTransactionProcessor {


    /**
     * 上账账户暂扣返利、代垫
     * 上账账户转入ad过账账户
     * <p>
     * 采购模块调用
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param couponToYh      yh采购使用的返利金额，账户中暂扣
     * @param couponToAd      转到ad缓冲的返利
     * @param prepaidToYh     yh采购使用的代垫金额，账户中暂扣
     * @param prepaidToAd     转到ad缓冲的代垫
     * @param purchaseOrderNo 采购单号
     * @param transactionTime 交易时间
     * @return PurchaseFlowCollections
     */
    RpcResult<PurchaseFlowCollections> payForPurchase(String prefix,
                                                      GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId,
                                                      long couponToYh, long couponToAd,
                                                      long prepaidToYh, long prepaidToAd,
                                                      String purchaseOrderNo, Date transactionTime);
}
