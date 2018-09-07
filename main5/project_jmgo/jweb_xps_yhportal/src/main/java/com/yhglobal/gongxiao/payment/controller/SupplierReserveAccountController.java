package com.yhglobal.gongxiao.payment.controller;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.JmgoSupplierSellHighFlowViewModel;
import com.yhglobal.gongxiao.payment.entity.FrontPageFlow;
import com.yhglobal.gongxiao.payment.entity.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.entity.SupplierSellHeightAccount;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.payment.util.AccountProtoUtil;
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
import java.util.LinkedList;

/**
 * 供应商预留账户管理
 *
 * @author: 葛灿
 */
@Controller
@RequestMapping("/payment/supplier/reserve")
public class SupplierReserveAccountController {


    private static final Logger logger = LoggerFactory.getLogger(SupplierReserveAccountController.class);
    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象


    /**
     * 查询实收账户余额
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/sellHighAccount")
    @ResponseBody
    public GongxiaoResult getYhglobalReceivedAccount(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(defaultValue = "CNY") String currencyCode,
                                                     long projectId) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, projectId={}",
                    traceId, currencyCode, projectId);

            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.GetSellHighAccountRequest rpcRequest = SupplierAccountServiceStructure.GetSellHighAccountRequest.newBuilder()
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setRpcHeader(rpcHeader)
                    .build();
            SupplierAccountServiceStructure.GetSellHighAccountResponse getSellHighAccountResponse = rpcStub.getSellHighAccount(rpcRequest);
            SupplierSellHeightAccount sellHighAccount = new SupplierSellHeightAccount();
            sellHighAccount.setTotalAmountDoubleStr(getSellHighAccountResponse.getTotalAmountDoubleStr());

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), sellHighAccount);
            logger.info("#traceId={}# [OUT] get account amount success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 查询 低买高卖账户记录list
     *
     * @param currencyCode    货币编码
     * @param projectId       项目id
     * @param moneyFlow       资金流向
     * @param beginDateString 日期开始
     * @param endDateString   日期结束
     * @return
     */
    @RequestMapping("/sellHighFlowList")
    @ResponseBody
    public GongxiaoResult selectCouponRecord(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                             long projectId,
                                             @RequestParam(defaultValue = "0") Integer moneyFlow,
                                             @RequestParam(defaultValue = "") String beginDateString,
                                             @RequestParam(defaultValue = "") String endDateString,
                                             @RequestParam(defaultValue = "1") int pageNumber,
                                             @RequestParam(defaultValue = "20") int pageSize
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params: currencyCode={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, projectId, moneyFlow, beginDateString, endDateString);

            //销售差价账户
            SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest rpcRequest = SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize)
                    .build();
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            SupplierAccountServiceStructure.SelectSupplierSellHighFlowListResponse rpcResponse = rpcStub.selectSupplierSellHighFlowList(rpcRequest);
            LinkedList<JmgoSupplierSellHighFlowViewModel> javaList = new LinkedList<>();
            for (SupplierAccountServiceStructure.SelectSupplierSellHighFlow protoModel : rpcResponse.getFlowsList()) {
                JmgoSupplierSellHighFlowViewModel javaModel = new JmgoSupplierSellHighFlowViewModel();
                javaModel.setFlowNo(protoModel.getFlowNo());
                javaModel.setType(protoModel.getType());
                javaModel.setCurrencyCode(protoModel.getCurrencyCode());
                javaModel.setTransactionAmount(protoModel.getTransactionAmount());
                javaModel.setAmountAfterTransaction(protoModel.getAmountAfterTransaction());
                javaModel.setCreateByName(protoModel.getCreateByName());
                javaModel.setDistributorName(protoModel.getDistirbutorName());
                javaModel.setRemark(protoModel.getRemark());
                javaModel.setCreateTime(new Date(protoModel.getCreateTime()));
                javaList.add(javaModel);
            }
            PageInfo<JmgoSupplierSellHighFlowViewModel> pageInfo = new PageInfo<>(javaList);
            pageInfo.setTotal(rpcResponse.getTotal());
            pageInfo.setPageSize(rpcResponse.getPageSize());
            pageInfo.setPageNum(rpcResponse.getPageNum());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            logger.info("#traceId={}# [OUT] post prepaid success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 查询低买高卖账户 小计 支出收入数量、支出收入金额
     *
     * @param currencyCode    货币编码
     * @param supplierId      供应商id
     * @param projectId       项目id
     * @param moneyFlow       资金流向
     * @param beginDateString 日期开始
     * @param endDateString   日期结束
     * @return
     */
    @RequestMapping("/flowSubtotal")
    @ResponseBody
    public GongxiaoResult getAccountSubtotal(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                             @RequestParam(defaultValue = "1") long supplierId,
                                             long projectId,
                                             @RequestParam(defaultValue = "0") Integer moneyFlow,
                                             @RequestParam(defaultValue = "") String beginDateString,
                                             @RequestParam(defaultValue = "") String endDateString
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, supplierId, projectId, moneyFlow, beginDateString, endDateString);
            FrontPageFlowSubtotal frontPageFlowSubtotal;
            //查询总计
            //销售差价账户
            SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest rpcRequest = SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setCurrencyCode(currencyCode)
                    .setProjectId(projectId)
                    .setMoneyFlow(moneyFlow)
                    .setBeginDate(beginDateString)
                    .setEndDate(endDateString)
                    .build();
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);
            PaymentCommonGrpc.FrontPageFlowSubtotal rpcResponse = rpcStub.getSellHighSubtotal(rpcRequest);
            frontPageFlowSubtotal = AccountProtoUtil.getJavaPageSubtotal(rpcResponse);
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] post prepaid success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
