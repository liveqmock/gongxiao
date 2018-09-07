package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.common.RpcHeader;

import java.util.Date;

/**
 * @author: 葛灿
 */
public interface SupplierPrepaidTransferRecordService {

    /**
     * 插入 供应商代垫过账记录
     *
     * @param rpcHeader            头
     * @param supplierId           供应商id
     * @param supplierName         供应商名称
     * @param projectId            项目id
     * @param projectName          项目名称
     * @param currencyCode         货币编码
     * @param amountBeforeTransfer 过账前的返利余额
     * @param transferAmount       过账金额
     * @param transferTime         过账时间
     * @param purchaseOrderNo      采购单号
     * @param transferAccountType  过账类型
     * @return
     */
    String insertRecord(RpcHeader rpcHeader,
                      Long supplierId, String supplierName,
                      long projectId, String projectName,
                      String currencyCode, long amountBeforeTransfer,
                      long transferAmount, Date transferTime,
                      String purchaseOrderNo, String transferAccountType,String remark);
}
