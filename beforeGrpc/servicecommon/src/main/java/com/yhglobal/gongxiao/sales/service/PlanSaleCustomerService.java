package com.yhglobal.gongxiao.sales.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.sales.model.plan.vo.CustomerPlanInfo;
import com.yhglobal.gongxiao.sales.model.plan.vo.CustomerPlanItemInfo;

import java.util.Date;

/**
 * 客户销售计划
 */
public interface PlanSaleCustomerService {

    /**
     * 查询客户预售单计划列表
     * @param rpcHeader
     * @param distributorId 经销商ID
     * @param distributorName 经销商名称
     * @param pageNumber 页数
     * @param pageSize 单页数据量
     * @return
     */
    PageInfo<CustomerPlanInfo> selectCustomerPlanList(RpcHeader rpcHeader,
                                                      String projectId,
                                                      String distributorId,
                                                      String distributorName,
                                                      int pageNumber,
                                                      int pageSize);

    /**
     * 查询客户预售单明细列表
     * @param rpcHeader
     * @param distributorId 经销商ID
     * @param pageNumber 页数
     * @param pageSize 单页数据量
     * @return
     */
    PageInfo<CustomerPlanItemInfo> selectCustomerPlanItemList(RpcHeader rpcHeader,
                                                              String projectId,
                                                              String distributorId,
                                                              String salePlanNo,
                                                              String productCode,
                                                              String productName,
                                                              int orderStatus,
                                                              Date startDate,
                                                              Date endDate,
                                                              int pageNumber,
                                                              int pageSize);
}
