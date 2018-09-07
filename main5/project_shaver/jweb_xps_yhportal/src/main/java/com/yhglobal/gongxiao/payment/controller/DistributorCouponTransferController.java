package com.yhglobal.gongxiao.payment.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


import com.yhglobal.gongxiao.accountmanage.microservice.DistributorCouponTransferServiceGrpc;
import com.yhglobal.gongxiao.accountmanage.microservice.DistributorCouponTransferServiceStructure;

import com.yhglobal.gongxiao.base.model.AccountAmount;

import com.yhglobal.gongxiao.base.model.DistributorDetail;
import com.yhglobal.gongxiao.base.model.FrontPageFlow;
import com.yhglobal.gongxiao.base.model.PortalUserInfo;
import com.yhglobal.gongxiao.base.protal.PortalConfig;
import com.yhglobal.gongxiao.common.GongxiaoResult;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.TerminalCode;


import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorServiceGrpc;
import com.yhglobal.gongxiao.foundation.distributor.microservice.DistributorStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import com.yhglobal.gongxiao.payment.entity.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;

import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import com.yhglobal.gongxiao.utils.*;
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

    private static DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub blockingStubStatic = RpcStubStore.getRpcStub(DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub.class);

    @Autowired
    PortalConfig portalConfig; //property注入类

    @Autowired
    PortalUserInfo portalUserInfo; //用户信息注入类

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
                                                   Long projectId, String remark, HttpServletRequest request){
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
      String traceId = null;
        GongxiaoResult gongxiaoResult = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            logger.info("#traceId={}# [IN][generatePurchaseOrders] params: distributorId:{}, receivedAmount:{},currencyCode:{}," +
                            "projectId:{},projectName:{},distributorName:{},remark:{}",
                    traceId, distributorId,receivedAmount,currencyCode,projectId,remark);

            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,portalUserInfo.getUserId(), portalUserInfo.getUserName());
            // 调用project的grpc
//            Project project = projectService.getByProjectId(rpcHeader, String.valueOf(projectId));
            ProjectStructure.GetByProjectIdReq.Builder builder = ProjectStructure.GetByProjectIdReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setProjectId(projectId+"");
            ProjectStructure.GetByProjectIdReq req = builder.build();
            ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdResp resp = blockingStub.getByProjectId(req);

            long supplierId = resp.getProject().getSupplierId();
            String supplierName = resp.getProject().getSupplierName();
            // 调用经销商账户的查询接口 getDistributorBusinessById 使用该方法获取 distributorname
            DistributorStructure.GetDistributorBusinessByIdReq.Builder builder1 = DistributorStructure.GetDistributorBusinessByIdReq.newBuilder();
            builder1.setRpcHeader(rpcHeader);
            builder1.setDistributorBusinessId(distributorId);
            DistributorStructure.GetDistributorBusinessByIdReq req1 = builder1.build();
            DistributorServiceGrpc.DistributorServiceBlockingStub blockingStub1 = RpcStubStore.getRpcStub( DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.GetDistributorBusinessByIdResp resp1 = blockingStub1.getDistributorBusinessById(req1);
//            DistributorPortalUser distributorPortalUser = distributorPortalUserService.getByDistributorId(rpcHeader, String.valueOf(distributorId));
            String distributorName = resp1.getDistributorBusiness().getDistributorName();
            // 数据校验 如果上账额度为负或0 数据异常
            if (receivedAmount <= 0){
                gongxiaoResult = new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
            }else {
                // 调用返利转入的接口
//                RpcResult rpcResult = distributorCouponTransferService.singleDistributorCouponTransferReceived(rpcHeader, distributorId, Math.round(receivedAmount * FXConstant.HUNDRED), currencyCode, supplierId
//                        , supplierName, projectId, distributorName, remark);
//                DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq.Builder builder2 = DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq.newBuilder();
//                builder2.setRpcHeader(rpcHeader);
//                builder2.setDistributorId(distributorId);
//                builder2.setReceivedAmount(Math.round(receivedAmount * FXConstant.HUNDRED));
//                builder2.setCurrencyCode(currencyCode);
//                builder2.setSupplierId(supplierId);
//                builder2.setSupplierName(supplierName);
//                builder2.setProjectId(projectId);
//                builder2.setDistributorName(distributorName);
//                builder2.setRemark(remark);
//                DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedReq req2 = builder2.build();
//                //DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub blockingStub2 = RpcStubStore.getRpcStub(DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub.class);
//                DistributorCouponTransferServiceStructure.SingleDistributorCouponTransferReceivedResp resp2 = blockingStubStatic.singleDistributorCouponTransferReceived(req2);
//                gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), resp2.getMsg());


                Date receivedDate = new Date();
                Long receivedAmountLong = Math.round(receivedAmount * FXConstant.HUNDRED);
                // 使用grpc调葛灿的经销商账户接口
                // DistributorAccountServiceStructure.DepositCouponReceivedResponse.Builder respBuilder = DistributorAccountServiceStructure.DepositCouponReceivedResponse.newBuilder();
                DistributorAccountServiceStructure.DepositCouponReceivedRequest.Builder reqBuilder = DistributorAccountServiceStructure.DepositCouponReceivedRequest.newBuilder();
                reqBuilder.setRpcHeader(rpcHeader);
                reqBuilder.setCurrencyCode(currencyCode);
                reqBuilder.setDistributorId(distributorId);
                reqBuilder.setProjectId(projectId);
                reqBuilder.setTransactionTime(receivedDate.getTime());
                reqBuilder.setRemark(remark);
                reqBuilder.setAmount(receivedAmountLong);
                DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub blockingStub2 = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                GongxiaoRpc.RpcResult response = blockingStub2.depositCouponReceived(reqBuilder.build());

                gongxiaoResult = new GongxiaoResult(response.getReturnCode(), response.getMsg());
                logger.info("#traceId={}# [OUT] distributor transportation transfer success.");
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
                                           @RequestParam(defaultValue = "-1")Integer moneyFlow,
                                           String beginDateString,
                                           String endDateString,
                                           @RequestParam(defaultValue = "1") int pageNumber,
                                           @RequestParam(defaultValue = "60") int pageSize
    ) {
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
       GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
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
            // PageInfo<FrontPageFlow> list = distributorCouponTransferService.selectCouponFlow(rpcHeader,currencyCode,distributorId,projectId,moneyFlow,beginDate,endDate,pageNumber,pageSize);
            // SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq.Builder reqBuilder = SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq.newBuilder();
            DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq.Builder reqBuilder = DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq.newBuilder();

            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setCurrencyCode(currencyCode);
            reqBuilder.setProjectId(projectId);
//            if (moneyFlow == null){
//                moneyFlow = -1;
//            }
            reqBuilder.setMoneyFlow(moneyFlow);
            long beginDateLong = beginDate==null?-1:beginDate.getTime();
            reqBuilder.setBeginDate(beginDateLong);
            long endDateLong = endDate==null?-1:endDate.getTime();
            reqBuilder.setEndDate(endDateLong);
            reqBuilder.setPageNumber(pageNumber);
            reqBuilder.setPageSize(pageSize);
            reqBuilder.setSupplierId(distributorId==null?-1:distributorId);// 此处grpc内定义的字段错误应为distributorId
            // SupplierCouponPostingServiceStructure.SelectAllBySupplierIdHasPageReq req = reqBuilder.build();
            DistributorCouponTransferServiceStructure.SelectCouponFlowHasPageReq req  = reqBuilder.build();

            //2. 获取该gRpc对应的stub 进行gRpc调用
            DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub blockingStubStaticTest = RpcStubStore.getRpcStub(DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub.class);
            PaymentCommonGrpc.FrontPageFlowPageInfo frontPageFlowPageInfo = blockingStubStaticTest.selectCouponFlowHasPage(req);
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
            pageInfo.setTotal(frontPageFlowPageInfo.getTotal());
            pageInfo.setPageNum(frontPageFlowPageInfo.getPageNum());
            pageInfo.setPageSize(frontPageFlowPageInfo.getPageSize());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), pageInfo);

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
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,portalUserInfo.getUserId(), portalUserInfo.getUserName());
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
            // List<FrontPageFlow> list = distributorCouponTransferService.selectCouponFlow(rpcHeader,currencyCode,distributorId,projectId,moneyFlow,beginDate,endDate);

            DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq.Builder builder = DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setCurrencyCode(currencyCode);
            builder.setSupplierId(distributorId);// 此处proto文件字段写错 应为distributor
            builder.setProjectId(projectId);
            if (moneyFlow == null){
                moneyFlow = -1;
            }
            builder.setMoneyFlow(moneyFlow);
            if (beginDate != null) {
                builder.setBeginDate(beginDate.getTime());
            }
            if (endDate != null){
                builder.setEndDate(endDate.getTime());
            }
            DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageReq req = builder.build();
            //DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub.class);
            DistributorCouponTransferServiceStructure.SelectCouponFlowNoPageResp resp = blockingStubStatic.selectCouponFlowNoPage(req);
            // 把proto数据封装给前端对象
            List<DistributorCouponTransferServiceStructure.ResultResp> respList = resp.getResultList();
            List<FrontPageFlow> list = new ArrayList<>();
            for (DistributorCouponTransferServiceStructure.ResultResp result:respList){
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
                list.add(frontPageFlow1);
            }
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
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][getYhglobalReceivedAccount] params: currencyCode={}, supplierId={}, projectId={}",
                    traceId, currencyCode, distributorId, projectId);

            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.Builder builder = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder();
            builder.setRpcHeader(rpcHeader);
            builder.setCurrencyCode(currencyCode);
            builder.setProjectId(projectId);
            builder.setDistributorId(distributorId);
            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest req = builder.build();
            DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
            PaymentCommonGrpc.AccountAmountResponse accountAmountResponse = blockingStub.getDistributorAccountAmount(req);
            //Transformer trans = new Transformer();
            //AccountAmount accountAmount = trans.messageToJava(accountAmountResponse, AccountAmount.class);
            AccountAmount accountAmount = new AccountAmount();
            accountAmount.setCashAmount(accountAmountResponse.getCashAmount());
            accountAmount.setCouponAmount(accountAmountResponse.getCouponAmount());
            accountAmount.setPrepaidAmount(accountAmountResponse.getPrepaidAmount());

            accountAmount.setCouponAmountDouble(accountAmount.getCouponAmount()!=0?accountAmount.getCouponAmount()/ FXConstant.HUNDRED_DOUBLE:0.00);
            accountAmount.setPrepaidAmountDouble(accountAmount.getPrepaidAmount()/ FXConstant.HUNDRED_DOUBLE);
            accountAmount.setCashAmountDouble(accountAmount.getCashAmount()/ FXConstant.HUNDRED_DOUBLE);
             gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), accountAmount);
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
     * @param moneyFlow      流水类型 收入306 支出305
     * @param beginDateString 开始时间
     * @param endDateString   结束时间
     * */
    @RequestMapping("/subtotal")
    @ResponseBody
    public GongxiaoResult getSubtotal(HttpServletRequest request,
                                      @RequestParam(defaultValue = "CNY") String currencyCode,
                                      Long distributorId,
                                      Long projectId,
                                      Integer moneyFlow,
                                      String beginDateString,
                                      String endDateString
    ) {
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId,portalUserInfo.getUserId(), portalUserInfo.getUserName());
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
            // FrontPageFlowSubtotal frontPageFlowSubtotal;
            //查询总计返利账户
            // frontPageFlowSubtotal = distributorCouponTransferService.getSubtotal(rpcHeader,currencyCode,distributorId,projectId,accountType,moneyFlow,beginDate,endDate);
            // SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.Builder reqBuilder = SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq.newBuilder();
            DistributorCouponTransferServiceStructure.GetSubtotalReq.Builder reqBuilder = DistributorCouponTransferServiceStructure.GetSubtotalReq.newBuilder();
            reqBuilder.setRpcHeader(rpcHeader);
            reqBuilder.setMoneyFlow(moneyFlow==null?-1:moneyFlow);
            reqBuilder.setCurrencyCode(currencyCode);
            reqBuilder.setSupplierId(distributorId==null?-1:distributorId);
            reqBuilder.setProjectId(projectId);
            reqBuilder.setBeginDate(beginDate==null?-1:beginDate.getTime());
            reqBuilder.setEndDate(endDate==null?-1:endDate.getTime());
            // SupplierCouponPostingServiceStructure.GenerateFrontFlowSubtotalReq req = reqBuilder.build();
            DistributorCouponTransferServiceStructure.GetSubtotalReq req = reqBuilder.build();
            //2. 获取该gRpc对应的stub
            // SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(SupplierCouponPostingServiceGrpc.SupplierCouponPostingServiceBlockingStub.class);
            //DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(DistributorCouponTransferServiceGrpc.DistributorCouponTransferServiceBlockingStub.class);

            DistributorCouponTransferServiceStructure.GetSubtotalResp resp;
            //3. 进行gRpc调用
            resp = blockingStubStatic.getSubtotal(req);
            //Transformer trans = new Transformer();
            //FrontPageFlowSubtotal frontPageFlowSubtotal = trans.messageToJava(resp, FrontPageFlowSubtotal.class);
            FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
            frontPageFlowSubtotal.setExpenditureAmount(resp.getExpenditureAmount());
            frontPageFlowSubtotal.setExpenditureQuantity(resp.getExpenditureQuantity());
            frontPageFlowSubtotal.setIncomeAmount(resp.getIncomeAmount());
            frontPageFlowSubtotal.setIncomeQuantity(resp.getIncomeQuantity());

           gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), frontPageFlowSubtotal);
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
                                          @RequestParam(defaultValue = "")String distributorId, @RequestParam(defaultValue = "")String distributorName,
                                          @RequestParam(defaultValue = "CNY") String currencyCode,
                                          Long projectId, @RequestParam(defaultValue = "1") int pageNumber,
                                          @RequestParam(defaultValue = "50") int pageSize) {
        if (projectId == null){
            return new GongxiaoResult(ErrorCode.ARGUMENTS_INVALID.getErrorCode(), ErrorCode.ARGUMENTS_INVALID.getMessage());
        }
        GongxiaoResult gongxiaoResult = null;
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params:  projectId={} ,distributorId={}, distributorName={} ",
                    traceId,  projectId,distributorId,distributorName);
             // 查询出对应的经销商
            // List<DistributorPortalUser> distributorPortalUserList = distributorPortalUserService.selectAllByProjectIdAndUserIdAndUserName(rpcHeader,projectId,distributorId,distributorName);
//            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.Builder builder = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder();
            DistributorStructure.SelectDistributorBusinessByConditionReq.Builder builder = DistributorStructure.SelectDistributorBusinessByConditionReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            // builder.setProjectId(projectId);
            if (!"".equals(distributorId)) {
                builder.setDistributorBusinessId(Long.valueOf(distributorId));
            }else {
                builder.setDistributorBusinessId(0L);
            }
            builder.setProjectId(projectId);
            builder.setDistributorName(distributorName);
            builder.setPageNumber(pageNumber);
            builder.setPageSize(pageSize);
            DistributorStructure.SelectDistributorBusinessByConditionReq req = builder.build();
            DistributorServiceGrpc.DistributorServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.SelectDistributorBusinessByConditionResp resp= blockingStub.selectDistributorBusinessByCondition(req);
            List<DistributorStructure.DistributorBusiness> distributorPortalUserList = resp.getDistributorBusinessList();
            // 获取账户额度等信息
            List<DistributorDetail> distributorDetailList = new ArrayList<>();

            // 封装数据
            for (DistributorStructure.DistributorBusiness distributorPortalUser:distributorPortalUserList) {
                // 调用葛灿接口查询余额
                // AccountAmount accountAmount = distributorAccountService.getDistributorAccountAmount(rpcHeader,currencyCode,projectId,distributorPortalUser.getDistributorId());
                DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.Builder builder1 = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder();
                builder1.setRpcHeader(rpcHeader);
                builder1.setCurrencyCode(currencyCode);
                builder1.setProjectId(projectId);
//                if (!"".equals(distributorId)) {
//                    builder1.setDistributorId(Long.valueOf(distributorId));
//                }
                builder1.setDistributorId(distributorPortalUser.getDistributorBusinessId());
                DistributorAccountServiceStructure.GetDistributorAccountAmountRequest req1 = builder1.build();
                DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub blockingStub1 = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                PaymentCommonGrpc.AccountAmountResponse accountAmountResponse = blockingStub1.getDistributorAccountAmount(req1);
                //Transformer trans = new Transformer();
                //AccountAmount accountAmount = trans.messageToJava(accountAmountResponse, AccountAmount.class);

                AccountAmount accountAmount = new AccountAmount();
                accountAmount.setCashAmount(accountAmountResponse.getCashAmount());
                accountAmount.setCouponAmount(accountAmountResponse.getCouponAmount());
                accountAmount.setPrepaidAmount(accountAmountResponse.getPrepaidAmount());

                // 根据项目ID 客户ID 查询项目表中返利代垫使用率
                // Project project = projectService.getByProjectId(rpcHeader,String.valueOf(projectId));
                ProjectStructure.GetByProjectIdReq.Builder builder2 = ProjectStructure.GetByProjectIdReq.newBuilder();
                builder2.setRpcHeader(rpcHeader);
                builder2.setProjectId(projectId+"");
                ProjectStructure.GetByProjectIdReq req2 = builder2.build();
                ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub2 = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
                ProjectStructure.GetByProjectIdResp resp2 = blockingStub2.getByProjectId(req2);
                ProjectStructure.Project project = resp2.getProject();
                // 给distributorDetail赋值
                DistributorDetail distributorDetail = generate(accountAmount,project,distributorPortalUser);
                distributorDetailList.add(distributorDetail);
            }
            // PageHelper.startPage(pageNumber, pageSize);
            PageInfo<DistributorDetail> list = new PageInfo<>(distributorDetailList);
            list.setTotal(resp.getTotal());
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), list);
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
                                        String distributorId, String distributorName, @RequestParam(defaultValue = "CNY") String currencyCode,
                                        Long projectId) {
        if (projectId == null){
            throw new RuntimeException("the projectId is null");
        }
        String traceId = null;
        try {
            traceId = YhPortalTraceIdGenerator.genTraceId(portalUserInfo.getUserId(), TerminalCode.WEB.getCode(), portalConfig.getPortalId(), request.getServletPath());
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader(traceId, portalUserInfo.getUserId(), portalUserInfo.getUserName());
            logger.info("#traceId={}# [IN][postCoupon] params:  projectId={} ,distributorId={}, distributorName={} ",
                    traceId,  projectId,distributorId,distributorName);
            // 查询出对应的经销商
            // List<DistributorPortalUser> distributorPortalUserList = distributorPortalUserService.selectAllByProjectIdAndUserIdAndUserName(rpcHeader,projectId,distributorId,distributorName);
//            DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.Builder builder = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder();
            DistributorStructure.SelectDistributorBusinessByConditionReq.Builder builder = DistributorStructure.SelectDistributorBusinessByConditionReq.newBuilder();
            builder.setRpcHeader(rpcHeader);
            // builder.setProjectId(projectId);
            if (!"".equals(distributorId)) {
                builder.setDistributorBusinessId(Long.valueOf(distributorId));
            }else {
                builder.setDistributorBusinessId(0L);
            }
            builder.setProjectId(projectId);
            builder.setDistributorName(distributorName);
            DistributorStructure.SelectDistributorBusinessByConditionReq req = builder.build();
            DistributorServiceGrpc.DistributorServiceBlockingStub blockingStub = RpcStubStore.getRpcStub(DistributorServiceGrpc.DistributorServiceBlockingStub.class);
            DistributorStructure.SelectDistributorBusinessByConditionResp resp= blockingStub.selectDistributorBusinessByCondition(req);
            List<DistributorStructure.DistributorBusiness> distributorPortalUserList = resp.getDistributorBusinessList();
            // 获取账户额度等信息
            List<DistributorDetail> distributorDetailList = new ArrayList<>();

            // 封装数据
            for (DistributorStructure.DistributorBusiness distributorPortalUser:distributorPortalUserList) {
                // 调用葛灿接口查询余额
                // AccountAmount accountAmount = distributorAccountService.getDistributorAccountAmount(rpcHeader,currencyCode,projectId,distributorPortalUser.getDistributorId());
                DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.Builder builder1 = DistributorAccountServiceStructure.GetDistributorAccountAmountRequest.newBuilder();
                builder1.setRpcHeader(rpcHeader);
                builder1.setCurrencyCode(currencyCode);
                builder1.setProjectId(projectId);
//                if (!"".equals(distributorId)) {
//                    builder1.setDistributorId(Long.valueOf(distributorId));
//                }
                builder1.setDistributorId(distributorPortalUser.getDistributorBusinessId());
                DistributorAccountServiceStructure.GetDistributorAccountAmountRequest req1 = builder1.build();
                DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub blockingStub1 = RpcStubStore.getRpcStub(DistributorAccountServiceGrpc.DistributorAccountServiceBlockingStub.class);
                PaymentCommonGrpc.AccountAmountResponse accountAmountResponse = blockingStub1.getDistributorAccountAmount(req1);
                //Transformer trans = new Transformer();
                //AccountAmount accountAmount = trans.messageToJava(accountAmountResponse, AccountAmount.class);

                AccountAmount accountAmount = new AccountAmount();
                accountAmount.setCashAmount(accountAmountResponse.getCashAmount());
                accountAmount.setCouponAmount(accountAmountResponse.getCouponAmount());
                accountAmount.setPrepaidAmount(accountAmountResponse.getPrepaidAmount());

                // 根据项目ID 客户ID 查询项目表中返利代垫使用率
                // Project project = projectService.getByProjectId(rpcHeader,String.valueOf(projectId));
                ProjectStructure.GetByProjectIdReq.Builder builder2 = ProjectStructure.GetByProjectIdReq.newBuilder();
                builder2.setRpcHeader(rpcHeader);
                builder2.setProjectId(projectId+"");
                ProjectStructure.GetByProjectIdReq req2 = builder2.build();
                ProjectServiceGrpc.ProjectServiceBlockingStub blockingStub2 = RpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
                ProjectStructure.GetByProjectIdResp resp2 = blockingStub2.getByProjectId(req2);
                ProjectStructure.Project project = resp2.getProject();
                // 给distributorDetail赋值
                DistributorDetail distributorDetail = generate(accountAmount,project,distributorPortalUser);
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
    public DistributorDetail generate(AccountAmount accountAmount, ProjectStructure.Project project, DistributorStructure.DistributorBusiness distributorPortalUser){
        DistributorDetail distributorDetail = new DistributorDetail();
        distributorDetail.setDistributorId(String.valueOf( distributorPortalUser.getDistributorBusinessId()));
        distributorDetail.setDistributorName(distributorPortalUser.getDistributorName());
        // distributorDetail.setProjectId(distributorPortalUser.getProjectId()); 接口暂时缺少该参数
        // distributorDetail.setUserId(String.valueOf(distributorPortalUser.getUserId()));

        distributorDetail.setCashAmountDouble(accountAmount.getCashAmount()/ FXConstant.HUNDRED_DOUBLE);
        distributorDetail.setCouponAmountDouble(accountAmount.getCouponAmount()/ FXConstant.HUNDRED_DOUBLE);
        distributorDetail.setPrepaidAmountDouble(accountAmount.getPrepaidAmount()/ FXConstant.HUNDRED_DOUBLE);
        String couponRate = String.valueOf(project.getCouponUseReferRate()/ FXConstant.MILLION_DOUBLE)  +"%";
        String prepaidRate =  String.valueOf(project.getPrepaidUseReferRate()/ FXConstant.MILLION_DOUBLE)  +"%";
        distributorDetail.setCouponUseableRate(couponRate);
        distributorDetail.setPrepaidUseableRate(prepaidRate);

        return distributorDetail;
    }



}
