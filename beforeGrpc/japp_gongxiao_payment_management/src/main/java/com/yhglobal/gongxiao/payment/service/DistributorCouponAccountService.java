package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;

import java.util.Date;

/**
 * ad返利账户 service
 *
 * @author: 葛灿
 */
public interface DistributorCouponAccountService {

    /**
     * 过账增加ad返利
     *
     * @param rpcHeader           头
     * @param currencyCode        货币编码
     * @param distributorId       客户id
     * @param projectId           项目id
     * @param couponAmount        修改金额
     * @param transactionTime     交易时间
     * @param remark              备注
     * @param sourceFlowNo 过账账户流水号
     * @return String 流水号
     */
    String updateDistributorCouponAccount(RpcHeader rpcHeader,
                                          String currencyCode, long distributorId,
                                          long projectId, long couponAmount,String orderNo,
                                          Date transactionTime, String remark,
                                          String sourceFlowNo);


}
