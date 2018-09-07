package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.model.DistributorCashAccount;

import java.util.Date;

/**
 * ad返利账户 service
 *
 * @author: 葛灿
 */
public interface DistributorCashAccountService {

    /**
     * 查询账户
     *
     * @param rpcHeader     头
     * @param currencyCode  货币编码
     * @param distributorId 经销商id
     * @param projectId     项目id‘
     * @return
     */
    DistributorCashAccount getDistributorCashAccount(GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long distributorId);


    /**
     * 修改供应商现金
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param distributorId   客户id
     * @param projectId       项目id
     * @param couponAmount    修改金额
     * @param orderNo         销售单号
     * @param transactionTime 交易时间
     * @param remark          备注
     * @return String 流水号
     */
    String updateDistributorCashAccount(GongxiaoRpc.RpcHeader rpcHeader,
                                        String currencyCode, long distributorId,
                                        long projectId, long couponAmount,
                                        String orderNo,
                                        Date transactionTime, String remark);

}
