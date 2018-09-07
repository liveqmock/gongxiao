package com.yhglobal.gongxiao.payment.controller;

import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.entity.CashLedger;
import com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure;
import com.yhglobal.gongxiao.payment.microservice.CashLedgerServiceGrpc;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
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
import java.util.ArrayList;

/**
 * 台账
 *
 * @author: 葛灿
 */
@Controller
@RequestMapping("/payment/cash/ledger")
public class CashLedgerController {

    private static Logger logger = LoggerFactory.getLogger(CashLedgerController.class);
    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象


    /**
     * 选择性查询(均为可选条件)
     *
     * @param bankName      银行名称
     * @param flowNo        流水号
     * @param confirmBegin  确认开始时间
     * @param confirmEnd    确认截止时间
     * @param receiveBegin  收款开始时间
     * @param receiveEnd    收款截止时间
     * @param approveStatus 审核状态
     * @param pageNumber    页码
     * @param pageSize      条数
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public GongxiaoResult selectLedgerList(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(defaultValue = "") String bankName,
                                           @RequestParam(defaultValue = "") String flowNo,
                                           @RequestParam(defaultValue = "") String confirmBegin,
                                           @RequestParam(defaultValue = "") String confirmEnd,
                                           @RequestParam(defaultValue = "") String receiveBegin,
                                           @RequestParam(defaultValue = "") String receiveEnd,
                                           @RequestParam(defaultValue = "") String approveStatus,
                                           @RequestParam(defaultValue = "") String clientName,
                                           int pageNumber,
                                           int pageSize) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectLedgerList] params: projectId={}, bankName={}, flowNo={}, confirmBegin={}, confirmEnd={}, receiveBegin={}, receiveEnd={}, approveStatus={}, pageNum={}, pageSize={}",
                    traceId, projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus, pageNumber, pageSize);
            CashLedgerSerivceStructure.SelectCashLedgerListRequest rpcRequest = CashLedgerSerivceStructure.SelectCashLedgerListRequest.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(projectId)
                    .setBankName(bankName)
                    .setConfirmBegin(confirmBegin)
                    .setConfirmEnd(confirmEnd)
                    .setReceiveBegin(receiveBegin)
                    .setReceiveEnd(receiveEnd)
                    .setApproveStatus(approveStatus)
                    .setPageNum(pageNumber)
                    .setPageSize(pageSize)
                    .setClientName(clientName)
                    .build();
            CashLedgerServiceGrpc.CashLedgerServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashLedgerServiceGrpc.CashLedgerServiceBlockingStub.class);
            CashLedgerSerivceStructure.SelectCashLedgerListResponse rpcResponse = rpcStub.selectCashLedgerList(rpcRequest);
            ArrayList<CashLedger> javaList = new ArrayList<>();
            for (CashLedgerSerivceStructure.CashLedgerResponse protoObject : rpcResponse.getListList()) {
                CashLedger javaObject = new CashLedger();
                javaObject.setFlowNo(protoObject.getFlowNo());
                javaObject.setApprovalStatus(protoObject.getApprovalStatus());
                javaObject.setDistributorName(protoObject.getDistributorName());
                javaObject.setConfirmAmountDouble(protoObject.getConfirmAmountDoubleStr());
                javaObject.setRecipientAmountDouble(protoObject.getRecipientAmountDoubleStr());
                javaObject.setConfirmTime(GrpcCommonUtil.getJavaParam(protoObject.getConfirmTime()));
                javaObject.setApproveTime(GrpcCommonUtil.getJavaParam(protoObject.getApproveTime()));
                javaObject.setApprovalUserName(protoObject.getApprovalUserName());
                javaObject.setReceiveTime(GrpcCommonUtil.getJavaParam(protoObject.getReceiveTime()));
                javaObject.setBankName(protoObject.getBankName());
                javaObject.setProjectName(protoObject.getProjectName());
                javaObject.setBankFlowNo(GrpcCommonUtil.getJavaParam(protoObject.getBankFlowNo()));
                javaObject.setClientName(GrpcCommonUtil.getJavaParam(protoObject.getClientName()));
                javaObject.setCreatedByName(GrpcCommonUtil.getJavaParam(protoObject.getCreatedByName()));
                javaList.add(javaObject);
            }
            PageInfo<CashLedger> pageInfo = new PageInfo<>();
            pageInfo.setList(javaList);
            pageInfo.setTotal(rpcResponse.getTotal());
            pageInfo.setPageNum(rpcResponse.getPageNum());
            pageInfo.setPageSize(rpcResponse.getPageSize());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            logger.info("#traceId={}# [OUT] get list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 审批
     *
     * @param flowNo 流水号
     * @return
     */
    @RequestMapping("/approve")
    @ResponseBody
    public GongxiaoResult approveLedger(HttpServletRequest request, HttpServletResponse response,
                                        String flowNo) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][approveLedger] params: flowNo={}", traceId, flowNo);
            CashLedgerSerivceStructure.CashLedgerRequest rpcRequest = CashLedgerSerivceStructure.CashLedgerRequest.newBuilder()
                    .setProjectId(projectId)
                    .setFlowNo(flowNo).setRpcHeader(rpcHeader).build();
            CashLedgerServiceGrpc.CashLedgerServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashLedgerServiceGrpc.CashLedgerServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.approveLedger(rpcRequest);
            if (rpcResponse.getReturnCode() == 0) {
                logger.info("#traceId={}# [OUT] approve success.");
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
            } else {
                logger.info("#traceId={}# [OUT] approve fail.");
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 取消审批
     *
     * @param flowNo 流水号
     * @return
     */
    @RequestMapping("/cancelApprove")
    @ResponseBody
    public GongxiaoResult cancelLedger(HttpServletRequest request, HttpServletResponse response,
                                       String flowNo) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][cancelLedger] params: flowNo={}",
                    traceId, flowNo);
            CashLedgerSerivceStructure.CashLedgerRequest rpcRequest = CashLedgerSerivceStructure.CashLedgerRequest.newBuilder()
                    .setProjectId(projectId)
                    .setFlowNo(flowNo).setRpcHeader(rpcHeader).build();
            CashLedgerServiceGrpc.CashLedgerServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashLedgerServiceGrpc.CashLedgerServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.cancelApproveLedger(rpcRequest);
            if (rpcResponse.getReturnCode() == 0) {
                logger.info("#traceId={}# [OUT] cancel success.");
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
            } else {
                logger.info("#traceId={}# [OUT] cancel fail.");
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 取消确认
     *
     * @param flowNo 流水号 数组
     * @return
     */
    @RequestMapping("/cancelConfirm")
    @ResponseBody
    public GongxiaoResult cancelConfirm(HttpServletRequest request, HttpServletResponse response,
                                        String flowNo) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            // 从session中获取projectId
            long projectId = portalUserInfo.getProjectId();

            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][cancelLedger] params: flowNo{}",
                    traceId, String.valueOf(flowNo));
            CashLedgerSerivceStructure.CashLedgerRequest rpcRequest = CashLedgerSerivceStructure.CashLedgerRequest.newBuilder()
                    .setProjectId(projectId)
                    .setFlowNo(flowNo).setRpcHeader(rpcHeader).build();
            CashLedgerServiceGrpc.CashLedgerServiceBlockingStub rpcStub = RpcStubStore.getRpcStub(CashLedgerServiceGrpc.CashLedgerServiceBlockingStub.class);
            GongxiaoRpc.RpcResult rpcResponse = rpcStub.cancelConfirm(rpcRequest);
            if (rpcResponse.getReturnCode() == 0) {
                logger.info("#traceId={}# [OUT] cancel success.");
                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), null);
            } else {
                logger.info("#traceId={}# [OUT] cancel fail.");
                gongxiaoResult = new GongxiaoResult(rpcResponse.getReturnCode(), rpcResponse.getMsg());
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
