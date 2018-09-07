package com.yhglobal.gongxiao.payment.microservice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;

import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.CashCouponPrepaidFlows;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.dao.DistributorCashAccountDao;
import com.yhglobal.gongxiao.payment.dao.DistributorCashFlowDao;
import com.yhglobal.gongxiao.payment.dao.DistributorCouponAccountDao;
import com.yhglobal.gongxiao.payment.dao.DistributorPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.dao.DistributorPrepaidFlowDao;
import com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceStructure;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.model.*;
import com.yhglobal.gongxiao.payment.service.DistributorCashAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorCouponAccountService;
import com.yhglobal.gongxiao.payment.service.DistributorPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.processor.DistributorAccountServiceTransactionProcessor;
import com.yhglobal.gongxiao.payment.util.AccountSubtotalUtil;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transAccountAmount;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transFrontPageFlow;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transFrontPageSubtotal;

/**
 * @author: 葛灿
 */
@Service
public class DistributorAccountServiceImpl extends com.yhglobal.gongxiao.payment.microservice.DistributorAccountServiceGrpc.DistributorAccountServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(DistributorAccountServiceImpl.class);

    @Autowired
    DistributorCashAccountService distributorCashAccountService;
    @Autowired
    DistributorCouponAccountService distributorCouponAccountService;
    @Autowired
    DistributorPrepaidAccountService distributorPrepaidAccountService;
    @Autowired
    SupplierCouponBufferToDistributorService supplierCouponBufferToDistributorService;
    @Autowired
    SupplierPrepaidBufferToDistributorService supplierPrepaidBufferToDistributorService;
    @Autowired
    DistributorCouponAccountDao distributorCouponAccountDao;
    @Autowired
    DistributorPrepaidAccountDao distributorPrepaidAccountDao;
    @Autowired
    DistributorCashAccountDao distributorCashAccountDao;
    @Autowired
    DistributorCashFlowDao distributorCashFlowDao;
    @Autowired
    DistributorPrepaidFlowDao distributorPrepaidFlowDao;
    @Autowired
    DistributorAccountServiceTransactionProcessor distributorAccountServiceTransactionProcessor;

    @Override
    public void getDistributorAccountAmount(DistributorAccountServiceStructure.GetDistributorAccountAmountRequest request, StreamObserver<PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
        PaymentCommonGrpc.AccountAmountResponse response;
        PaymentCommonGrpc.AccountAmountResponse.Builder respBuilder = PaymentCommonGrpc.AccountAmountResponse.newBuilder();
        //拆分请求对象
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        long distributorId = request.getDistributorId();
        logger.info("#traceId={}# [IN][getDistributorAccountAmount] params: currencyCode={}, projectId={}, distributorId={}",
                rpcHeader.getTraceId(), currencyCode, projectId, distributorId);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            AccountAmount accountAmount = new AccountAmount();
            //查询现金账户
            DistributorCashAccount account = distributorCashAccountDao.getAccount(prefix, projectId, distributorId);
            accountAmount.setCashAmount(account.getTotalAmount());
            accountAmount.setCashAmountDouble(1.0 * account.getTotalAmount() / HUNDRED);
            logger.info("#traceId={}# [OUT]: get amount success.", rpcHeader.getTraceId());
            //拼装结果对象
            transAccountAmount(accountAmount, respBuilder);
            response = respBuilder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void payForSalesOrder(DistributorAccountServiceStructure.PayForSalesOrderRequest request, StreamObserver<DistributorAccountServiceStructure.PayForSalesOrderResponse> responseObserver) {
        DistributorAccountServiceStructure.PayForSalesOrderResponse response;
        DistributorAccountServiceStructure.PayForSalesOrderResponse.Builder builder = DistributorAccountServiceStructure.PayForSalesOrderResponse.newBuilder();
        //拆分请求对象
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long distributorId = request.getDistributorId();
        long projectId = request.getProjectId();
        long couponAmount = request.getCouponAmount();
        long prepaidAmount = request.getPrepaidAmount();
        long cashAmount = request.getCashAmount();
        String salesOrderNo = request.getSalesOrderNo();
        long transactionTime = request.getTransactionTime();
        logger.info("#traceId={}# [IN][payForSalesOrder] params: currencyCode={}, distributorId={}, projectId={}, couponAmount={}, prepaidAmount={}, cashAmount={}, salesOrderNo={}, transactionTime={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesOrderNo, transactionTime);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountServiceTransactionProcessor.payForSalesOrder(prefix, rpcHeader, currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesOrderNo, new Date(transactionTime));
            if (rpcResult.getSuccess()) {
                logger.info("#traceId={}# [OUT]: payForSalesOrder success.", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT]: payForSalesOrder FAILED.", rpcHeader.getTraceId());
            }
            //拼装结果对象
            transAccountFlows(rpcResult, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void returnForSalesReturnOrder(DistributorAccountServiceStructure.PayForSalesOrderRequest request, StreamObserver<DistributorAccountServiceStructure.PayForSalesOrderResponse> responseObserver) {
        DistributorAccountServiceStructure.PayForSalesOrderResponse response;
        DistributorAccountServiceStructure.PayForSalesOrderResponse.Builder builder = DistributorAccountServiceStructure.PayForSalesOrderResponse.newBuilder();
        //拆分请求对象
        //拆分请求对象
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long distributorId = request.getDistributorId();
        long projectId = request.getProjectId();
        long couponAmount = request.getCouponAmount();
        long prepaidAmount = request.getPrepaidAmount();
        long cashAmount = request.getCashAmount();
        String salesOrderNo = request.getSalesOrderNo();
        long transactionTime = request.getTransactionTime();
        logger.info("#traceId={}# [IN][returnForSalesReturnOrder] params: currencyCode={}, distributorId={}, projectId={}, couponAmount={}, prepaidAmount={}, cashAmount={}, salesReturnOrderNo={}, transactionTime={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesOrderNo, transactionTime);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            RpcResult<CashCouponPrepaidFlows> rpcResult = distributorAccountServiceTransactionProcessor.returnForSalesReturnOrder(prefix, rpcHeader, currencyCode, distributorId, projectId, couponAmount, prepaidAmount, cashAmount, salesOrderNo, new Date(transactionTime));
            if (rpcResult.getSuccess()) {
                logger.info("#traceId={}# [OUT]: payForSalesReturnOrder success.", rpcHeader.getTraceId());
            } else {
                logger.info("#traceId={}# [OUT]: payForSalesReturnOrder FAILED.", rpcHeader.getTraceId());
            }
            //拼装结果对象
            transAccountFlows(rpcResult, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void depositCouponReceived(DistributorAccountServiceStructure.DepositCouponReceivedRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        //拆分请求对象
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long distributorId = request.getDistributorId();
        long projectId = request.getProjectId();
        long couponAmount = request.getAmount();
        long transactionTime = request.getTransactionTime();
        String remark = request.getRemark();
        logger.info("#traceId={}# [IN][depositCouponReceived] params: currencyCode={}, distributorId={}, distributorName={}, projectId={}, couponAmount={}, transactionTime={}, remark={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, null, projectId, couponAmount, transactionTime, remark);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            RpcResult<String> rpcResult = distributorAccountServiceTransactionProcessor.depositCouponReceived(prefix, rpcHeader, currencyCode, distributorId, null, projectId, couponAmount, new Date(transactionTime), remark);
            if (rpcResult.getSuccess()) {
                logger.info("#traceId={}# [OUT]: deposit coupon success.", rpcHeader.getTraceId());
                builder.setMsg("");
                builder.setReturnCode(0);
            } else {
                logger.info("#traceId={}# [OUT]: deposit coupon FAILED.", rpcHeader.getTraceId());
                builder.setReturnCode(rpcResult.getCode());
                builder.setMsg(rpcResult.getMessage());
            }
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void depositPrepaidReceived(DistributorAccountServiceStructure.DepositCouponReceivedRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        //拆分请求对象
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long distributorId = request.getDistributorId();
        long projectId = request.getProjectId();
        long prepaidAmount = request.getAmount();
        long transactionTime = request.getTransactionTime();
        String remark = request.getRemark();
        logger.info("#traceId={}# [IN][depositPrepaidReceived] params: currencyCode={}, supplierId={}, supplierName={}, distributorId={}, distributorName={}, projectId={}, couponAmount={}, transactionTime={}, remark={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, null, projectId, prepaidAmount, transactionTime, remark);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            RpcResult<String> rpcResult = distributorAccountServiceTransactionProcessor.depositPrepaidReceived(prefix, rpcHeader, currencyCode, distributorId, null, projectId, prepaidAmount, new Date(transactionTime), remark);
            if (rpcResult.getSuccess()) {
                logger.info("#traceId={}# [OUT]: deposit prepaid success.", rpcHeader.getTraceId());
                builder.setMsg("");
                builder.setReturnCode(0);
            } else {
                logger.info("#traceId={}# [OUT]: deposit prepaid FAILED.", rpcHeader.getTraceId());
                builder.setReturnCode(rpcResult.getCode());
                builder.setMsg(rpcResult.getMessage());
            }
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectDistributorCouponAccountList(DistributorAccountServiceStructure.SelectDistributorAccountListRequest request, StreamObserver<DistributorAccountServiceStructure.DistributorAccountListResponse> responseObserver) {
        DistributorAccountServiceStructure.DistributorAccountListResponse response;
        DistributorAccountServiceStructure.DistributorAccountListResponse.Builder builder = DistributorAccountServiceStructure.DistributorAccountListResponse.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectDistributorCouponAccountList] params: currencyCode={}, projectId={}, pageNum={}, pageSize={}",
                rpcHeader.getTraceId(), currencyCode, projectId, pageNum, pageSize);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //启动分页
            PageHelper.startPage(pageNum, pageSize);
            //查询列表
            List<DistributorCouponAccount> couponAccountList = distributorCouponAccountDao.getAccountList(prefix, projectId);
            PageInfo<DistributorCouponAccount> pageInfo = new PageInfo<>(couponAccountList);
            logger.info("#traceId={}# [OUT]: select list success.", rpcHeader.getTraceId());
            builder.setPageNum(pageInfo.getPageNum());
            builder.setPageSize(pageInfo.getPageSize());
            builder.setTotal(pageInfo.getTotal());
            if (pageInfo.getList() != null) {
                for (DistributorCouponAccount javaObject : pageInfo.getList()) {

                    DistributorAccountServiceStructure.DistributorAccount.Builder protoObject = DistributorAccountServiceStructure.DistributorAccount.newBuilder()
                            .setProjectId(javaObject.getProjectId())
                            .setDistributorId(javaObject.getDistributorId())
                            .setDistributorName(javaObject.getDistributorName())
                            .setTransferAmountDouble(javaObject.getTransferAmountDouble());
                    builder.addList(protoObject);
                }
            }
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectDistributorPrepaidAccountList(DistributorAccountServiceStructure.SelectDistributorAccountListRequest request, StreamObserver<DistributorAccountServiceStructure.DistributorAccountListResponse> responseObserver) {
        DistributorAccountServiceStructure.DistributorAccountListResponse response;
        DistributorAccountServiceStructure.DistributorAccountListResponse.Builder builder = DistributorAccountServiceStructure.DistributorAccountListResponse.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectDistributorPrepaidAccountList] params: currencyCode={}, projectId={}, pageNum={}, pageSize={}",
                rpcHeader.getTraceId(), currencyCode, projectId, pageNum, pageSize);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //启动分页
            PageHelper.startPage(pageNum, pageSize);
            //查询列表
            List<DistributorPrepaidAccount> prepaidAccountList = distributorPrepaidAccountDao.getAccountList(prefix, projectId);
            PageInfo<DistributorPrepaidAccount> pageInfo = new PageInfo<>(prepaidAccountList);
            logger.info("#traceId={}# [OUT]: select list success.", rpcHeader.getTraceId());
            builder.setPageNum(pageInfo.getPageNum());
            builder.setPageSize(pageInfo.getPageSize());
            builder.setTotal(pageInfo.getTotal());
            for (DistributorPrepaidAccount javaObject : pageInfo.getList()) {
                DistributorAccountServiceStructure.DistributorAccount.Builder innerModel = DistributorAccountServiceStructure.DistributorAccount.newBuilder()
                        .setProjectId(javaObject.getProjectId())
                        .setDistributorId(javaObject.getDistributorId())
                        .setDistributorName(javaObject.getDistributorName())
                        .setTransferAmountDouble(javaObject.getTransferAmountDouble());
                builder.addList(innerModel);
            }
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void depositCouponReceivedAccounts(DistributorAccountServiceStructure.DepositReceivedAccountsRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        List<DistributorAccountServiceStructure.DistributorAccount> protoList = request.getListList();
        List<DistributorCouponAccount> distributorCouponAccounts = new ArrayList<>();
        for (DistributorAccountServiceStructure.DistributorAccount protoModel : protoList) {
            DistributorCouponAccount couponAccount = new DistributorCouponAccount();
            couponAccount.setProjectId(protoModel.getProjectId());
            couponAccount.setDistributorId(protoModel.getDistributorId());
            couponAccount.setDistributorName(protoModel.getDistributorName());
            couponAccount.setTransferAmountDouble(protoModel.getTransferAmountDouble());
            distributorCouponAccounts.add(couponAccount);
        }
        logger.info("#traceId={}# [IN][depositCouponReceivedAccounts] params:  distributorCouponAccounts.size()={}",
                rpcHeader.getTraceId(), distributorCouponAccounts.size());
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            RpcResult rpcResult = distributorAccountServiceTransactionProcessor.depositCouponReceivedAccounts(prefix, rpcHeader, distributorCouponAccounts);
            if (rpcResult.getSuccess()) {
                logger.info("#traceId={}# [OUT]: batch deposit coupon success.", rpcHeader.getTraceId());
                response = GrpcCommonUtil.success();
            } else {
                logger.info("#traceId={}# [OUT]: batch deposit coupon FAILED.", rpcHeader.getTraceId());
                response = GrpcCommonUtil.fail(rpcResult.getCode(), rpcResult.getMessage());
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void depositPrepaidReceivedAccounts(DistributorAccountServiceStructure.DepositReceivedAccountsRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        List<DistributorAccountServiceStructure.DistributorAccount> protoList = request.getListList();
        List<DistributorPrepaidAccount> distributorCouponAccounts = new ArrayList<>();
        for (DistributorAccountServiceStructure.DistributorAccount protoModel : protoList) {
            DistributorPrepaidAccount couponAccount = new DistributorPrepaidAccount();
            couponAccount.setProjectId(protoModel.getProjectId());
            couponAccount.setDistributorId(protoModel.getDistributorId());
            couponAccount.setDistributorName(protoModel.getDistributorName());
            couponAccount.setTransferAmountDouble(protoModel.getTransferAmountDouble());
            distributorCouponAccounts.add(couponAccount);
        }
        logger.info("#traceId={}# [IN][depositPrepaidReceivedAccounts] params:  distributorPrepaidAccounts.size()={}",
                rpcHeader.getTraceId(), distributorCouponAccounts.size());
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            RpcResult rpcResult = distributorAccountServiceTransactionProcessor.depositPrepaidReceivedAccounts(prefix, rpcHeader, distributorCouponAccounts);
            if (rpcResult.getSuccess()) {
                logger.info("#traceId={}# [OUT]: batch deposit prepaid success.", rpcHeader.getTraceId());
                response = GrpcCommonUtil.success();
            } else {
                logger.info("#traceId={}# [OUT]: batch deposit prepaid FAILED.", rpcHeader.getTraceId());
                response = GrpcCommonUtil.fail(rpcResult.getCode(), rpcResult.getMessage());
            }
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectCashFlow(DistributorAccountServiceStructure.SelectDistributorFlowRqeust request, StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowPageInfo response;
        PaymentCommonGrpc.FrontPageFlowPageInfo.Builder builder = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
        //拆分请求
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long distributorId = request.getDistributorId();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][cancelApproveLedger] params: currencyCode={}, distributorId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(pageNum, pageSize);
            //查询流水
            List<DistributorCashFlow> list = distributorCashFlowDao.selectAllBydistributorId(prefix, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
            //转换模型
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (DistributorCashFlow record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getFlowId() + "");
                pageFlow.setType(record.getFlowType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
                pageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction() / FXConstant.HUNDRED_DOUBLE);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreateByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            logger.info("#traceId={}# [OUT]: select list success.", rpcHeader.getTraceId());
            transFrontPageFlow(pageInfo, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void getCashSubtotal(DistributorAccountServiceStructure.GetDistributorSubtotalRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowSubtotal response;
        PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
        //拆分请求
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long distributorId = request.getDistributorId();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        logger.info("#traceId={}# [IN][cancelApproveLedger] params: currencyCode={}, distributorId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //查询小计
            List<FlowSubtotal> flowSubtotals = distributorCashFlowDao.selectIncomeAndExpenditure(prefix, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal subtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: get subtotal success.", rpcHeader.getTraceId());
            transFrontPageSubtotal(subtotal, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    /**
     * 转换rpcResult<AccountFlows>(Java) -> AccountFlows(proto)
     *
     * @param rpcResult java对象
     * @param builder   proto对象
     * @return
     */
    private void transAccountFlows(RpcResult<CashCouponPrepaidFlows> rpcResult, DistributorAccountServiceStructure.PayForSalesOrderResponse.Builder builder) {
        builder.setReturnCode(rpcResult.getCode());
        DistributorAccountServiceStructure.CashCouponPrepaidFlows.Builder flowBuilder = DistributorAccountServiceStructure.CashCouponPrepaidFlows.newBuilder();
        if (rpcResult.getSuccess()) {
            flowBuilder.setCashFlowNo(GrpcCommonUtil.getProtoParam(rpcResult.getResult().getCashFlowNo()));
            flowBuilder.setCouponFlowNo(GrpcCommonUtil.getProtoParam(rpcResult.getResult().getCashFlowNo()));
            flowBuilder.setPrepaidFlowNo(GrpcCommonUtil.getProtoParam(rpcResult.getResult().getCashFlowNo()));
            builder.setMsg("");
        } else {
            flowBuilder.setCashFlowNo("");
            flowBuilder.setCouponFlowNo("");
            flowBuilder.setPrepaidFlowNo("");
            builder.setMsg(rpcResult.getMessage());
        }
        builder.setResult(flowBuilder);
    }

    @Override
    public void selectPrepaidFlow(DistributorAccountServiceStructure.SelectPrepaidFlowRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        Long distributorId = request.getDistributorId();
        Long projectId = request.getProjectId();
        Integer accountType = request.getAccountType();
        Integer moneyFlow = request.getMoneyFlow();
        Date beginDate = null;
        if (request.getBeginDate() != "") {
            try {
                beginDate = DateUtil.stringToDate(request.getBeginDate());
            } catch (Exception e) {
            }
        }
        Date endDate = null;
        if (request.getEndDate() != "") {
            try {
                endDate = DateUtil.stringToDate(request.getEndDate());
            } catch (Exception e) {
            }
        }
        Integer pageNum = request.getPageNum();
        Integer pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectPrepaidFlow] params: currencyCode={}, distributorId={}, projectId={}, accountType={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, projectId, accountType, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PaymentCommonGrpc.FrontPageFlowPageInfo.Builder builder = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
            PaymentCommonGrpc.FrontPageFlowPageInfo response;
            PageHelper.startPage(pageNum, pageSize);
            List<DistributorPrepaidFlow> prepaidTransferRecordList = distributorPrepaidFlowDao.selectAllBydistributorId(prefix, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
            List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
            for (DistributorPrepaidFlow record : prepaidTransferRecordList) {
                FrontPageFlow frontPageFlow = generateFrontPageFlow(record);
                frontPageFlowList.add(frontPageFlow);
            }
            // 用分页包装
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlowList);
            transFrontPageFlow(pageInfo, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }

    /**
     * 生成流水包装类
     */
    FrontPageFlow generateFrontPageFlow(DistributorPrepaidFlow record) {
        FrontPageFlow frontPageFlow = new FrontPageFlow();
        // 前台显示订单号
        frontPageFlow.setFlowNo(record.getFlowNo());
        frontPageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction() / FXConstant.HUNDRED_DOUBLE);
        frontPageFlow.setCreateTime(record.getCreateTime());
        frontPageFlow.setCurrencyCode(record.getCurrencyCode());
        frontPageFlow.setTransactionAmount(record.getTransactionAmount() / FXConstant.HUNDRED_DOUBLE);
        frontPageFlow.setType(record.getFlowType());
        frontPageFlow.setCreateByName(record.getCreateByName());
        return frontPageFlow;
    }

    @Override
    public void getPrepaidSubtotal(DistributorAccountServiceStructure.GetPrepaidSubtotalRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        Long distributorId = request.getDistributorId();
        Long projectId = request.getProjectId();
        Integer accountType = request.getAccountType();
        Integer moneyFlow = request.getMoneyFlow();
        Date beginDate = null;
        if (request.getBeginDate() != "") {
            try {
                beginDate = DateUtil.stringToDate(request.getBeginDate());
            } catch (Exception e) {
            }
        }
        Date endDate = null;
        if (request.getEndDate() != "") {
            try {
                endDate = DateUtil.stringToDate(request.getEndDate());
            } catch (Exception e) {
            }
        }
        logger.info("#traceId={}# [IN][getPrepaidSubtotal] params: currencyCode={}, distributorId={}, projectId={}, accountType={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, distributorId, projectId, accountType, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
            PaymentCommonGrpc.FrontPageFlowSubtotal response;
            FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
            List<FlowSubtotal> flowSubtotals = distributorPrepaidFlowDao.selectIncomeAndExpenditure(prefix, currencyCode, distributorId, projectId, moneyFlow, beginDate, endDate);
            for (FlowSubtotal flowSubtotal : flowSubtotals) {
                if (YhGlobalInoutFlowConstant.FLOW_TYPE_IN.getNum() == flowSubtotal.getRecordType()) {
                    frontPageFlowSubtotal.setIncomeQuantity(flowSubtotal.getCount());
                    frontPageFlowSubtotal.setIncomeAmount(flowSubtotal.getAmountCount() / FXConstant.HUNDRED_DOUBLE);
                } else if (YhGlobalInoutFlowConstant.FLOW_TYPE_OUT.getNum() == flowSubtotal.getRecordType()) {
                    frontPageFlowSubtotal.setExpenditureQuantity(flowSubtotal.getCount());
                    frontPageFlowSubtotal.setExpenditureAmount(flowSubtotal.getAmountCount() / FXConstant.HUNDRED_DOUBLE);
                }
            }
            transFrontPageSubtotal(frontPageFlowSubtotal, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }
}
