package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import java.util.Date;

/**
 * 低买高卖账户 service
 *
 * @author: 葛灿
 */
public interface SupplierSellHeightTransferAccountService {


    /**
     * 查询低买高卖账户账户
     *
     * @param rpcHeader    头
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    //SupplierSellHeightAccount getSupplierPrepaidBufferToYhglobal(GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId);


    /**
     * 使用低买高卖做核销
     * <p>
     * 支付模块调用
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param amount          核销金额
     * @param transactionTime 创建时间
     * @return
     */
    String writeoffSellHeightAmount(GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long amount, Date transactionTime);

    String updateSupplierSellHeightTransferAccount(GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long amount, String salesOrderNo, Date transactionTime);
}
