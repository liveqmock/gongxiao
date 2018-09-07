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
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
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
    @Reference
    SupplierAccountService supplierAccountService;


    private static Logger logger = LoggerFactory.getLogger(SupplierPrepaidController.class);
    /**
     * 实现上账
     * */
    @RequestMapping(value = "/posting")
    @ResponseBody
    public GongxiaoResult supplierCouponPosting(Long supplierId, String supplierName,
                                                @RequestParam(defaultValue = "CNY")String currencyCode, String remark,
                                                Double postingAmount, long projectId, HttpServletRequest request ){
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][supplierCouponPosting] params: supplierId:{}, supplierName:{}",
                    traceId, supplierId, supplierName);
            if(postingAmount == null || postingAmount == 0){
                return GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.ARGUMENTS_INVALID);
            }
            Long postingAmountLong = Math.round(postingAmount * FXConstant.HUNDRED);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            supplierAccountService.postSupplierPrepaidAccount(rpcHeader, currencyCode, projectId, postingAmountLong, remark);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), true);
            logger.info("#traceId={}# [OUT] supplier coupon posting success.");
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
            logger.info("#traceId={}# [IN][selectPrepaidFlows] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, supplierId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            try{
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }catch(Exception e){}
            // 查询流水
            PageInfo<FrontPageFlow> pageInfo = supplierAccountService.selectPrepaidBySupplierId(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            logger.info("#traceId={}# [OUT] post prepaid success.");
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
                                             long projectId,
                                             Integer moneyFlow,
                                             String beginDateString,
                                             String endDateString){

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode, supplierId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            FrontPageFlowSubtotal frontPageFlowSubtotal;
            frontPageFlowSubtotal = supplierAccountService.selectIncomeAndExpenditure(currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] post prepaid success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
}
