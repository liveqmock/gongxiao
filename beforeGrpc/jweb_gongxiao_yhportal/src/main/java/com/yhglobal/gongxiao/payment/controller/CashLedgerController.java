package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import com.yhglobal.gongxiao.payment.service.CashLedgerService;
import com.yhglobal.gongxiao.sales.bo.SalesOrderList;
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
import java.util.List;

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
    @Reference
    CashLedgerService cashLedgerService;


    /**
     * 选择性查询(均为可选条件)
     *
     * @param projectId     项目id
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
                                           Long projectId,
                                           String bankName,
                                           String flowNo,
                                           String confirmBegin,
                                           String confirmEnd,
                                           String receiveBegin,
                                           String receiveEnd,
                                           Boolean approveStatus,
                                           @RequestParam(defaultValue = "1") int pageNumber,
                                           @RequestParam(defaultValue = "100") int pageSize) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectLedgerList] params: projectId={}, bankName={}, flowNo={}, confirmBegin={}, confirmEnd={}, receiveBegin={}, receiveEnd={}, approveStatus={}, pageNum={}, pageSize={}",
                    traceId, projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus, pageNumber, pageSize);
            Date confirmBeginDate = DateUtil.stringToDate(confirmBegin);
            Date confirmEndDate = DateUtil.stringToDate(confirmEnd);
            Date receiveBeginDate = DateUtil.stringToDate(receiveBegin);
            Date receiveEndDate = DateUtil.stringToDate(receiveEnd);
            PageInfo<CashLedger> pageInfo =
                    cashLedgerService.selectCashLedgerList(rpcHeader, projectId, bankName, flowNo, confirmBeginDate, confirmEndDate, receiveBeginDate, receiveEndDate, approveStatus, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
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
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][approveLedger] params: flowNo={}",
                    traceId, flowNo);
            RpcResult rpcResult = cashLedgerService.approveLedger(rpcHeader, flowNo);
            if (rpcResult.getSuccess()) {
                logger.info("#traceId={}# [OUT] approve success.");
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
            } else {
                logger.info("#traceId={}# [OUT] approve fail.");
                gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());
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
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][cancelLedger] params: flowNo={}",
                    traceId, flowNo);
            RpcResult rpcResult = cashLedgerService.cancelApproveLedger(rpcHeader, flowNo);
            if (rpcResult.getSuccess()) {
                //如果修改成功
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] cancel success.");
            } else {
                gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());
                logger.info("#traceId={}# [OUT] cancel FAILED.");
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
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][cancelLedger] params: flowNo{}",
                    traceId, String.valueOf(flowNo));
            RpcResult rpcResult = cashLedgerService.cancelConfirm(rpcHeader, flowNo);
            if (rpcResult.getSuccess()) {
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
                logger.info("#traceId={}# [OUT] cancel success.");
            } else {
                gongxiaoResult = new GongxiaoResult<>(rpcResult.getCode(), rpcResult.getMessage());
                logger.info("#traceId={}# [OUT] cancel failed.");
            }
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
