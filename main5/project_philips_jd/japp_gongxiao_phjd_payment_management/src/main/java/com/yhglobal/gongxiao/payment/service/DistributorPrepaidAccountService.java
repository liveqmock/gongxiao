package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import java.util.Date;

/**
 * AD代垫账户 service
 *
 * @author: 葛灿
 */
public interface DistributorPrepaidAccountService {


    /**
     * 修改ad账户金额
     *
     * @param rpcHeader           头
     * @param currencyCode        货币编码
     * @param distributorId       客户id
     * @param projectId           项目id
     * @param couponAmount        修改金额
     * @param transactionTime     交易时间
     * @param sourceFlowNo 过账账户流水号
     * @param remark              备注
     * @return String 流水号
     */
    String updateDistributorPrepaidAccount(GongxiaoRpc.RpcHeader rpcHeader,
                                           String currencyCode, long distributorId,
                                           long projectId, long couponAmount, String orderNo,
                                           Date transactionTime, String remark,
                                           String sourceFlowNo);

}
