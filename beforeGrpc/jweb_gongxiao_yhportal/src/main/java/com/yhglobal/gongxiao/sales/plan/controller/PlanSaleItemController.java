package com.yhglobal.gongxiao.sales.plan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.foundation.customer.Customer;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorPortalUserService;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.sales.model.plan.SalePlanItemInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SaleItemEditInfo;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanInfoOut;
import com.yhglobal.gongxiao.sales.model.plan.dto.SalePlanItemBo;
import com.yhglobal.gongxiao.sales.model.plan.model.SalesPlanItem;
import com.yhglobal.gongxiao.sales.service.PlanSaleItemService;
import com.yhglobal.gongxiao.sales.service.PlanSaleService;
import com.yhglobal.gongxiao.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 销售计划明细
 *
 * @author: 陈浩
 * @create: 2018-03-09 11:25
 **/
@Controller
@RequestMapping("/planSaleItem")
public class PlanSaleItemController {
    private static Logger logger = LoggerFactory.getLogger(PlanSaleItemController.class);

    @Reference
    PlanSaleService planSaleService;

    @Reference
    PlanSaleItemService planSaleItemService;

    @Reference
    DistributorPortalUserService distributorPortalUserService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    /**
     * 获取该项目客户列表
     *
     * @param projectId    项目ID
     * @param customerId   客户ID
     * @param customerName 客户名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/getCustomerList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getCustomerList(String projectId,
                                          String customerId,
                                          String customerName,
                                          int pageNumber,
                                          int pageSize,
                                          HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        logger.info("#traceId={}# [IN][getCustomerList] params: projectId:{}, customerId:{}, customerName:{}  ", traceId, projectId, customerId, customerName);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        PageInfo<DistributorPortalUser> distributorPortalUserPageInfo = distributorPortalUserService.selectDistributorByProject(rpcHeader,
                projectId,
                customerId,
                customerName,
                pageNumber,
                pageSize);

        List<Customer> customerList = new ArrayList<>();
        for (DistributorPortalUser user : distributorPortalUserPageInfo.getList()) {
            Customer customer = new Customer();
            customer.setCustomerId(user.getDistributorId() + "");
            customer.setCustomerName(user.getDistributorName());
            customerList.add(customer);
        }
        PageInfo<Customer> customerPageInfo =  new PageInfo<>(customerList);
        customerPageInfo.setTotal(distributorPortalUserPageInfo.getTotal());

        logger.info("#traceId={}# [OUT] getCustomerList success: ", traceId);
        return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), customerPageInfo);
    }

    /**
     * 通过预销单号,客户ID,客户名称获取预销列表
     *
     * @param salesPlanNo  销售计划单号
     * @param customerId   客户ID
     * @param customerName 客户名称
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPlanSaleItemList", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getPlanSaleItemList(String salesPlanNo,
                                              String customerId,
                                              String customerName,
                                              int pageNumber,
                                              int pageSize,
                                              HttpServletRequest request) throws Exception {
        String traceId = null;
        YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        logger.info("#traceId={}# [IN][getPlanSaleItemList] params: salesPlanNo:{}, customerId:{}, customerName:{}  ", salesPlanNo, customerId, customerName);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        if ("".equals(salesPlanNo)) {
            logger.error("#traceId={}# [OUT] fail to getPlanSaleItemList:", traceId);
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            PageInfo<SalePlanItemBo> salePlanItemList = planSaleItemService.selectSalePlanItemList(rpcHeader,
                    salesPlanNo,
                    customerId,
                    customerName,
                    pageNumber,
                    pageSize);
            logger.info("#traceId={}# [OUT] getPlanSaleItemList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), salePlanItemList);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     * 获取某一销售计划单的剩余可售总数
     *
     * @param salesPlanNo 销售计划单号
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOnSaleQuantity", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getOnSaleQuantity(String salesPlanNo, HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][getOnSaleQuantity] params: salesPlanNo:{}   ", salesPlanNo);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            int couldAllocatedTotalQuantity = planSaleService.getUnHandleQuantity(rpcHeader, salesPlanNo);
            logger.info("#traceId={}# [OUT] getOnSaleQuantity success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), couldAllocatedTotalQuantity);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }


    /**
     * 新增计划销售明细信息
     *
     * @param salePlanItemJson 客户可售计划json
     * @param request
     * @return
     */
    @RequestMapping(value = "/putCustomerSalePlan", method = RequestMethod.POST)
    @ResponseBody
    public GongxiaoResult putCustomerSalePlan(String salePlanItemJson, HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][putCustomerSalePlan] params: #salePlanItemJson:{}   ", salePlanItemJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            //将货品列表json数组的字符串转换为List
            ArrayList<SalePlanItemInfo> purchaseItemInfoList
                    = JSON.parseObject(salePlanItemJson, new TypeReference<ArrayList<SalePlanItemInfo>>() {
            });
            //数据校验 start
            SalePlanInfoOut salePlanDetail = planSaleService.getSalePlanDetail(rpcHeader, purchaseItemInfoList.get(0).getSalesPlanNo());
            String orderStartTime = salePlanDetail.getStartTime();
            String orderEndTime = salePlanDetail.getEndTime();
            Date orderStartDate = DateUtil.stringToDate(orderStartTime, "yyyy-MM-dd");
            Date orderEndDate = DateUtil.stringToDate(orderEndTime, "yyyy-MM-dd");
            int totalQuantity = 0;//分配数量
            for (SalePlanItemInfo salePlanItemInfo : purchaseItemInfoList) {
                totalQuantity += salePlanItemInfo.getOnSaleQuantity();
                //1. 校验时间是否为空
                String startTime = salePlanItemInfo.getStartTime();
                String endTime = salePlanItemInfo.getEndTime();
                boolean notEmpty = ValidationUtil.isNotEmpty(startTime, endTime);
                if (!notEmpty) {
                    return new GongxiaoResult(ErrorCode.DATE_NOT_NULL.getErrorCode(), ErrorCode.DATE_NOT_NULL.getMessage());
                }
                Date startDate = DateUtil.stringToDate(startTime, "yyyy-MM-dd");
                Date endDate = DateUtil.stringToDate(endTime, "yyyy-MM-dd");
                if (startDate.after(endDate)) {
                    return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_REPEAT_START_END_DATE);
                }
                //2. 校验客户预售单的有效期是否超出货品有效期

                if (startDate.before(orderStartDate) || endDate.after(orderEndDate)) {
                    return new GongxiaoResult(ErrorCode.DATE_OUT_EXPIRE.getErrorCode(), ErrorCode.DATE_OUT_EXPIRE.getMessage());
                }
                //3. 校验同货品在同一有效时间是否分配给客户多次
                List<SalesPlanItem> salesPlanItemList = planSaleItemService.getProductCustomer(rpcHeader, salePlanItemInfo.getProjectId(),
                        salePlanItemInfo.getCustomerId(),
                        salePlanItemInfo.getProductId(),
                        startDate,
                        endDate);
                for (SalesPlanItem salesPlanItem : salesPlanItemList) {
                    Date startTimeCustomer = salesPlanItem.getStartTime();
                    Date endTimeCustomer = salesPlanItem.getEndTime();
                    if (startDate.getTime() >= startTimeCustomer.getTime() && startDate.getTime() <= endTimeCustomer.getTime()
                            || endDate.getTime() >= startTimeCustomer.getTime() && endDate.getTime() <= endTimeCustomer.getTime()) {
                        return new GongxiaoResult(ErrorCode.EXPIRE_DATE_CUSTOMER_REPEAT.getErrorCode(), ErrorCode.EXPIRE_DATE_CUSTOMER_REPEAT.getMessage());

                    }
                }
                //4. 校验是否超出可下单货品可下单数量
                int unallocatedQuantity = salePlanDetail.getUnallocatedQuantity();
                if ((unallocatedQuantity < totalQuantity) || totalQuantity < 0) {
                    return new GongxiaoResult(ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getErrorCode(), ErrorCode.OVERTAKE_MAX_ALLOCATE_NUMBER.getMessage());
                }
            }
            //数据校验结束
            int addNumber = planSaleItemService.createSalePlanItem(rpcHeader, purchaseItemInfoList);
            logger.info("#traceId={}# [OUT] putCustomerSalePlan success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), addNumber);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 编辑获取明细信息
     *
     * @param itemId  明细ID
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEditItemInfo", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult getEditItemInfo(String itemId, HttpServletRequest request) throws Exception {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        logger.info("#traceId={}# [IN][getEditItemInfo] params: itemId:{}   ", itemId);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        if ("".equals(itemId)) {
            logger.error("#traceId={}# [OUT] fail to getEditItemInfo:", traceId);
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        try {
            SaleItemEditInfo editItemInfo = planSaleItemService.getEditItemInfo(rpcHeader, itemId);
            logger.info("#traceId={}# [OUT] getEditItemInfo success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), editItemInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 编辑明细信息
     *
     * @param adjustNumber         调整数量
     * @param itemId               客户计划货销售单号
     * @param brandPrepaidUnit     品牌商单位代垫
     * @param brandPrepaidDiscount 品牌商代垫折扣
     * @param yhPrepaidUnit        越海单位代垫
     * @param yhPrepaidDiscount    越海代垫折扣
     * @param startDate            开始日期
     * @param endDate              结束日期
     * @return
     * @aram adjustNumber 调整数量
     */
    @RequestMapping(value = "/editItemInfo", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult editItemInfo(String itemId,
                                       int adjustNumber,
                                       double brandPrepaidUnit,
                                       double brandPrepaidDiscount,
                                       double yhPrepaidUnit,
                                       double yhPrepaidDiscount,
                                       String startDate,
                                       String endDate,
                                       HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][editItemInfo] params: itemId:{}, adjustNumber:{}, brandPrepaidUnit:{}, brandPrepaidDiscount:{} " +
                            "#yhPrepaidUnit:{}, yhPrepaidDiscount:{}, startDate:{}, endDate:{} ",
                    itemId, adjustNumber, brandPrepaidUnit, brandPrepaidDiscount, yhPrepaidUnit, yhPrepaidDiscount, startDate, endDate);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            boolean dateNotNull = ValidationUtil.isNotEmpty(startDate, endDate);
            if (!dateNotNull) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.DATE_NOT_NULL);
            }
            Date startTime = DateUtil.stringToDate(startDate, "yyyy-MM-dd");
            Date endTime = DateUtil.stringToDate(endDate, "yyyy-MM-dd");
            if (startTime.after(endTime)) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_REPEAT_START_END_DATE);
            }
            SaleItemEditInfo editItemInfo = planSaleItemService.getEditItemInfo(rpcHeader, itemId);
            int itemAmount = editItemInfo.getRemainSaleAmount();//客户可销售总数
            int orderQuantity = editItemInfo.getUnallocatedQuantity();//货品剩余销售总数
            if (adjustNumber > orderQuantity) { //上调超过未处理货品总数
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SALE_ITEM_UP_ADJUST);
            }
            if (-adjustNumber > itemAmount) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.SALE_ITEM_DOWN_ADJUST);
            }

            int i = planSaleItemService.editSaleItem(rpcHeader, itemId,
                    adjustNumber,
                    brandPrepaidUnit,
                    brandPrepaidDiscount,
                    yhPrepaidUnit,
                    yhPrepaidDiscount,
                    startTime,
                    endTime);
            logger.info("#traceId={}# [OUT] editItemInfo success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), i);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }

    /**
     * 作废客户销售计划单
     *
     * @param itemIdJson 客户销售计划ID串
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelPlanSaleItemOrder", method = RequestMethod.GET)
    @ResponseBody
    public GongxiaoResult cancelPlanSaleItemOrder(String itemIdJson, HttpServletRequest request) throws Exception {
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][cancelPlanSaleItemOrder] params: #itemId:{}  ", itemIdJson);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            boolean notEmpty = ValidationUtil.isNotEmpty(itemIdJson);
            if (!notEmpty) {
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.NOT_SELECT_DATA);
            }
            String[] itemIdArray = itemIdJson.split(",");
            int cancleQuantity = 0;
            String salePlanNo = "";
            //变更客户预售明细
            for (String itemId : itemIdArray) {
                int cancelNumber = planSaleItemService.cancelPlanItem(rpcHeader, itemId);
                SalesPlanItem salesPlanItem = planSaleItemService.getOnSaleQuantity(rpcHeader, itemId);
                int unallocatedQuantity = salesPlanItem.getUnallocatedQuantity();//未分配数量的额度腾出来
                cancleQuantity += (unallocatedQuantity);
                salePlanNo = salesPlanItem.getSalesPlanNo();
            }
            //变更订单预售明细
            planSaleService.updateReturnSaleQuantity(rpcHeader, salePlanNo, cancleQuantity);
            logger.info("#traceId={}# [OUT] cancelPlanSaleItemOrder success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), "");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }

    }


}
