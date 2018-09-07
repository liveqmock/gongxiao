package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import java.util.Date;

/**
 * @author: 葛灿
 */
public interface DistributorPrepaidFlowService {


    /**
     * 插入代垫账户销售流水
     *
     * @param rpcHeader               头
     * @param projectId               项目id
     * @param projectName             项目名称
     * @param distributorId           经销商id
     * @param distributorName         经销商名称
     * @param currencyCode            货币编码
     * @param amountBeforeTransaction 交易前金额
     * @param transactionAmount       交易金额
     * @param transactionTime         交易时间
     * @param orderNo                 销售单号
     * @param sourceFlowNo     缓冲账户扣款的流水号
     * @param extroInfo               其他信息（JSON）
     * @return
     */
    String insertFlow(GongxiaoRpc.RpcHeader rpcHeader,
                      long projectId, String projectName,
                      long distributorId, String distributorName,
                      String currencyCode, long amountBeforeTransaction,
                      long transactionAmount, Date transactionTime,
                      String orderNo, String sourceFlowNo,
                      String extroInfo);
}
