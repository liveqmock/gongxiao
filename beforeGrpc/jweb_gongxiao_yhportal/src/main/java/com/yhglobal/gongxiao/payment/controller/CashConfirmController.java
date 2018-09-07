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
import com.yhglobal.gongxiao.payment.service.CashConfirmService;
import com.yhglobal.gongxiao.sales.bo.SalesOrderList;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.apache.ibatis.exceptions.TooManyResultsException;
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

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;

/**
 * 收款确认
 *
 * @author: 葛灿
 */
@Controller
@RequestMapping("/payment/cashConfirm")
public class CashConfirmController {

    private static Logger logger = LoggerFactory.getLogger(CashConfirmController.class);
    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象
    @Reference
    CashConfirmService cashConfirmService;


    /**
     * 选择性查询(均为可选条件)
     *
     * @param salesOrderNo    销售单号
     * @param projectId       项目id
     * @param distributorName ad名称
     * @param bankName        收款银行
     * @param confirmBegin    确认时间开始
     * @param confirmEnd      确认时间截止
     * @param orderBegin      订单时间起始
     * @param orderEnd        订单时间截止
     * @param orderStatus     订单状态
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public GongxiaoResult selectList(HttpServletRequest request, HttpServletResponse response,
                                     String salesOrderNo,
                                     Long projectId,
                                     String distributorName,
                                     String bankName,
                                     String orderBegin,
                                     String orderEnd,
                                     String confirmBegin,
                                     String confirmEnd,
                                     Integer[] orderStatus,
                                     int pageNumber,
                                     int pageSize) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectList] params: salesOrderNo={}, projectId={}, distributorName={}, bankName={}, orderBegin={}, orderEnd={}, confirmBegin={}, confirmEnd={}, orderStatus={}",
                    traceId, salesOrderNo, projectId, distributorName, bankName, orderBegin, orderEnd, confirmBegin, confirmEnd, orderStatus);
            Date orderBeginDate = null;
            Date orderEndDate = null;
            Date confirmBeginDate = null;
            Date confirmEndDate = null;
            if (orderBegin != null && !"".equals(orderBegin)) {
                orderBeginDate = DateUtil.stringToDate(orderBegin, "yyyy-MM-dd");
            }
            if (orderEnd != null && !"".equals(orderEnd)) {
                orderEndDate = DateUtil.stringToDate(orderEnd, "yyyy-MM-dd");
            }
            if (confirmBegin != null && !"".equals(confirmBegin)) {
                confirmBeginDate = DateUtil.stringToDate(confirmBegin, "yyyy-MM-dd");
            }
            if (confirmEnd != null && !"".equals(confirmEnd)) {
                confirmEndDate = DateUtil.stringToDate(confirmEnd, "yyyy-MM-dd");
            }
            PageInfo<CashConfirm> salesCashConfirmPageInfo =
                    cashConfirmService.selectListSelective(rpcHeader, salesOrderNo, projectId, distributorName, bankName, orderBeginDate, orderEndDate, confirmBeginDate, confirmEndDate, orderStatus, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), salesCashConfirmPageInfo);
            logger.info("#traceId={}# [OUT] get list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 确认收款页面，展示可确认的列表
     *
     * @param salesOrderNoList 销售单号数组
     * @return
     */
    @RequestMapping("/confirmList")
    @ResponseBody
    public GongxiaoResult selectConfirmList(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(required = true) String[] salesOrderNoList) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectConfirmList] params: salesOrderNo={}, projectId={}, distributorId={}, orderBegin={}, orderEnd={}, orderStatus={}, pageNumber={}, pageSize={} ",
                    traceId);
            List<CashConfirm> list =
                    cashConfirmService.selectConfirmList(rpcHeader, salesOrderNoList);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] get confirm list success.");
        } catch (TooManyResultsException | NullPointerException e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.CASH_DATA_ALREADY_MODIFIED);
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 确认收款
     *
     * @param bankName            收款银行
     * @param recipientDateString 收款日期
     * @param itemsJson           确认收款详情
     * @param bankFlowNo          银行流水号
     * @return
     */
    @RequestMapping("/confirm")
    @ResponseBody
    public GongxiaoResult<SalesOrderList> confirmCash(HttpServletRequest request, HttpServletResponse response,
                                                      String bankName, String recipientDateString, String itemsJson,
                                                      String bankFlowNo) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][confirmCash] params:  bankName={}, recipientDate={}, itemsJson={}",
                    traceId, bankName, recipientDateString, itemsJson);
            //没有填写收款银行
            if (bankName == null || "".equals(bankName)) {
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ARGUMENTS_INVALID);
                return gongxiaoResult;
            }
            //没有填写收款日期
            if (recipientDateString == null || "".equals(recipientDateString)) {
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ARGUMENTS_INVALID);
                return gongxiaoResult;
            }
            Date recipientDate = null;
            if (!"".equals(recipientDateString)) {
                recipientDate = DateUtil.stringToDate(recipientDateString);
            }
            if (bankFlowNo == null || "".equals(bankFlowNo)) {
                gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ARGUMENTS_INVALID);
                return gongxiaoResult;
            }
            List<CashConfirm> cashConfirms = JSON.parseArray(itemsJson, CashConfirm.class);
            for (CashConfirm cashConfirm : cashConfirms) {
                double confirmAmountDouble = Double.parseDouble(cashConfirm.getConfirmAmountDouble());
                if (confirmAmountDouble == 0) {
                    gongxiaoResult = new GongxiaoResult<>(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                    return gongxiaoResult;
                }

            }
            RpcResult rpcResult = cashConfirmService.confirmCash(rpcHeader, bankName, recipientDate, cashConfirms, bankFlowNo);
            gongxiaoResult = new GongxiaoResult<>(rpcResult.getCode(), rpcResult.getMessage());
            logger.info("#traceId={}# [OUT] confirm success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
