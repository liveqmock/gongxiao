package com.yhglobal.gongxiao.distributor.account.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.distributor.purchase.controller.OrderController;
import com.yhglobal.gongxiao.distributor.util.DistributorPortalTraceIdGenerator;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorPortalUserService;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.flow.service.DistributorCouponTransferService;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
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
 * AD账户Controller
 *
 * @author: 葛灿
 */
@Controller
@RequestMapping("/account")
public class AdDistributorAccountController {

    private static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private PortalConfig portalConfig;

    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象
    @Reference
    DistributorAccountService distributorAccountService;
    @Reference
    DistributorCouponTransferService distributorCouponTransferService;
    @Reference
    private DistributorPortalUserService distributorPortalUserService;

    /**
     * 查询现金余额
     *
     * @param currencyCode 货币编码
     * @return
     */
    @RequestMapping("/accountAmount")
    @ResponseBody
    public GongxiaoResult getDistributorCashAccountAmount(HttpServletRequest request, HttpServletResponse response,
                                                          @RequestParam(defaultValue = "CNY") String currencyCode) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, projectId={}",
                    traceId, currencyCode);
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader, portalUserInfo.getUserId());
            AccountAmount accountAmount = distributorAccountService.getDistributorAccountAmount(rpcHeader, currencyCode, distributorPortalUser.getProjectId(), distributorPortalUser.getDistributorId());
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), accountAmount);
            logger.info("#traceId={}# [OUT] get account amount success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目ID和供应商ID查询现金流水
     *
     * @param accountType       账户类型 1:现金 2 返利 3 代垫
     * @param currencyCode    货币编码 (可选)
     * @param moneyFlow       资金流向 305支出 306收入 (可选)
     * @param beginDateString 查询开始时间(可选)
     * @param endDateString   查询截止时间(可选)
     * @param pageNumber      页码
     * @param pageSize        条数
     * @return
     */
    @RequestMapping("/flowList")
    @ResponseBody
    public GongxiaoResult selectCashFlow(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "CNY") String currencyCode,
                                         Integer moneyFlow,
                                         Integer accountType,
                                         String beginDateString,
                                         String endDateString,
                                         @RequestParam(defaultValue = "1") int pageNumber,
                                         @RequestParam(defaultValue = "50") int pageSize
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        portalUserInfo.setUserId("1");
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectCashFlow] params: currencyCode={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode,  moneyFlow, beginDateString, endDateString);
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader, portalUserInfo.getUserId());
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            PageInfo<FrontPageFlow> list = null;
            //查询流水
            switch (accountType) {
                case 1:
                    //现金账户
                    list = distributorAccountService.selectCashFlow(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), moneyFlow, beginDate, endDate, pageNumber, pageSize);
                    break;
                case 2:
                    //返利账户
                    list = distributorCouponTransferService.selectCouponFlow(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), moneyFlow, beginDate, endDate, pageNumber, pageSize);
                    break;
                case 3:
                    //代垫账户
                    list = distributorAccountService.selectPrepaidFlow(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), accountType, moneyFlow, beginDate, endDate, pageNumber, pageSize);
                    break;
                default:
                    list = null;
            }
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] get flow list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 根据项目ID和供应商ID查询现金流水
     *
     * @param currencyCode    货币编码
     * @param moneyFlow       资金流向
     * @param beginDateString 查询开始时间
     * @param endDateString   查询截止时间
     * @return
     */
    @RequestMapping("/subtotal")
    @ResponseBody
    public GongxiaoResult selectCashFlow(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "CNY") String currencyCode,
                                         Integer moneyFlow,
                                         Integer accountType,
                                         String beginDateString,
                                         String endDateString
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        portalUserInfo.setUserId("1");
        try {
            traceId = DistributorPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectCashFlow] params: currencyCode={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode,  moneyFlow, beginDateString, endDateString);
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getPortalUserById(rpcHeader, portalUserInfo.getUserId());
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            FrontPageFlowSubtotal frontPageFlowSubtotal = null;
            //查询流水
            switch (accountType) {
                case 1:
                    //现金账户
                    frontPageFlowSubtotal = distributorAccountService.getCashSubtotal(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), moneyFlow, beginDate, endDate);
                    break;
                case 2:
                    //返利账户
                    frontPageFlowSubtotal = distributorCouponTransferService.getSubtotal(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), accountType, moneyFlow, beginDate, endDate);
                    break;
                case 3:
                    //代垫账户
                    frontPageFlowSubtotal = distributorAccountService.getPrepaidSubtotal(rpcHeader, currencyCode, distributorPortalUser.getDistributorId(), distributorPortalUser.getProjectId(), moneyFlow, beginDate, endDate);
                    break;
                default:
                    frontPageFlowSubtotal = null;
            }
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] get flow list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

}
