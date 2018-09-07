package com.yhglobal.gongxiao.payment.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.flow.service.DistributorCouponTransferService;
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

    @Reference
    DistributorAccountService distributorAccountService;

    /**
     * 根据项目ID和供应商ID查询现金流水
     *
     * @param currencyCode    货币编码
     * @param distributorId   供应商id
     * @param projectId       项目id
     * @param moneyFlow       资金流向
     * @param beginDateString 查询开始时间
     * @param endDateString   查询截止时间
     * @param pageNum         页码
     * @param pageSize        条数
     * @return
     */
    @RequestMapping("/flowList")
    @ResponseBody
    public GongxiaoResult selectCashFlow(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "CNY") String currencyCode,
                                         @RequestParam(defaultValue = "2") long distributorId,
                                         long projectId,
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
            list = distributorAccountService.selectCashFlow(rpcHeader, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
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
                                                          @RequestParam(defaultValue = "2") long distributorId,
                                                          long projectId) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, supplierId={}, projectId={}",
                    traceId, currencyCode, distributorId, projectId);
            AccountAmount accountAmount = distributorAccountService.getDistributorAccountAmount(rpcHeader, currencyCode, projectId, distributorId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), accountAmount);
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
            //查询总计
            //现金账户
            frontPageFlowSubtotal = distributorAccountService.getCashSubtotal(rpcHeader, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] get subtotal success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


}
