package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.model.SupplierPrepaidAccount;

import java.util.Date;

/**
 * 供应商代垫上账账户 service
 *
 * @author: 葛灿
 */
public interface SupplierPrepaidAccountService {



    /**
     * 返利修改
     *
     * @param rpcHeader       头
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param amount    修改金额
     * @param transactionTime 交易时间
     * @param purchaseOrderNo 采购单号 上账时为null
     * @param remark          备注 过账时为null
     * @param transferType    过账类型 上账时为null
     * @return
     */
    String updateSupplierPrepaidAccount(RpcHeader rpcHeader, String currencyCode, long projectId, long amount,
                                       Date transactionTime, String remark, String purchaseOrderNo, String transferType);


}
