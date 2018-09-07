package com.yhglobal.gongxiao.plansale.controller;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.plansale.controller.vo.CustomerPlanInfo;
import com.yhglobal.gongxiao.plansale.controller.vo.CustomerPlanItemInfo;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerServiceGrpc;
import com.yhglobal.gongxiao.plansale.microservice.PlanSaleCustomerStructure;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author: 陈浩
 **/

@Controller
@RequestMapping("/customerPlanSale")
public class PlanSaleCustomerController {

    private static Logger logger = LoggerFactory.getLogger(PlanSaleCustomerController.class);

    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;


    @RequestMapping("/selectCustomerSalePlanList")
    @ResponseBody
    public GongxiaoResult selectCustomerSalePlanList(String distributorId,
                                                     String distributorName,
                                                     int pageNumber,
                                                     int pageSize,
                                                     HttpServletRequest request) {
        String traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        long projectId = portalUserInfo.getProjectId();

        logger.info("#traceId={}# [IN][selectCustomerSalePlanList] params: distributorId:{}, distributorName:{} ",
                projectId, distributorId, distributorName);
        try {
            PlanSaleCustomerServiceGrpc.PlanSaleCustomerServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleCustomerServiceGrpc.PlanSaleCustomerServiceBlockingStub.class);
            PlanSaleCustomerStructure.SelectCustomerPlanListReq selectCustomerPlanListReq = PlanSaleCustomerStructure.SelectCustomerPlanListReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId+"")
                    .setDistributorId(distributorId)
                    .setDistributorName(distributorName)
                    .setPageNumber(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            PlanSaleCustomerStructure.SelectCustomerPlanListResp selectCustomerPlanListResp = rpcStub.selectCustomerPlanList(selectCustomerPlanListReq);
            PageInfo<CustomerPlanInfo> customerPlanInfoPageInfo = new PageInfo<>();
            List<PlanSaleCustomerStructure.CustomerPlanInfo> customerPlanInfoListList = selectCustomerPlanListResp.getCustomerPlanInfoListList();
            long total = selectCustomerPlanListResp.getTotal();
            List<CustomerPlanInfo> customerPlanInfoList = new ArrayList<>();
            for (PlanSaleCustomerStructure.CustomerPlanInfo customerPlanInfo : customerPlanInfoListList) {
                CustomerPlanInfo customerPlanInfo1 = new CustomerPlanInfo();
                customerPlanInfo1.setBrandId(customerPlanInfo.getBrandId());
                customerPlanInfo1.setBrandName(customerPlanInfo.getBrandName());
                customerPlanInfo1.setProjectId(customerPlanInfo.getProjectId());
                customerPlanInfo1.setProjectName(customerPlanInfo.getProjectName());
                customerPlanInfo1.setDistributorId(customerPlanInfo.getDistributorId());
                customerPlanInfo1.setDistributorName(customerPlanInfo.getDistributorName());
                customerPlanInfo1.setOnSaleQuantity(customerPlanInfo.getOnSaleQuantity());
                customerPlanInfo1.setOnSaleCategory(customerPlanInfo.getOnSaleCategory());
                customerPlanInfo1.setSaleOccupationQuantity(customerPlanInfo.getSaleOccupationQuantity());
                customerPlanInfo1.setSoldQuantity(customerPlanInfo.getSoldQuantity());
                customerPlanInfo1.setRemainQuantity(customerPlanInfo.getRemainQuantity());
                customerPlanInfoList.add(customerPlanInfo1);
            }
            customerPlanInfoPageInfo.setTotal(total);
            customerPlanInfoPageInfo.setList(customerPlanInfoList);
            logger.info("#traceId={}# [OUT] selectCustomerSalePlanList success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), customerPlanInfoPageInfo);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @RequestMapping("/selectCustomerSalePlanItemList")
    @ResponseBody
    public GongxiaoResult selectCustomerSalePlanItemList(String distributorId,
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
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        long projectId = portalUserInfo.getProjectId();

        logger.info("#traceId={}# [IN][selectCustomerSalePlanItemList] params: distributorId:{} ",
                projectId, distributorId);
        Date startTime = DateUtil.stringToDate(startDate);
        Date endTime = DateUtil.stringToDate(endDate);
        PlanSaleCustomerServiceGrpc.PlanSaleCustomerServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(PlanSaleCustomerServiceGrpc.PlanSaleCustomerServiceBlockingStub.class);
        PlanSaleCustomerStructure.SelectCustomerPlanItemListReq selectCustomerPlanItemListReq = PlanSaleCustomerStructure.SelectCustomerPlanItemListReq.newBuilder()
                .setRpcHeader(rpcHeader)
                .setProjectId(projectId+"")
                .setDistributorId(distributorId)
                .setSalePlanNo(salePlanNo)
                .setProductModle(productCode)
                .setProductName(productName)
                .setOrderStatus(orderStatus)
                .setStartDate(DateUtil.getTime(startTime))
                .setEndDate(DateUtil.getTime(endTime))
                .build();


        try {
            PlanSaleCustomerStructure.SelectCustomerPlanItemListResp selectCustomerPlanItemListResp
                    = rpcStub.selectCustomerPlanItemList(selectCustomerPlanItemListReq);

            PageInfo<CustomerPlanItemInfo> customerPlanItemInfoPageInfo = new PageInfo<>();
            List<CustomerPlanItemInfo> customerPlanItemInfoList = new ArrayList<>();

            List<PlanSaleCustomerStructure.CustomerPlanItemInfo> customerPlanItemInfoListList
                    = selectCustomerPlanItemListResp.getCustomerPlanItemInfoListList();
            for (PlanSaleCustomerStructure.CustomerPlanItemInfo customerPlanItemInfo:customerPlanItemInfoListList){
                CustomerPlanItemInfo customerPlanItemInfo1 = new CustomerPlanItemInfo();
                customerPlanItemInfo1.setProductCode(customerPlanItemInfo.getProductModle());
                customerPlanItemInfo1.setProductName(customerPlanItemInfo.getProductName());
                customerPlanItemInfo1.setAllocatedQuantity(customerPlanItemInfo.getAllocatedQuantity());
                customerPlanItemInfo1.setSaleOccupationQuantity(customerPlanItemInfo.getSaleOccupationQuantity());
                customerPlanItemInfo1.setSoldQuantity(customerPlanItemInfo.getSoldQuantity());
                customerPlanItemInfo1.setCouldBuyNumber(customerPlanItemInfo.getCouldBuyNumber());
                customerPlanItemInfo1.setCurrencyCode(customerPlanItemInfo.getCurrencyCode());
                customerPlanItemInfo1.setGuidePrice(customerPlanItemInfo.getGuidePrice());
                customerPlanItemInfo1.setBrandPrepaidDiscount(customerPlanItemInfo.getBrandPrepaidDiscount());
                customerPlanItemInfo1.setYhPrepaidDiscount(customerPlanItemInfo.getYhPrepaidDiscount());
                customerPlanItemInfo1.setWholesalePrice(customerPlanItemInfo.getWholesalePrice());
                customerPlanItemInfo1.setRecordStatus(customerPlanItemInfo.getRecordStatus());
                customerPlanItemInfo1.setSalePlaneNo(customerPlanItemInfo.getSalePlaneNo());
                customerPlanItemInfo1.setStartDate(customerPlanItemInfo.getStartDate());
                customerPlanItemInfo1.setEndDate(customerPlanItemInfo.getEndDate());
                customerPlanItemInfoList.add(customerPlanItemInfo1);
            }
            customerPlanItemInfoPageInfo.setList(customerPlanItemInfoList);
            customerPlanItemInfoPageInfo.setTotal(selectCustomerPlanItemListResp.getTotal());
            logger.info("#traceId={}# [OUT] selectCustomerSalePlanItemList success: ", rpcHeader.getTraceId());
            return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), customerPlanItemInfoPageInfo);

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    }

}

