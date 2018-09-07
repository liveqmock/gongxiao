package com.yhglobal.gongxiao.payment.flow.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.payment.flow.DistributorInoutFlow;

import java.util.Date;
import java.util.List;

/**
 * 经销商流水表Service
 *
 * @author: 葛灿
 */
public interface DistributorInoutFlowService {

    /**
     * 通过项目、分销商查找分销商流水信息
     *
     * @param rpcHeader     rpc调用的header
     * @param projectId     项目id
     * @param distributorId 分销商id
     * @return
     */
    PageInfo<DistributorInoutFlow> selectDistributorFlowListByProjectIdAndDistributorId(RpcHeader rpcHeader, long projectId, long distributorId, int pageNum, int pageSize);

    String insertCashFlow(RpcHeader rpcHeader, long distributorId, String distributorName,
                          long projectId, String projectName, long cashAmount,
                          Date transactionTime, String salesOrderNo);
}
