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
import com.yhglobal.gongxiao.excel.ExcelUtil;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.record.SupplierCouponPostingService;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.ExcelDownUtil;
import com.yhglobal.gongxiao.util.GongxiaoResultUtil;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.util.YhPortalTraceIdGenerator;
import org.apache.poi.ss.usermodel.Workbook;
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
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;


/**
 *  供应商上账的接口
 * @author 王帅
 */
@Controller
@RequestMapping("/supplier/coupon")
public class SupplierCouponPostingController {


    private static Logger logger = LoggerFactory.getLogger(SupplierCouponPostingController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类


    @Reference
    SupplierAccountService supplierAccountService;

    @Reference
    SupplierCouponPostingService supplierCouponPostingService;

    /**
     * 实现上账
     * */
    @RequestMapping(value = "/posting")
    @ResponseBody
    public GongxiaoResult supplierCouponPosting(Long supplierId, String supplierName,
                                         @RequestParam(defaultValue = "CNY")String currencyCode,String remark,
                                     Double postingAmount,Long projectId, HttpServletRequest request ){
        if (projectId == null){
            return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(),ARGUMENTS_INVALID.getMessage());
        }
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;

        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][supplierCouponPosting] params: supplierId:{}, supplierName:{}",
                    traceId, supplierId, supplierName);
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            // 数据校验 如果上账额度为负或0 数据异常
            if (postingAmount <= 0){
                gongxiaoResult = new GongxiaoResult<>(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
            }else {
                supplierAccountService.postSupplierCouponAccount(rpcHeader, currencyCode, projectId, Math.round(postingAmount * HUNDRED), remark);

                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), true);
                logger.info("#traceId={}# [OUT] supplier coupon posting success.");
            }
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    return gongxiaoResult;
    }

    /**
     * 查询供应商账户余额
     *
     * @param currencyCode 货币编码
     * @param projectId    项目id
     * @return
     */
    @RequestMapping("/accountAccount")
    @ResponseBody
    public GongxiaoResult getYhglobalReceivedAccount(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(defaultValue = "CNY") String currencyCode,
                                                     Long projectId) {
        if (projectId == null){
            return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(),ARGUMENTS_INVALID.getMessage());
        }

        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={},  projectId={}",
                    traceId, currencyCode,  projectId);
            // 前台页面显示的账户余额数据
            AccountAmount accountAmount = supplierAccountService.getSupplierAccountAmount(rpcHeader,currencyCode,projectId);
            accountAmount.setCouponAmountDouble(accountAmount.getCouponAmount()/FXConstant.HUNDRED_DOUBLE);
            accountAmount.setCashAmountDouble(accountAmount.getCashAmount()/FXConstant.HUNDRED_DOUBLE);
            accountAmount.setPrepaidAmountDouble(accountAmount.getPrepaidAmount()/FXConstant.HUNDRED_DOUBLE);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), accountAmount);
            logger.info("#traceId={}# [OUT] get account amount success.");
        } catch (Exception e) {
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
    @RequestMapping("/supplierCouponFlow")
    @ResponseBody
    public GongxiaoResult selectCouponFlows(HttpServletRequest request,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                             @RequestParam(defaultValue = "1") long supplierId,
                                             Long projectId,
                                             Integer moneyFlow,
                                             String beginDateString,
                                             String endDateString,
                                             @RequestParam(defaultValue = "1") int pageNum,
                                             @RequestParam(defaultValue = "50") int pageSize
    ) {
        if (projectId == null){
            return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(),ARGUMENTS_INVALID.getMessage());
        }
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
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            // 查询流水
            PageInfo<FrontPageFlow> pageInfo = supplierCouponPostingService.selectAllBySupplierId(rpcHeader,currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            logger.info("#traceId={}# [OUT] select coupon flow success.");
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
                                            Long projectId,
                                            int accountType,
                                            Integer moneyFlow,
                                            String beginDateString,
                                            String endDateString){
        if (projectId == null){
            return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(),ARGUMENTS_INVALID.getMessage());
        }

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
            //查询总计
            frontPageFlowSubtotal = supplierCouponPostingService.generateFrontFlowSubtotal(rpcHeader,currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] get coupon subtotal success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 实现查询流水的导出
     * */
    @RequestMapping("/export")
    @ResponseBody
    public void selectCouponFlowsToExport(HttpServletRequest request,HttpServletResponse response,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                            @RequestParam(defaultValue = "1") long supplierId,
                                             Long projectId,
                                             Integer moneyFlow,
                                             String beginDateString,
                                             String endDateString
    ){
        if (projectId == null){
            throw new RuntimeException("the projectId is null");
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String traceId = null;
        traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
        RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
        logger.info("#traceId={}# [IN][selectCouponFlows to export] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                traceId, currencyCode, supplierId, projectId, moneyFlow, beginDateString, endDateString);
        Date beginDate = null;
        if (beginDateString != null && !"".equals(beginDateString)) {
            beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
        }
        Date endDate = null;
        if (endDateString != null && !"".equals(endDateString)) {
            endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
        }
        String downFileName = new String("供应商返利流水列表.xls");
        try {
            List<FrontPageFlow> frontPageFlowList = supplierCouponPostingService.selectAllBySupplierId(rpcHeader,currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

            for (FrontPageFlow frontPageFlow:frontPageFlowList){
                frontPageFlow.setTypeStr(frontPageFlow.getTypeStr());
                frontPageFlow.setTransactionAmountStr(frontPageFlow.getTransactionAmountStr());
                frontPageFlow.setAmountAfterTransactionStr(frontPageFlow.getAmountAfterTransactionStr());
            }
            ExcelUtil<FrontPageFlow> util = new ExcelUtil<FrontPageFlow>(FrontPageFlow.class);
            Workbook workbook = util.getListToExcel(frontPageFlowList,"流水列表");
            ExcelDownUtil.resetResponse(response,workbook,downFileName);
            logger.info("#traceId={}# [selectcouponflow to export success][OUT]");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }

}
