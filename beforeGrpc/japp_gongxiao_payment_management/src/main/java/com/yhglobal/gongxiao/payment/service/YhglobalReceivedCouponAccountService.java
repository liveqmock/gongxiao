package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;

import java.util.Date;

/**
 * 越海返利实收账户 service
 *
 * @author: 葛灿
 */
public interface YhglobalReceivedCouponAccountService {


    /**
     * 修改越海返利实收账户
     * 入账插入记录
     * 出账由调用发插入记录、流水
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId
     * @param couponAmount 修改金额
     * @param sourceFlowNo 来源流水号
     * @return
     */
    String updateYhglobalReceivedCouponAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long couponAmount, Date transactionTime, String orderNo, String sourceFlowNo, String remark);


    /**
     * 核销返利
     * <p>
     * 越海返利实收转出
     * 流水由调用方插入
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param supplierId      供应商id
     * @param projectId       项目id
     * @param amount          核销金额
     * @param transactionTime 核销时间
     * @return
     */
    void writeoffYhglobalCouponReceivedAccount(RpcHeader rpcHeader,
                                               String currencyCode, long supplierId,
                                               long projectId, long amount, Date transactionTime, String remark);

}
