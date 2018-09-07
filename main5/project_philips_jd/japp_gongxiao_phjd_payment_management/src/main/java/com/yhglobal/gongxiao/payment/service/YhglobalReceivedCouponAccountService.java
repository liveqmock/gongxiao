package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.project.utils.RpcResult;

import java.math.BigDecimal;
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
     * @param projectId 项目id
     * @param couponAmount 修改金额
     * @param sourceFlowNo 来源流水号
     * @return WriteOffReturnObject
     */
    WriteOffReturnObject updateYhglobalReceivedCouponAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode,
                                                             long projectId, BigDecimal couponAmount, Date transactionTime,
                                                             String orderNo, String sourceFlowNo);




}
