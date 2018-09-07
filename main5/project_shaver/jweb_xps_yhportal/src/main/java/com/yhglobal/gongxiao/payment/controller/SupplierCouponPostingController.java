package com.yhglobal.gongxiao.payment.controller;

import com.github.pagehelper.PageInfo;

import com.yhglobal.gongxiao.accountmanage.microservice.SupplierCouponPostingServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.SupplierCouponPostingServiceStructure;
import com.yhglobal.gongxiao.base.model.AccountAmount;
import com.yhglobal.gongxiao.base.model.FrontPageFlow;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.common.GongxiaoResult;

import com.yhglobal.gongxiao.base.protal.PortalConfig;


import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.entity.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;



import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.*;
import com.yhglobal.gongxiao.utils.YhPortalTraceIdGenerator;

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


    /**
     * 供应商上账界面 点击转入按钮实现的上账功能入口
     * */
    @RequestMapping(value = "/posting")
    @ResponseBody
    public GongxiaoResult supplierCouponPosting(Long supplierId, String supplierName,
                                                @RequestParam(defaultValue = "CNY")String currencyCode, String remark,
                                                Double postingAmount, Long projectId, HttpServletRequest request ){
        if (projectId == null){
            return new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(),ARGUMENTS_INVALID.getMessage());
        }
        String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][supplierCouponPosting] params: supplierId:{}, supplierName:{}",
                    traceId, supplierId, supplierName);
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,  String.valueOf(portalUserInfo.getUserId()), portalUserInfo.getUserName());
            // 数据校验 如果上账额度为负或0 数据异常
            if (postingAmount <= 0 || postingAmount==null){
                gongxiaoResult = new GongxiaoResult(ARGUMENTS_INVALID.getErrorCode(), ARGUMENTS_INVALID.getMessage());
                return gongxiaoResult;
            }
            // supplierAccountService.postSupplierCouponAccount(rpcHeader, currencyCode, projectId, Math.round(postingAmount * HUNDRED), remark);
            SupplierAccountServiceStructure.PostSupplierCouponAccountRequest.Builder builder =  SupplierAccountServiceStructure.PostSupplierCouponAccountRequest.newBuilder();
            builder.setCurrencyCode(currencyCode);
            builder.setRemark(remark==null?"":remark);
            builder.setProjectId(projectId);
            builder.setAmount(Math.round(postingAmount * HUNDRED));
            builder.setRpcHeader(rpcHeader);

            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);

            GongxiaoRpc.RpcResult couponAccountResponse = blockingStub.postSupplierCouponAccount(builder.build());

            //gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), true);
            gongxiaoResult = new GongxiaoResult(couponAccountResponse.getReturnCode(),couponAccountResponse.getMsg());
            //TODO: 根据gongxiaoResult的returnCode来打印日记
            logger.info("#traceId={}# [OUT] supplier transportation posting success.");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
    return gongxiaoResult;
    }

    /**
     * 供应商上账界面 加载时显示供应商账户余额的功能入口
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
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={},  projectId={}",
                    traceId, currencyCode,  projectId);
            // 前台页面显示的账户余额数据
//            AccountAmount accountAmount = supplierAccountService.getSupplierAccountAmount(rpcHeader,currencyCode,projectId);
            SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.Builder builder = SupplierAccountServiceStructure.GetSupplierAccountAmountRequest.newBuilder();
            builder.setCurrencyCode(currencyCode);
            builder.setProjectId(projectId);
            builder.setRpcHeader(rpcHeader);

            SupplierAccountServiceStructure.GetSupplierAccountAmountRequest req = builder.build();
//            //2. 获取该gRpc对应的stub
//            SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub.class);
            SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierAccountServiceGrpc.SupplierAccountServiceBlockingStub.class);

            //3. 进行gRpc调用
            PaymentCommonGrpc.AccountAmountResponse accountAmountResponse = blockingStub.getSupplierAccountAmount(req);
            // 把接口返回的grpc对象转化为普通Java类
            AccountAmount accountAmount = new AccountAmount();
            accountAmount.setCashAmount(accountAmountResponse.getCashAmount());
            accountAmount.setCouponAmount(accountAmountResponse.getCouponAmount());
            accountAmount.setPrepaidAmount(accountAmountResponse.getPrepaidAmount());

            accountAmount.setCouponAmountDouble(accountAmount.getCouponAmount()/ FXConstant.HUNDRED_DOUBLE);
            accountAmount.setCashAmountDouble(accountAmount.getCashAmount()/ FXConstant.HUNDRED_DOUBLE);
            accountAmount.setPrepaidAmountDouble(accountAmount.getPrepaidAmount()/ FXConstant.HUNDRED_DOUBLE);

             gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), accountAmount);
            logger.info("#traceId={}# [OUT] get account amount success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 供应商上账界面 条件查询供应商账户流水的功能入口
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
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
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
           // PageInfo<FrontPageFlow> pageInfo = supplierCouponPostingService.selectAllBySupplierId(rpcHeader,currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
            //gongxiaoResult = new GongxiaoResult<>(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
           // SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.Builder reqBuilder = SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.newBuilder();
            SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq.Builder reqBuilder = SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq.newBuilder();

            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setCurrencyCode(currencyCode);
            reqBuilder.setSupplierId(supplierId);
            reqBuilder.setProjectId(projectId);
            if (moneyFlow == null){
                moneyFlow = -1;
            }
            reqBuilder.setMoneyFlow(moneyFlow);
            if (beginDate == null){
                reqBuilder.setBeginDate(-1);
            }else {
                long beginDateLong = beginDate.getTime();
                reqBuilder.setBeginDate(beginDateLong);
            }
            if (endDate == null){
                reqBuilder.setEndDate(-1);
            }else {
                long endDateLong = endDate.getTime();
                reqBuilder.setEndDate(endDateLong);
            }

            reqBuilder.setPageNumber(pageNum);
            reqBuilder.setPageSize(pageSize);
            SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq req = reqBuilder.build();
            //2. 获取该gRpc对应的stub
            // ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub.class);
            // ProjectServiceStructure.GetProjectByIdResp resp;
            PaymentCommonGrpc.FrontPageFlowPageInfo frontPageFlowPageInfo;
            //3. 进行gRpc调用
            frontPageFlowPageInfo = blockingStub.selectAllBySupplierIdHasPage(req);
            // 把返回的数据封装到返给前端的数据对象
            List<PaymentCommonGrpc.FrontPageFlow> list = frontPageFlowPageInfo.getFlowsList();

            List<FrontPageFlow> list1 = new ArrayList<>();
            // 查询流水
            for (PaymentCommonGrpc.FrontPageFlow frontPageFlow:list){
                FrontPageFlow frontPageFlow1 = new FrontPageFlow();
                frontPageFlow1.setFlowNo(frontPageFlow.getFlowNo());
                frontPageFlow1.setType(frontPageFlow.getType());
                //frontPageFlow1.setTypeStr(frontPageFlow.getTypeStr());
                frontPageFlow1.setCurrencyCode(frontPageFlow.getCurrencyCode());
                frontPageFlow1.setTransactionAmount(frontPageFlow.getTransactionAmount());
                //frontPageFlow1.setTransactionAmountStr(frontPageFlow.getTransactionAmountStr());
                frontPageFlow1.setAmountAfterTransaction(frontPageFlow.getAmountAfterTransaction());
                //frontPageFlow1.setAmountAfterTransactionStr(frontPageFlow.getAmountAfterTransactionStr());
                frontPageFlow1.setCreateTime(new Date(frontPageFlow.getCreateTime()));
                frontPageFlow1.setCreateByName(frontPageFlow.getCreateByName());
                //frontPageFlow1.setRemark(frontPageFlow.getRemark());
                list1.add(frontPageFlow1);

            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(list1);
            if (list.size()==0){
                return new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            }
            pageInfo.setTotal(frontPageFlowPageInfo.getTotal());
            pageInfo.setPageNum(frontPageFlowPageInfo.getPageNum());
            pageInfo.setPageSize(frontPageFlowPageInfo.getPageSize());

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);
            logger.info("#traceId={}# [OUT] select transportation flow success.");
        } catch (Exception e) {
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * "账户管理 >  供应商过账账户"页面上 点击"统计金额"时的响应入口
     * */
    @RequestMapping("/subtotal")
    @ResponseBody
    public GongxiaoResult getAccountSubtotal(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(defaultValue = "CNY") String currencyCode,
                                             @RequestParam(defaultValue = "1") long supplierId,
                                             Long projectId,
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
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,portalUserInfo.getUserId(), portalUserInfo.getUserName());
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
            Long beginDateLong = beginDate==null?-1:beginDate.getTime();
            Long endDateLong = endDate==null?-1:endDate.getTime();

            //查询总计  参数封装成req
            // frontPageFlowSubtotal = supplierCouponPostingService.generateFrontFlowSubtotal(rpcHeader,currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.Builder reqBuilder = SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.newBuilder();

            reqBuilder.setMoneyFlow(moneyFlow==null?-1:moneyFlow);
            reqBuilder.setCurrencyCode(currencyCode);
            reqBuilder.setSupplierId(supplierId);
            reqBuilder.setProjectId(projectId);
            reqBuilder.setBeginDate(beginDateLong);
            reqBuilder.setEndDate(endDateLong);

            SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq req = reqBuilder.build();
            //2. 获取该gRpc对应的stub
            // ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub.class);

            // ProjectServiceStructure.GetProjectByIdResp resp;
            SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalResp resp;

            //3. 进行gRpc调用
            resp = blockingStub.generateFrontFlowSubtotal(req);

            //Transformer trans = new Transformer();
            //FrontPageFlowSubtotal frontPageFlowSubtotal = trans.messageToJava(resp, FrontPageFlowSubtotal.class);
            // 把grpc对象转化为普通Java对象
            FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
            frontPageFlowSubtotal.setExpenditureAmount(resp.getExpenditureAmount());
            frontPageFlowSubtotal.setExpenditureQuantity(resp.getExpenditureQuantity());
            frontPageFlowSubtotal.setIncomeAmount(resp.getIncomeAmount());
            frontPageFlowSubtotal.setIncomeQuantity(resp.getIncomeQuantity());

            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
            logger.info("#traceId={}# [OUT] get transportation subtotal success.");
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
    public void selectCouponFlowsToExport(HttpServletRequest request, HttpServletResponse response,
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
        GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
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
//            List<FrontPageFlow> frontPageFlowList = supplierCouponPostingService.selectAllBySupplierId(rpcHeader,currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);

            SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq.Builder reqBuilder = SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq.newBuilder();

            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setCurrencyCode(currencyCode);
            reqBuilder.setSupplierId(supplierId);
            reqBuilder.setProjectId(projectId);
            if (moneyFlow == null){
                moneyFlow = -1;
            }
            reqBuilder.setMoneyFlow(moneyFlow);
            long beginDateLong = beginDate==null?-1:beginDate.getTime();
            reqBuilder.setBeginDate(beginDateLong);
            long endDateLong = endDate==null?-1:endDate.getTime();
            reqBuilder.setEndDate(endDateLong);
            SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageReq req = reqBuilder.build();
            //2. 获取该gRpc对应的stub
            // ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub.class);

            //3. 进行gRpc调用
            SupplierCouponPostingServiceStructure.SelectAllBySupplierIdNoPageResp resp = blockingStub.selectAllBySupplierIdNoPage(req);
            // 把返回的数据封装到返给前端的数据对象
            List<SupplierCouponPostingServiceStructure.Result> list = resp.getResultList();

            if (list.size() == 0){
                // 查询数据为空则不进行转换 直接返回
                return;
            }
            List<FrontPageFlow> list1 = new ArrayList<>();
            for (SupplierCouponPostingServiceStructure.Result result:list){
                FrontPageFlow frontPageFlow1 = new FrontPageFlow();
                frontPageFlow1.setFlowNo(result.getFlowNo());
                frontPageFlow1.setType(result.getType());
                // frontPageFlow1.setTypeStr(result.getTypeStr());
                frontPageFlow1.setCurrencyCode(result.getCurrencyCode());
                frontPageFlow1.setTransactionAmount(result.getTransactionAmount());
                // frontPageFlow1.setTransactionAmountStr(result.getTransactionAmountStr());
                frontPageFlow1.setAmountAfterTransaction(result.getAmountAfterTransaction());
                // frontPageFlow1.setAmountAfterTransactionStr(result.getAmountAfterTransactionStr());
                frontPageFlow1.setCreateTime(new Date(result.getCreateTime()));
                frontPageFlow1.setCreateByName(result.getCreateByName());
                frontPageFlow1.setRemark(result.getRemark());
                list1.add(frontPageFlow1);

            }
            for (FrontPageFlow frontPageFlow:list1){
                frontPageFlow.setTypeStr(frontPageFlow.getTypeStr());
                frontPageFlow.setTransactionAmountStr(frontPageFlow.getTransactionAmountStr());
                frontPageFlow.setAmountAfterTransactionStr(frontPageFlow.getAmountAfterTransactionStr());
            }
            ExcelUtil<FrontPageFlow> util = new ExcelUtil<FrontPageFlow>(FrontPageFlow.class);
            Workbook workbook = util.getListToExcel(list1,"流水列表");
            ExcelDownUtil.resetResponse(response,workbook,downFileName);
            logger.info("#traceId={}# [selectcouponflow to export success][OUT]");
        }catch (Exception e){
            logger.error("#traceId=" + traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }

}
