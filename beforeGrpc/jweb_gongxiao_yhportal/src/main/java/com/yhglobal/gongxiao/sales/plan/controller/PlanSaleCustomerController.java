package com.yhglobal.gongxiao.sales.plan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.sales.model.plan.vo.CustomerPlanInfo;
import com.yhglobal.gongxiao.sales.model.plan.vo.CustomerPlanItemInfo;
import com.yhglobal.gongxiao.sales.service.PlanSaleCustomerService;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@RequestMapping("/customerPlanSale")
public class PlanSaleCustomerController {

    private static Logger logger = LoggerFactory.getLogger(PlanSaleCustomerController.class);

    @Reference
    PlanSaleCustomerService planSaleCustomerService;

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    @RequestMapping("/selectCustomerSalePlanList")
    @ResponseBody
    public GongxiaoResult selectCustomerSalePlanList(String projectId,
                                                     String distributorId,
                                                     String distributorName,
                                                     int pageNumber,
                                                     int pageSize,
                                                     HttpServletRequest request) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        logger.info("#traceId={}# [IN][selectCustomerSalePlanList] params: distributorId:{}, distributorName:{} ",
                projectId, distributorId, distributorName);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            PageInfo<CustomerPlanInfo> customerPlanInfoPageInfo = planSaleCustomerService.selectCustomerPlanList(rpcHeader,
                    projectId,
                    distributorId,
                    distributorName,
                    pageNumber,
                    pageSize);
            logger.info("#traceId={}# [OUT] selectCustomerSalePlanList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), customerPlanInfoPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @RequestMapping("/selectCustomerSalePlanItemList")
    @ResponseBody
    public GongxiaoResult selectCustomerSalePlanItemList(String projectId,
                                                         String distributorId,
                                                         String salePlanNo,
                                                         String productCode,
                                                         String productName,
                                                         int orderStatus,
                                                         String startDate,
                                                         String endDate,
                                                         int pageNumber,
                                                         int pageSize,
                                                         HttpServletRequest request) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        logger.info("#traceId={}# [IN][selectCustomerSalePlanItemList] params: distributorId:{} ",
                projectId, distributorId);
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        try {
            Date startTime = DateUtil.stringToDate(startDate);
            Date endTime = DateUtil.stringToDate(endDate);

            PageInfo<CustomerPlanItemInfo> customerPlanItemInfoPageInfo = planSaleCustomerService.selectCustomerPlanItemList(rpcHeader,
                    projectId,
                    distributorId,
                    salePlanNo,
                    productCode,
                    productName,
                    orderStatus,
                    startTime,
                    endTime,
                    pageNumber,
                    pageSize);
            logger.info("#traceId={}# [OUT] selectCustomerSalePlanItemList success: ", traceId);
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), customerPlanItemInfoPageInfo);

        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}
