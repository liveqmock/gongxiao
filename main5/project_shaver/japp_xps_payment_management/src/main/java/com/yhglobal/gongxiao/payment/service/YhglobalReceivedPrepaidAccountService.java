package com.yhglobal.gongxiao.payment.service;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.project.utils.RpcResult;

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
     * @return RpcResult<流水号> </>
     */
    WriteOffReturnObject updateYhglobalReceivedPrepaidAccount(String prefix,GongxiaoRpc.RpcHeader rpcHeader, String currencyCode, long projectId, long prepaidAmount, Date transactionTime, String orderNo, String sourceFlowNo);


}
