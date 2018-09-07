package com.yhglobal.gongxiao.payment.controller;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.entity.AccountAmount;
import com.yhglobal.gongxiao.payment.entity.FrontPageFlow;
import com.yhglobal.gongxiao.payment.entity.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.util.AccountProtoUtil;
import com.yhglobal.gongxiao.purchase.controller.PurchaseOrderController;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;
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
 * 查询ad现金、流水
 * 页面位于账户管理-分销商账户-明细
 *
 * @author 葛灿
 */

@Controller
@RequestMapping("/distributor/cash")
public class DistributorCashTransferController {


    private static Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);


    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类


    /**
     * 根据项目ID和供应商ID查询现金流水
     *
     * @param currencyCode    货币编码
     * @param distributorId   供应商id
     * @param projectId       项目id
     * @param moneyFlow       资金流向
     * @param beginDateString 查询开始时间
     * @param endDateString   查询截止时间
     * @param pageNumber         页码
     * @param pageSize        条数
     * @return
     */
    @RequestMapping("/flowList")
    @ResponseBody
    public GongxiaoResult selectCashFlow(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "CNY") String currencyCode,
                                         long distributorId,
                                         long projectId,
                                         @RequestParam(defaultValue = "0") Integer moneyFlow,
                                         @RequestParam(defaultValue = "") String beginDateString,
                                         @RequestParam(defaultValue = "") String endDateString,
                                         int pageNumber,
                                         int pageSize
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectCashFlow] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, distributorId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            PageInfo<FrontPageFlow> list;
            //查询流水
            //现金账户
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub distributorAccountService = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            DistributorAccountServiceStructure.SelectDistributorFlowRqeust selectDistributorFlowRqeust = DistributorAccountServiceStructure.SelectDistributorFlowRqeust.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setDistributorId(distributorId)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize).build();
            PaymentCommonGrpc.FrontPageFlowPageInfo rpcPageInfo = distributorAccountService.selectCashFlow(selectDistributorFlowRqeust);
            list = AccountProtoUtil.getJavaPageFlow(rpcPageInfo);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] get flow list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 查询现金余额
     *
     * @param currencyCode  货币编码
     * @param distributorId 供应商id
     * @param projectId     项目id
     * @return
     */
    @RequestMapping("/accountAmount")
    @ResponseBody
    public GongxiaoResult getDistributorCashAccountAmount(HttpServletRequest request, HttpServletResponse response,
                                                          @RequestParam(defaultValue = "CNY") String currencyCode,
                                                          long distributorId,
                                                          long projectId) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, supplierId={}, projectId={}",
                    traceId, currencyCode, distributorId, projectId);

            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub distributorAccountService = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest getDistributorAccountAmountRequest = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setDistributorId(distributorId).build();
            PaymentCommonGrpc.AccountAmountResponse rpcResponse = distributorAccountService.getDistributorAccountAmount(getDistributorAccountAmountRequest);

            AccountAmount accountAmount = AccountProtoUtil.getJavaAccountAmount(rpcResponse);

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), accountAmount);
            logger.info("#traceId={}# [OUT] get account amount success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 统计金额(流水的总和)
     */
    @RequestMapping("/subtotal")
    @ResponseBody
    public GongxiaoResult getSubtotal(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(defaultValue = "CNY") String currencyCode,
                                      @RequestParam(defaultValue = "2") long distributorId,
                                      long projectId,
                                      @RequestParam(defaultValue = "0")Integer moneyFlow,
                                      @RequestParam(defaultValue = "")String beginDateString,
                                      @RequestParam(defaultValue = "")String endDateString
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getSubtotal] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, distributorId, projectId, moneyFlow, beginDateString, endDateString);
            FrontPageFlowSubtotal frontPageFlowSubtotal;
            //查询总计
            //现金账户
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub distributorAccountService = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            DistributorAccountServiceStructure.GetDistributorSubtotalRequest getDistributorSubtotalRequest = DistributorAccountServiceStructure.GetDistributorSubtotalRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setDistributorId(distributorId)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString).build();
            PaymentCommonGrpc.FrontPageFlowSubtotal rpcResponse = distributorAccountService.getCashSubtotal(getDistributorSubtotalRequest);
            frontPageFlowSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcResponse);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] get subtotal success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
