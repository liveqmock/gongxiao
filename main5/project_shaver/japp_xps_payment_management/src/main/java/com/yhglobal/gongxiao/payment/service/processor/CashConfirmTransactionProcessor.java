package com.yhglobal.gongxiao.payment.service.processor;

import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.model.CashConfirm;

import java.util.Date;
import java.util.List;

/**
 * 现金确认事务类
 *
 * @author 葛灿
 */
public interface CashConfirmTransactionProcessor {

    /**
     * 现金确认事务
     *
     * @param rpcHeader       头
     * @param cashConfirmList 确认
     * @return
     */
    void cashConfirmTransaction(String prefix,
                                GongxiaoRpc.RpcHeader rpcHeader,
                                String bankName, String bankFlowNo,
                                Date recipientDate, List<CashConfirm> cashConfirmList,
                                String clientName, String remark) throws CloneNotSupportedException;
}
