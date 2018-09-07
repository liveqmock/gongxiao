package com.yhglobal.gongxiao.sales.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.sales.model.plan.dto.SaleEditInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanInfoIn;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanInfoOut;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售计划
 *
 * @author: 陈浩
 * @create: 2018-03-08 19:56
 **/
public interface PlanSaleService {
    /**
     * 插入货品销售计划列表
     *
     * @param rpcHeader rpc调用的header
     * @param salePlanInfoOuts 销售计划列表
     * @return
     */
    int createSalePlan(RpcHeader rpcHeader, ArrayList<SalePlanInfoIn> salePlanInfoOuts);

    /**
     * 获取当前项目的销售计划列表
     * @param rpcHeader     必填
     * @param projectId     必填 项目ID
     * @param productCode   选填  货品编码
     * @param productName   选填  货品名称
     * @param createTime    选填  创建时间
     * @param pageNumber    选填  页号
     * @param pageSize      选填  页数
     * @return
     */
    PageInfo<SalePlanInfoOut> selectSalePlanListByProject(RpcHeader rpcHeader,
                                                          String projectId,
                                                          String productCode,
                                                          String productName,
                                                          String createTime,
                                                          int pageNumber,
                                                          int pageSize);

    /**
     *
     * 变更销售计划单可售数量
     *
     * @param rpcHeader 必填  rpc调用的header
     * @param salesPlanNo 必填    销售计划单号
     * @param onSaleQuantity  必填    可售总数
     * @param allocatedQuantity 必填  已分配数量
     * @param unallocatedQuantity 必填    未分配额数量
     * @return
     */
    int updatePlanQuantity(RpcHeader rpcHeader,
                           String salesPlanNo,
                           int onSaleQuantity,
                           int allocatedQuantity,
                           int unallocatedQuantity);

    /**
     * 通过销售计划单号获取预约销售单未处理的数量
     *
     * @param rpcHeader     必填   rpc调用的header
     * @param salesPlanNo   必填  销售单号
     * @return
     */
    int getUnHandleQuantity(RpcHeader rpcHeader,
                            String  salesPlanNo);

    /**
     * 通过销售计划单号获取销售计划信息
     *
     * @param rpcHeader rpc调用的header
     * @param salesPlanNo 必填    销售单号
     * @return
     */
    SaleEditInfo getSalePlanInfo(RpcHeader rpcHeader, String salesPlanNo);


    /**
     * 变更销售计划信息,主要市变更可售数量
     *
     * @param rpcHeader      必填
     * @param salesPlanNo    必填  销售计划单号
     * @param onSaleQuantity 必填  可售数量
     * @param startDate      必填  有效期起
     * @param endDate        必填  有效期止
     * @return
     */
    GongxiaoResult editSalePlan(RpcHeader rpcHeader,
                                String salesPlanNo,
                                int onSaleQuantity,
                                String startDate,
                                String endDate);

    /**
     *
     * 获取销售计划单详情
     * @param rpcHeader
     * @param salesPlanNo   销售计划单号
     * @return
     */
    SalePlanInfoOut getSalePlanDetail(RpcHeader rpcHeader,
                                      String salesPlanNo);

    /**
     * 判定货品是否重复
     * @param rpcHeader
     * @param projectId 项目ID
     * @param productId 项目名称
     * @return
     */
    boolean isProductRepeat(RpcHeader rpcHeader,
                            String projectId,
                            String productId,
                            Date startTime,
                            Date endTime);

    /**
     * 作废预售单
     * @param rpcHeader
     * @param salesPlanNo 预售单号
     * @return
     */
    int cancelPlanSaleOrder(RpcHeader rpcHeader,
                            String salesPlanNo);

    /**
     * 自动或者手动失效,返还订单的可售数量
     * @param rpcHeader
     * @param changeQuantity 返还数量
     * @return
     */
    int updateReturnSaleQuantity(RpcHeader rpcHeader,
                                 String salePlanNo,
                                 int changeQuantity);

}
