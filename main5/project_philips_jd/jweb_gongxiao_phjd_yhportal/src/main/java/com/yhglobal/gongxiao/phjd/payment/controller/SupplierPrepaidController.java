package com.yhglobal.gongxiao.phjd.payment.controller;


import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.phjd.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.phjd.base.protal.PortalConfig;
import com.yhglobal.gongxiao.phjd.bean.FrontPageFlow;
import com.yhglobal.gongxiao.phjd.bean.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.phjd.payment.util.AccountProtoUtil;
import com.yhglobal.gongxiao.phjd.utils.YhPortalTraceIdGenerator;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 供应商代垫帐户
 * @author: LUOYI
 * @Date: Created in 16:10 2018/5/10
 */
@Controller
@RequestMapping("/supplier/prepaid")
public class SupplierPrepaidController {
    @Autowired
    PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;



    private static Logger logger = LoggerFactory.getLogger(SupplierPrepaidController.class);
    /**
     * 实现上账
     * */
    @RequestMapping(value = "/posting")
    @ResponseBody
    public GongxiaoResult supplierCouponPosting(String supplierName,
                                                @RequestParam(defaultValue = "CNY")String currencyCode, @RequestParam(defaultValue = "")String remark,
                                                Double postingAmount, long projectId, HttpServletRequest request ){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][supplierCouponPosting] params:  supplierName:{}",
                    traceId, supplierName);
            // 上账金额校验
            if(postingAmount == null || postingAmount == 0 || postingAmount < 0){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.ARGUMENTS_INVALID);
            }
            Long postingAmountLong = Math.round(postingAmount * FXConstant.HUNDRED);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest rpcReq = SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setAmount(postingAmountLong)
                    .setCurrencyCode(currencyCode)
                    .setRemark(remark)
                    .build();
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResult = blockingStub.postSupplierPrepaidAccount(rpcReq);
            gongxiaoResult = new GongxiaoResult(rpcResult.getReturnCode(), rpcResult.getMsg());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), true);
            logger.info("#traceId={}# [OUT] supplier transportation posting success.");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /**
     * 查询供应商账户流水
     *
     * @param currencyCode    货币编码
     * @param supplierId      供应商id
     * @param projectId       项目id
     * @param moneyFlow       资金流向
     * @param beginDateString 日期开始
     * @param endDateString   日期结束
     * @return
     */
    @RequestMapping("/flow")
    @ResponseBody
    public GongxiaoResult selectPrepaidFlows(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                             @RequestParam(defaultValue = "1") long supplierId,
                                             @RequestParam(defaultValue = "0")long projectId,
                                             @RequestParam(defaultValue = "0")Integer moneyFlow,
                                             @RequestParam(defaultValue = "")String beginDateString,
                                             @RequestParam(defaultValue = "")String endDateString,
                                             @RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "60") int pageSize
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectPrepaidFlows] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, supplierId, projectId, moneyFlow, beginDateString, endDateString);
            SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest rpcReq = SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setSupplierId(supplierId)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setCurrencyCode(currencyCode)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .setPageNum(pageNum)
                    .setPageSize(pageSize)
                    .build();
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            PaymentCommonGrpc.FrontPageFlowPageInfo rpcResult = blockingStub.selectPrepaidBySupplierId(rpcReq);
            PageInfo<FrontPageFlow> list = AccountProtoUtil.getJavaPageFlow(rpcResult);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] post selectFlow success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 统计金额实现
     * */
    @RequestMapping("/subtotal")
    @ResponseBody
    public GongxiaoResult getAccountSubtotal(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                             @RequestParam(defaultValue = "1") long supplierId,
                                             @RequestParam(defaultValue = "0")long projectId,
                                             @RequestParam(defaultValue = "0")Integer moneyFlow,
                                             @RequestParam(defaultValue = "")String beginDateString,
                                             @RequestParam(defaultValue = "")String endDateString){

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, supplierId, projectId, moneyFlow, beginDateString, endDateString);
            SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest rpcReq = SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setSupplierId(supplierId)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setCurrencyCode(currencyCode)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .build();
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            PaymentCommonGrpc.FrontPageFlowSubtotal rpcResult = blockingStub.selectIncomeAndExpenditure(rpcReq);
            FrontPageFlowSubtotal frontPageFlowSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcResult);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] post prepaid success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
