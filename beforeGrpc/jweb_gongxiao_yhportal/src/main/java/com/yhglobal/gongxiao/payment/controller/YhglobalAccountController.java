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
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.model.DistributorCouponAccount;
import com.yhglobal.gongxiao.payment.model.DistributorPrepaidAccount;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalAccountService;
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
 * YH账户管理
 * 页面位于账户管理-越海账户管理
 *
 * @author: 葛灿
 */
@Controller
@RequestMapping("/payment/yhglobal")
public class YhglobalAccountController {

    private static Logger logger = LoggerFactory.getLogger(CashConfirmController.class);
    @Autowired
    private PortalConfig portalConfig;
    @Autowired
    PortalUserInfo portalUserInfo;  //处于登陆状态时 用户信息会注入到这个对象
    @Reference
    SupplierAccountService supplierAccountService;
    @Reference
    YhglobalAccountService yhglobalAccountService;
    @Reference
    DistributorAccountService distributorAccountService;

    /**
     * 查询实收账户余额
     *
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/receivedAccount")
    @ResponseBody
    public GongxiaoResult getYhglobalReceivedAccount(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(defaultValue = "CNY") String currencyCode,
                                                     @RequestParam(defaultValue = "1") long supplierId,
                                                     long projectId) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, supplierId={}, projectId={}",
                    traceId, currencyCode, supplierId, projectId);
            AccountAmount yhglobalReceivedAccountAmount = yhglobalAccountService.getYhglobalReceivedAccountAmount(rpcHeader, currencyCode, projectId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), yhglobalReceivedAccountAmount);
            logger.info("#traceId={}# [OUT] get account amount success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 查询ad过账账户余额
     *
     * @param currencyCode 货币编码
     * @param supplierId   供应商id
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/bufferAccount")
    @ResponseBody
    public GongxiaoResult getDistributorBufferAccount(HttpServletRequest request, HttpServletResponse response,
                                                      @RequestParam(defaultValue = "CNY") String currencyCode,
                                                      @RequestParam(defaultValue = "1") long supplierId,
                                                      long projectId) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getDistributorBufferAccount] params: currencyCode={}, supplierId={}, projectId={}",
                    traceId, currencyCode, supplierId, projectId);
            AccountAmount yhglobalReceivedAccountAmount = supplierAccountService.getSupplierCouponBufferToDistributor(rpcHeader, currencyCode, projectId);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), yhglobalReceivedAccountAmount);
            logger.info("#traceId={}# [OUT] get account amount success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 查询返利账户list
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/couponAccountList")
    @ResponseBody
    public GongxiaoResult getCouponAccountList(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(defaultValue = "CNY") String currencyCode,
                                               long projectId,
                                               @RequestParam(defaultValue = "1") int pageNumber,
                                               @RequestParam(defaultValue = "5") int pageSize

    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getCouponAccountList] params: currencyCode={}, projectId={} ",
                    traceId, currencyCode, projectId);
            PageInfo<DistributorCouponAccount> distributorCouponAccounts = distributorAccountService.selectDistributorCouponAccountList(rpcHeader, currencyCode, projectId, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), distributorCouponAccounts);
            logger.info("#traceId={}# [OUT] get account list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 查询返利账户list
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/prepaidAccountList")
    @ResponseBody
    public GongxiaoResult getPrepaidAccountList(HttpServletRequest request, HttpServletResponse response,
                                                @RequestParam(defaultValue = "CNY") String currencyCode,
                                                long projectId,
                                                @RequestParam(defaultValue = "1") int pageNumber,
                                                @RequestParam(defaultValue = "5") int pageSize

    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getPrepaidAccountList] params: currencyCode={}, projectId={} ",
                    traceId, currencyCode, projectId);
            PageInfo<DistributorPrepaidAccount> distributorPrepaidAccounts = distributorAccountService.selectDistributorPrepaidAccountList(rpcHeader, currencyCode, projectId, pageNumber, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), distributorPrepaidAccounts);
            logger.info("#traceId={}# [OUT] get account list success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 发放返利
     *
     * @param accounts 分配详情
     * @return
     */
    @RequestMapping("/transCoupon")
    @ResponseBody
    public GongxiaoResult transCoupon(HttpServletRequest request, HttpServletResponse response,
                                      String accounts

    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][transCoupon] params: distributorCouponAccounts={}",
                    traceId, accounts);
            List<DistributorCouponAccount> distributorCouponAccounts = JSON.parseArray(accounts, DistributorCouponAccount.class);
            distributorAccountService.depositCouponReceivedAccounts(rpcHeader, distributorCouponAccounts);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
            logger.info("#traceId={}# [OUT] trans coupon success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 发放代垫
     *
     * @param accounts 分配详情
     * @return
     */
    @RequestMapping("/transPrepaid")
    @ResponseBody
    public GongxiaoResult transPrepaid(HttpServletRequest request, HttpServletResponse response,
                                       String accounts

    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][transPrepaid] params: distributorPrepaidAccounts={}",
                    traceId, accounts);
            List<DistributorPrepaidAccount> distributorPrepaidAccounts = JSON.parseArray(accounts, DistributorPrepaidAccount.class);
            distributorAccountService.depositPrepaidReceivedAccounts(rpcHeader, distributorPrepaidAccounts);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), null);
            logger.info("#traceId={}# [OUT] trans prepaid success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 按类型查询账户记录
     * <p>
     * accountType 对应账户类型
     * 查询流水的账户类型 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
     *
     * @param currencyCode    货币编码
     * @param supplierId      供应商id
     * @param projectId       项目id
     * @param accountType     查询流水的账户类型 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
     * @param moneyFlow       资金流向
     * @param beginDateString 日期开始
     * @param endDateString   日期结束
     * @return
     */
    @RequestMapping("/flowList")
    @ResponseBody
    public GongxiaoResult selectFlowList(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(defaultValue = "CNY") String currencyCode,
                                         @RequestParam(defaultValue = "1") long supplierId,
                                         int accountType,
                                         long projectId,
                                         Integer moneyFlow,
                                         String beginDateString,
                                         String endDateString,
                                         @RequestParam(defaultValue = "1") int pageNumber,
                                         @RequestParam(defaultValue = "50") int pageSize
    ) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][selectFlowList] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, supplierId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            PageInfo<FrontPageFlow> list;
            //查询流水 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
            switch (accountType) {
                case 1:
                    //返利实收账户
                    list = yhglobalAccountService.selectCouponReceivedRecords(rpcHeader, currencyCode, projectId, moneyFlow, beginDate, endDate, pageNumber, pageSize);
                    break;
                case 2:
                    //代垫实收账户
                    list = yhglobalAccountService.selectPrepaidReceivedRecords(rpcHeader, currencyCode, projectId, moneyFlow, beginDate, endDate, pageNumber, pageSize);
                    break;
                case 3:
                    //返利过账账户
                    list = supplierAccountService.selectBufferCouponFlowList(rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNumber, pageSize);
                    break;
                case 4:
                    //代垫过账账户
                    list = supplierAccountService.selectBufferPrepaidFlowList(rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNumber, pageSize);
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
     * 查询返利实收账户记录
     * <p>
     * accountType 对应账户类型
     * 查询小计的账户类型 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
     *
     * @param currencyCode    货币编码
     * @param supplierId      供应商id
     * @param projectId       项目id
     * @param accountType     查询流水的账户类型 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
     * @param moneyFlow       资金流向
     * @param beginDateString 日期开始
     * @param endDateString   日期结束
     * @return
     */
    @RequestMapping("/flowSubtotal")
    @ResponseBody
    public GongxiaoResult getFlowSubtotal(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(defaultValue = "CNY") String currencyCode,
                                          @RequestParam(defaultValue = "1") long supplierId,
                                          int accountType,
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
            logger.info("#traceId={}# [IN][getFlowSubtotal] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, supplierId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            FrontPageFlowSubtotal couponSubtotal = null;
            //查询流水 1：返利实收账户 2：代垫实收账户 3：返利过账账户 4：代垫过账账户
            switch (accountType) {
                case 1:
                    //返利实收账户
                    couponSubtotal = yhglobalAccountService.getCouponSubtotal(rpcHeader, currencyCode, projectId, moneyFlow, beginDate, endDate);
                    break;
                case 2:
                    //代垫实收账户
                    couponSubtotal = yhglobalAccountService.getPrepaidSubtotal(rpcHeader, currencyCode, projectId, moneyFlow, beginDate, endDate);
                    break;
                case 3:
                    //返利过账账户
                    couponSubtotal = supplierAccountService.getCouponBufferToDistributorSubtotal(rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                    break;
                case 4:
                    //代垫过账账户
                    couponSubtotal = supplierAccountService.getPrepaidBufferToDistributorSubtotal(rpcHeader, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
                    break;
                default:
                    couponSubtotal = null;
            }
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), couponSubtotal);
            logger.info("#traceId={}# [OUT] get flow subtotal success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 实收账户转出返利
     * <p>
     *
     * @param projectId 项目id
     * @param amount    转出金额
     * @return
     */
    @RequestMapping("/transReceivedCoupon")
    @ResponseBody
    public GongxiaoResult transferCoupon(HttpServletRequest request, HttpServletResponse response,
                                         long projectId, double amount,
                                         @RequestParam(defaultValue = "")String remark) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][transferCoupon] params: projectId={}, amount={}",
                    traceId, projectId, amount);
            RpcResult rpcResult = yhglobalAccountService.transferYhglobalReceivedCoupon(rpcHeader, projectId, amount,remark);
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 实收账户转出返利
     * <p>
     *
     * @param projectId 项目id
     * @param amount    转出金额
     * @return
     */
    @RequestMapping("/transReceivedPrepaid")
    @ResponseBody
    public GongxiaoResult transferPrepaid(HttpServletRequest request, HttpServletResponse response,
                                          long projectId, double amount,
                                          @RequestParam(defaultValue = "")String remark) {
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][transferPrepaid] params: projectId={}, amount={}",
                    traceId, projectId, amount);
            RpcResult rpcResult = yhglobalAccountService.transferYhglobalReceivedPrepaid(rpcHeader, projectId, amount,remark);
            gongxiaoResult = new GongxiaoResult(rpcResult.getCode(), rpcResult.getMessage());
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
