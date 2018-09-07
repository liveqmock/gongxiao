package com.yhglobal.gongxiao.payment.flow.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.model.DistributorCouponFlow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DistributorCouponTransferService {


    /**
     * 单个数据接口
     * */
    RpcResult singleDistributorCouponTransferReceived(RpcHeader rpcHeader,long distributorId, long receivedAmount, String currencyCode,
                                                      long supplierId, String supplierName, long projectId, String distributorName, String remark);

     PageInfo<FrontPageFlow> selectCouponFlow(RpcHeader rpcHeader,String currencyCode, long distributorId,
                                              long projectId,
                                              Integer moneyFlow,
                                              Date beginDate, Date endDate,
                                              int pageNum,
                                              int pageSize);
    List<FrontPageFlow> selectCouponFlow(RpcHeader rpcHeader,String currencyCode, long distributorId,
                                             long projectId,
                                             Integer moneyFlow,
                                             Date beginDate, Date endDate);

    FrontPageFlowSubtotal getSubtotal( RpcHeader rpcHeader,String currencyCode,
                                      long distributorId,
                                      long projectId,
                                      int accountType,
                                      Integer moneyFlow,
                                       Date beginDate,Date endDate);


}
