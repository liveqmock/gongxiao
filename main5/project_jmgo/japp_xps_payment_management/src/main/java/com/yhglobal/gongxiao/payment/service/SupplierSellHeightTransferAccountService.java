package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.model.SupplierSellHeightAccount;

import java.util.Date;

/**
 * 低买高卖账户 service
 *
 * @author: 葛灿
 */
public interface SupplierSellHeightTransferAccountService {


    WriteOffReturnObject updateSupplierSellHeightTransferAccount(String prefix, GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long amount, String salesOrderNo, Date transactionTime);

    /**
     * 修改坚果项目下的差价账户
     *
     * @param prefix          表前缀
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param distributorId   经销商id
     * @param distributorName 经销商名称
     * @param amount          修改的金额
     * @param salesOrderNo    销售单号
     * @param transactionTime 交易时间
     * @param remark          备注
     * @return 返回错误码
     */
    WriteOffReturnObject updateSupplierSellHighAccountOnJmgo(String prefix,
                                                             GongxiaoRpc.RpcHeader rpcHeader,
                                                             String currencyCode,
                                                             long projectId,
                                                             long distributorId,
                                                             String distributorName,
                                                             long amount,
                                                             String salesOrderNo,
                                                             Date transactionTime,
                                                             String remark);
}
