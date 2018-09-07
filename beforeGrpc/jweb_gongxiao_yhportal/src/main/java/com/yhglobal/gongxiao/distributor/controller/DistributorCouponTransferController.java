package com.yhglobal.gongxiao.distributor.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.base.config.PortalConfig;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.common.RpcResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.excel.ExcelUtil;
import com.yhglobal.gongxiao.foundation.distributor.model.Distributor;
import com.yhglobal.gongxiao.foundation.distributor.service.DistributorPortalUserService;
import com.yhglobal.gongxiao.foundation.portal.user.model.DistributorPortalUser;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.DistributorDetail;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.flow.service.DistributorCouponTransferService;
import com.yhglobal.gongxiao.payment.service.DistributorAccountService;
import com.yhglobal.gongxiao.purchase.controller.PurchaseOrderController;
import com.yhglobal.gongxiao.util.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户账户明细界面的controller
 * @author 王帅
 */

@Controller
@RequestMapping("/distributor/coupon")
public class DistributorCouponTransferController {

    private static Logger logger = LoggerFactory.getLogger(DistributorCouponTransferController.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

    @Reference
    DistributorCouponTransferService distributorCouponTransferService;

    @Reference
    DistributorAccountService distributorAccountService;

    @Reference
    DistributorPortalUserService distributorPortalUserService;

    @Reference
    ProjectService projectService;

    /**
     *客户明细即是前端查询流水 余额 等
     * */
    /**
     * 返利转入
     * @param distributorId    经销商ID
     * @param receivedAmount   实收金额
     * @param currencyCode     货币编码(不需传)
     * @param projectId        项目ID
     * @param remark           备注
     * */
    @RequestMapping(value = "/transfer")
    @ResponseBody
    public GongxiaoResult distributorCouponTansfer(long distributorId, Double receivedAmount,
                                            @RequestParam(defaultValue = "CNY") String currencyCode,
                                             Long projectId, String remark,HttpServletRequest request){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][generatePurchaseOrders] params: distributorId:{}, receivedAmount:{},currencyCode:{}," +
                            "projectId:{},projectName:{},distributorName:{},remark:{}",
                    traceId, distributorId,receivedAmount,currencyCode,projectId,remark);

            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            Project project = projectService.getByProjectId(rpcHeader, String.valueOf(projectId));
            long supplierId = project.getSupplierId();
            String supplierName = project.getSupplierName();
            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getByDistributorId(rpcHeader, String.valueOf(distributorId));
            String distributorName = distributorPortalUser.getDistributorName();
            // 数据校验 如果上账额度为负或0 数据异常
            if (receivedAmount <= 0){
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
            }else {
                RpcResult rpcResult = distributorCouponTransferService.singleDistributorCouponTransferReceived(rpcHeader, distributorId, Math.round(receivedAmount * FXConstant.HUNDRED), currencyCode, supplierId
                        , supplierName, projectId, distributorName, remark);
                gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), rpcResult);
                logger.info("#traceId={}# [OUT] distributor coupon transfer success.");
            }
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 根据项目ID和供应商ID查询返利流水
     * @param currencyCode   货币编码(不传)
     * @param distributorId  经销商ID
     * @param projectId      项目ID
     * @param moneyFlow      流水类型 收入306 支出305
     * @param beginDateString 开始时间
     * @param endDateString   结束时间
     * @param pageNumber        分页参数
     * @param pageSize       分页参数
     * */
    @RequestMapping("/flowList")
    @ResponseBody
    public GongxiaoResult selectCouponFlow(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                              Long distributorId,
                                             Long projectId,
                                             Integer moneyFlow,
                                             String beginDateString,
                                             String endDateString,
                                             @RequestParam(defaultValue = "1") int pageNumber,
                                             @RequestParam(defaultValue = "60") int pageSize
    ) {
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode,distributorId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            PageInfo<FrontPageFlow> list = distributorCouponTransferService.selectCouponFlow(rpcHeader,currencyCode,distributorId,projectId,moneyFlow,beginDate,endDate,pageNumber,pageSize);

             gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] select flow by projectid and distributor success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 客户明细页信息导出
     * */
    @RequestMapping("/export")
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam(defaultValue = "CNY") String currencyCode,
                                           Long distributorId,
                                           Long projectId,
                                           Integer moneyFlow,
                                           String beginDateString,
                                           String endDateString
    ) {
        if (projectId == null){
            throw new RuntimeException("the projectId is null");
        }

        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
                    traceId, currencyCode,distributorId, projectId, moneyFlow, beginDateString, endDateString);
            Date beginDate = null;
            if (beginDateString != null && !"".equals(beginDateString)) {
                beginDate = DateUtil.stringToDate(beginDateString, "yyyy-MM-dd");
            }
            Date endDate = null;
            if (endDateString != null && !"".equals(endDateString)) {
                endDate = DateUtil.stringToDate(endDateString, "yyyy-MM-dd");
            }
            List<FrontPageFlow> list = distributorCouponTransferService.selectCouponFlow(rpcHeader,currencyCode,distributorId,projectId,moneyFlow,beginDate,endDate);

            String downFileName = new String("经销商返利流水表表.xls");
            ExcelUtil<FrontPageFlow> util = new ExcelUtil<FrontPageFlow>(FrontPageFlow.class);
            Workbook workbook = util.getListToExcel(list,"流水列表");
            ExcelDownUtil.resetResponse(response,workbook,downFileName);
            logger.info("#traceId={}# [OUT] distributordetail export success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);

        }

    }
    /**
     * 查询账户余额
     * @param currencyCode    货币编码(不传)
     * @param distributorId   经销商ID(测试数据传2)
     * @param projectId       项目ID
     * */
    @RequestMapping("/accountAmount")
    @ResponseBody
    public GongxiaoResult getAccountAmount(HttpServletRequest request, HttpServletResponse response,
                                                     @RequestParam(defaultValue = "CNY") String currencyCode,
                                                     @RequestParam(defaultValue = "2") long distributorId,
                                                     Long projectId) {
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, supplierId={}, projectId={}",
                    traceId, currencyCode, distributorId, projectId);

            AccountAmount accountAmount = distributorAccountService.getDistributorAccountAmount(rpcHeader,currencyCode,projectId,distributorId);
            accountAmount.setCouponAmountDouble(accountAmount.getCouponAmount()!=0?accountAmount.getCouponAmount()/FXConstant.HUNDRED_DOUBLE:0.00);
            accountAmount.setPrepaidAmountDouble(accountAmount.getPrepaidAmount()/FXConstant.HUNDRED_DOUBLE);
            accountAmount.setCashAmountDouble(accountAmount.getCashAmount()/FXConstant.HUNDRED_DOUBLE);
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
     * @param currencyCode   货币编码(不传)
     * @param distributorId  经销商ID
     * @param projectId      项目ID
     * @param accountType    账户类型1
     * @param moneyFlow      流水类型 收入306 支出305
     * @param beginDateString 开始时间
     * @param endDateString   结束时间
     * */
    @RequestMapping("/subtotal")
    @ResponseBody
    public GongxiaoResult getSubtotal(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                            Long distributorId,
                                             Long projectId,
                                             int accountType,
                                             Integer moneyFlow,
                                             String beginDateString,
                                             String endDateString
    ) {
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params: currencyCode={}, supplierId={}, projectId={} , moneyFlow={}, beginDateString={}, endDateString={}",
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
            //查询总计返利账户
            frontPageFlowSubtotal = distributorCouponTransferService.getSubtotal(rpcHeader,currencyCode,distributorId,projectId,accountType,moneyFlow,beginDate,endDate);

           gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] get distributor subtotal success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 根据项目ID 客户账户 客户名称 查询经销商列表
     * @param projectId       项目ID
     * @param distributorId          客户账号
     * @param distributorName        客户名称
     * @param currencyCode    货币编码
     * */
    @RequestMapping("/distributorDatailList")
    @ResponseBody
    public GongxiaoResult getSupplierList(HttpServletRequest request, HttpServletResponse response,
                                      String distributorId,String distributorName,@RequestParam(defaultValue = "CNY") String currencyCode,
                                      Long projectId,  @RequestParam(defaultValue = "1") int pageNumber,
                                          @RequestParam(defaultValue = "50") int pageSize) {
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(),ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params:  projectId={} ,distributorId={}, distributorName={} ",
                    traceId,  projectId,distributorId,distributorName);
             // 查询出对应的经销商
             List<DistributorPortalUser> distributorPortalUserList = distributorPortalUserService.selectAllByProjectIdAndUserIdAndUserName(rpcHeader,projectId,distributorId,distributorName);
            // 获取账户额度等信息
            List<DistributorDetail> distributorDetailList = new ArrayList<>();
            for (DistributorPortalUser distributorPortalUser:distributorPortalUserList) {
                // 调用葛灿接口查询余额
                AccountAmount accountAmount = distributorAccountService.getDistributorAccountAmount(rpcHeader,currencyCode,projectId,distributorPortalUser.getDistributorId());
                // 根据项目ID 客户ID 查询项目表中返利代垫使用率
                Project project = projectService.getByProjectId(rpcHeader,String.valueOf(projectId));
                // 给distributorDetail赋值
                DistributorDetail distributorDetail = generate(accountAmount,project,distributorPortalUser);
                distributorDetailList.add(distributorDetail);
            }
            PageHelper.startPage(pageNumber, pageSize);
            PageInfo<DistributorDetail> list = new PageInfo<>(distributorDetailList);
            gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), list);
            logger.info("#traceId={}# [OUT] query distributor success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * 导出经销商列表
     * */
    @RequestMapping("/exportSupplierList")
    @ResponseBody
    public void getSupplierListToExport(HttpServletRequest request, HttpServletResponse response,
                                          String distributorId,String distributorName,@RequestParam(defaultValue = "CNY") String currencyCode,
                                          Long projectId) {
        if (projectId == null){
            throw new RuntimeException("the projectId is null");
        }
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params:  projectId={} ,distributorId={}, distributorName={} ",
                    traceId,  projectId,distributorId,distributorName);
            // 查询出对应的经销商
            List<DistributorPortalUser> distributorPortalUserList = distributorPortalUserService.selectAllByProjectIdAndUserIdAndUserName(rpcHeader,projectId,distributorId,distributorName);
            // 获取账户额度等信息
            List<DistributorDetail> distributorDetailList = new ArrayList<>();
            for (DistributorPortalUser distributorPortalUser:distributorPortalUserList) {
                // 调用葛灿接口查询余额
                AccountAmount accountAmount = distributorAccountService.getDistributorAccountAmount(rpcHeader,currencyCode,projectId,distributorPortalUser.getDistributorId());
                // 根据项目ID 客户ID 查询项目表中返利代垫使用率
                Project project = projectService.getByProjectId(rpcHeader,String.valueOf(projectId));
                // 给distributorDetail赋值
                DistributorDetail distributorDetail = generate(accountAmount,project,distributorPortalUser);

                distributorDetail.setCashAmountDoubleString(distributorDetail.getCashAmountDoubleString());
                distributorDetail.setCouponAmountDoubleString(distributorDetail.getCouponAmountDoubleString());
                distributorDetail.setPrepaidAmountDoubleString(distributorDetail.getPrepaidAmountDoubleString());
                distributorDetailList.add(distributorDetail);
            }
            String downFileName = new String("经销商信息列表.xls");
            ExcelUtil<DistributorDetail> util = new ExcelUtil<DistributorDetail>(DistributorDetail.class);
            Workbook workbook = util.getListToExcel(distributorDetailList,"经销商列表");
            ExcelDownUtil.resetResponse(response,workbook,downFileName);

            logger.info("#traceId={}# [OUT] query distributor to export success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }
    /**
     * 生成前端数据封装对象
     * */
    public DistributorDetail generate(AccountAmount accountAmount,Project project,DistributorPortalUser distributorPortalUser){
        DistributorDetail distributorDetail = new DistributorDetail();
        distributorDetail.setDistributorId(String.valueOf( distributorPortalUser.getDistributorId()));
        distributorDetail.setDistributorName(distributorPortalUser.getDistributorName());
        distributorDetail.setProjectId(distributorPortalUser.getProjectId());
        distributorDetail.setUserId(String.valueOf(distributorPortalUser.getUserId()));

        distributorDetail.setCashAmountDouble(accountAmount.getCashAmount()/FXConstant.HUNDRED_DOUBLE);
        distributorDetail.setCouponAmountDouble(accountAmount.getCouponAmount()/FXConstant.HUNDRED_DOUBLE);
        distributorDetail.setPrepaidAmountDouble(accountAmount.getPrepaidAmount()/FXConstant.HUNDRED_DOUBLE);
        String couponRate = String.valueOf(project.getCouponUseReferRate()/FXConstant.MILLION_DOUBLE)  +"%";
        String prepaidRate =  String.valueOf(project.getPrepaidUseReferRate()/FXConstant.MILLION_DOUBLE)  +"%";
        distributorDetail.setCouponUseableRate(couponRate);
        distributorDetail.setPrepaidUseableRate(prepaidRate);

        return distributorDetail;
    }



}
