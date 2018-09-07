package com.yhglobal.gongxiao.payment.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import com.yhglobal.gongxiao.payment.model.CashLedger;

import java.util.Date;
import java.util.List;

/**
 * 收款台账表Service
 *
 * @author: 葛灿
 */
public interface CashLedgerService {
    /**
     * 选择性查询(均为可选条件)
     *
     * @param rpcHeader     头
     * @param projectId     项目id
     * @param bankName      银行名称
     * @param flowNo        流水号
     * @param confirmBegin  确认开始时间
     * @param confirmEnd    确认截止时间
     * @param receiveBegin  收款开始时间
     * @param receiveEnd    收款截止时间
     * @param approveStatus 审核状态
     * @param pageNum       页码
     * @param pageSize      条数
     * @return
     */
    PageInfo<CashLedger> selectCashLedgerList(RpcHeader rpcHeader, Long projectId, String bankName, String flowNo, Date confirmBegin, Date confirmEnd, Date receiveBegin, Date receiveEnd, Boolean approveStatus, int pageNum, int pageSize);

    /**
     * 取消收款确认
     *
     * @param rpcHeader  头
     * @param flowNo 流水号
     * @return
     */
    RpcResult cancelConfirm(RpcHeader rpcHeader, String flowNo)throws CloneNotSupportedException;

    /**
     * 审批通过
     *
     * @param rpcHeader  头
     * @param flowNo 流水号
     * @return
     */
    RpcResult approveLedger(RpcHeader rpcHeader, String flowNo);

    /**
     * 取消审批
     *
     * @param rpcHeader  头
     * @param flowNo 流水号
     * @return
     */
    RpcResult cancelApproveLedger(RpcHeader rpcHeader, String flowNo);

}
