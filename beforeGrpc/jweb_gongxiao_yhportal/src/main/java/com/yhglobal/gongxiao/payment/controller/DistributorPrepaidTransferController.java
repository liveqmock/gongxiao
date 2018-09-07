package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.purchase.controller.PurchaseOrderController;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description:
 * @author: LUOYI
 * @Date: Created in 10:38 2018/5/11
 */
@Controller
@RequestMapping("/distributor/prepaid")
public class DistributorPrepaidTransferController {
    private static Logger logger = LoggerFactory.getLogger(DistributorPrepaidTransferController.class);


    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    @Reference
    DistributorAccountService distributorAccountService;

    /**
     * 代垫转入
     * @param distributorId    经销商ID
     * @param receivedAmount   实收金额
     * @param currencyCode     货币编码(不需传)
     * @param projectId        项目ID
     * @param distributorName  经销商名称
     * @param remark           备注
     * */
    @RequestMapping(value = "/transfer")
    @ResponseBody
    public GongxiaoResult distributorCouponTansfer(long distributorId, Double receivedAmount,
                                                   @RequestParam(defaultValue = "CNY") String currencyCode,
                                                    long projectId, String distributorName,
                                                   String remark, HttpServletRequest request){

        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][generatePurchaseOrders] params: distributorId:{}, receivedAmount:{},currencyCode:{}," +
                            "projectId:{},projectName:{},distributorName:{},remark:{}",
                    traceId, distributorId,receivedAmount,currencyCode
                    ,projectId,distributorName,remark);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            if(receivedAmount == null || receivedAmount == 0){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.ARGUMENTS_INVALID);
            }
            Long receivedAmountLong = Math.round(receivedAmount * FXConstant.HUNDRED);
            distributorAccountService.depositPrepaidReceived(rpcHeader,currencyCode,distributorId,distributorName,
                    projectId,receivedAmountLong,new Date(),remark);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
            logger.info("#traceId={}# [OUT] distributor coupon transfer success.");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目ID和供应商ID查询代垫流水
     * @param currencyCode
     * */
    @RequestMapping("/flowList")
    @ResponseBody
    public GongxiaoResult selectFlow(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(defaultValue = "CNY") String currencyCode,
                                           @RequestParam(defaultValue = "2") long distributorId,
                                           long projectId,
                                           int accountType,
                                           Integer moneyFlow,
                                           String beginDateString,
                                           String endDateString,
                                           @RequestParam(defaultValue = "1") int pageNum,
                                           @RequestParam(defaultValue = "50") int pageSize
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectFlow] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode,distributorId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            PageInfo<FrontPageFlow> list;
            list = distributorAccountService.selectPrepaidFlow(rpcHeader,currencyCode,distributorId,projectId,accountType,moneyFlow,beginDate,endDate,pageNum,pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] post selectFlow success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }



    /**
     * 统计金额(流水的总和)
     * */
    @RequestMapping("/subtotal")
    @ResponseBody
    public GongxiaoResult getSubtotal(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(defaultValue = "CNY") String currencyCode,
                                      @RequestParam(defaultValue = "2") long distributorId,
                                      long projectId,
                                      Integer moneyFlow,
                                      String beginDateString,
                                      String endDateString
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSubtotal] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, distributorId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            FrontPageFlowSubtotal frontPageFlowSubtotal;
            frontPageFlowSubtotal = distributorAccountService.getPrepaidSubtotal(rpcHeader,currencyCode,distributorId,projectId,moneyFlow,beginDate,endDate);

            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] post getSubtotal success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
