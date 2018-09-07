package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;

import java.util.Date;

/**
 * 越海代垫实收账户 service
 *
 * @author: 葛灿
 */
public interface YhglobalReceivedPrepaidAccountService {


    /**
     * 修改越海代垫实收账户（记录入账）
     *
     * @param rpcHeader     头
     * @param currencyCode  货币编码
     * @param projectId     项目id
     * @param prepaidAmount 修改金额
     * @param sourceFlowNo  来源流水号
     * @return
     */
    String updateYhglobalReceivedPrepaidAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long prepaidAmount, Date transactionTime, String orderNo, String sourceFlowNo, String remark);

    /**
     * 核销代垫
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
    void writeoffYhglobalPrepaidReceivedAccount(RpcHeader rpcHeader,
                                                String currencyCode, long supplierId,
                                                long projectId, long amount, Date transactionTime, String remark);

}
