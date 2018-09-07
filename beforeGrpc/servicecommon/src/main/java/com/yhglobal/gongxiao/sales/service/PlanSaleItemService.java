package com.yhglobal.gongxiao.sales.service;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.sales.model.plan.SalePlanItemInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SaleItemEditInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanItemBo;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售计划明细
 *
 * @author: 陈浩
 */
public interface PlanSaleItemService {
    /**
     * 插入销售计划列表
     *
     * @param rpcHeader       rpc调用的header
     * @param salePlanInfoIns 销售计划列表
     * @return
     */
    int createSalePlanItem(RpcHeader rpcHeader,
                           ArrayList<SalePlanItemInfo> salePlanInfoIns);

    /**
     * 获取预销客户列表
     *
     * @param rpcHeader
     * @param salesPlanNo  必填       预售单号
     * @param customerId   非必填      客户ID
     * @param customerName 非必填      客户名称
     * @return
     */
    PageInfo<SalePlanItemBo> selectSalePlanItemList(RpcHeader rpcHeader,
                                                    String salesPlanNo,
                                                    String customerId,
                                                    String customerName,
                                                    int pageNumber,
                                                    int pageSize);

    /**
     * 获取可编辑的信息
     *
     * @param rpcHeader rpc调用的header
     * @param itemId    预售明细ID
     * @return
     */
    SaleItemEditInfo getEditItemInfo(RpcHeader rpcHeader,
                                     String itemId);


    /**
     * 更新客户销售计划信息
     *
     * @param rpcHeader            rpc调用的header
     * @param itemId               客户计划货销售单号
     * @param adjustNumber         TODO:添加注释
     * @param brandPrepaidUnit     品牌商单位代垫
     * @param brandPrepaidDiscount 品牌商代垫折扣
     * @param yhPrepaidUnit        越海单位代垫
     * @param yhPrepaidDiscount    越海代垫折扣
     * @param startDate            开始日期
     * @param endDate              结束日期
     * @return
     */
    int editSaleItem(RpcHeader rpcHeader,
                     String itemId,
                     int adjustNumber,
                     double brandPrepaidUnit,
                     double brandPrepaidDiscount,
                     double yhPrepaidUnit,
                     double yhPrepaidDiscount,
                     Date startDate,
                     Date endDate);

    /**
     * 取消客户计划销售单
     *
     * @param rpcHeader rpc调用的header
     * @param itemId    被取消的计划销售单号
     * @return
     */
    int cancelPlanItem(RpcHeader rpcHeader,
                       String itemId);

    /**
     * 通过预售单取消预售详情
     *
     * @param rpcHeader
     * @param planSaleNo
     * @return
     */
    int cancelPlan(RpcHeader rpcHeader,
                   String planSaleNo);

    /**
     * 获取客户某个有效的货品信息
     *
     * @param rpcHeader     rpc调用的header
     * @param projectId     项目ID
     * @param distributorId 经销商ID
     * @param productId     货品ID
     * @param businessDate  有效日期
     * @return
     */
    SalesPlanItem getCustomerPlanInfo(RpcHeader rpcHeader,
                                      String projectId,
                                      String distributorId,
                                      String productId,
                                      Date businessDate);

    /**
     * 获取该货品分配给客户的所有记录数
     *
     * @param rpcHeader
     * @param projectId
     * @param distributorId
     * @param productId
     * @return
     */
    List<SalesPlanItem> getProductCustomer(RpcHeader rpcHeader,
                                           String projectId,
                                           String distributorId,
                                           String productId,
                                           Date startDate,
                                           Date endDate);


    /////////////////////////////////////////////////////////////////////////
    //👆 预售
    //
    //👇 第1,2是AD需要的 后三是销售需要的
    ////////////////////////////////////////////////////////////////////////

    /**
     * 获取客户有效的货品信息列表
     *
     * @param rpcHeader rpc调用的header
     * @param queryStr  查询
     * @return
     */
    List<SalesPlanItem> searchProductByCustomer(RpcHeader rpcHeader,
                                                String queryStr);

    /**
     * 更新销售占用信息
     *
     * @param rpcHeader
     * @param projectId              必填   项目ID
     * @param distributorId          必填   经销商ID
     * @param productId            必填   货品Id
     * @param businessDate           必填   业务发生时间
     * @param changeQuantity 必填  销售占用变更数量
     * @return
     */
    RpcResult updateSaleOccupation(RpcHeader rpcHeader,
                                   String projectId,
                                   String distributorId,
                                   String productId,
                                   Date businessDate,
                                   int changeQuantity);



    /**
     * 更新已售数量信息
     *
     * @param rpcHeader
     * @param projectId     必填  项目ID
     * @param distributorId 必填  经销商ID
     * @param productCode   必填  商品ID
     * @param businessDate  必填  业务发生时间
     * @param changeQuantity  必填  已售数量
     * @return
     */
    RpcResult updateSoldQuantity(RpcHeader rpcHeader,
                                 String projectId,
                                 String distributorId,
                                 String productCode,
                                 Date businessDate,
                                 int changeQuantity);

    /**
     * 获取有效客户列表
     *
     * @param rpcHeader
     * @param projectId     必填  项目ID
     * @param distributorId 必填  经销商ID
     * @param productCode   必填  货品ID
     * @param businessDate  必填  业务发生时间
     * @return
     */
    PageInfo<SalesPlanItem> selectSalePlanItemListConditions(RpcHeader rpcHeader,
                                                             String projectId,
                                                             String distributorId,
                                                             String productCode,
                                                             Date businessDate,
                                                             int pageNum,
                                                             int pageSize);

    /**
     * 通过itemid,获取可售信息
     *
     * @param rpcHeader
     * @param itemId
     * @return
     */
    SalesPlanItem getOnSaleQuantity(RpcHeader rpcHeader, String itemId);

    /**
     * 取消订单
     * @param rpcHeader
     * @param projectId 项目ID
     * @param distributorId 经销商id
     * @param productCode 货品CODE
     * @param businessDate 业务日期
     * @param saleOccupationQuantity 销售占用
     * @param changeQuantity  已售数量
     * @return
     */
    RpcResult cancelCustomerOrder(RpcHeader rpcHeader,
                                  String projectId,
                                  String distributorId,
                                  String productCode,
                                  Date businessDate,
                                  int saleOccupationQuantity,
                                  int changeQuantity);
}
