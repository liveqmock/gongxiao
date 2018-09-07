package com.yhglobal.gongxiao.payment.microservice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponRecordDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidRecordDao;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure;
import com.yhglobal.gongxiao.payment.model.FlowSubtotal;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponAccount;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedCouponRecord;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidAccount;
import com.yhglobal.gongxiao.payment.model.YhglobalReceivedPrepaidRecord;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidAccountService;
import com.yhglobal.gongxiao.payment.util.AccountSubtotalUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.ErrorCode.YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transAccountAmount;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transFrontPageFlow;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transFrontPageSubtotal;

/**
 * @author: 葛灿
 */
@Service
public class YhglobalAccountServiceImpl extends com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceGrpc.YhglobalAccountServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(YhglobalAccountServiceImpl.class);

    @Autowired
    SupplierCouponBufferToYhglobalService supplierCouponBufferToYhglobalService;

    @Autowired
    SupplierPrepaidBufferToYhglobalService supplierPrepaidBufferToYhglobalService;

    @Autowired
    YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;

    @Autowired
    YhglobalReceivedPrepaidAccountService yhglobalReceivedPrepaidAccountService;

    @Autowired
    YhglobalReceivedCouponAccountDao yhglobalReceivedCouponAccountDao;

    @Autowired
    YhglobalReceivedPrepaidAccountDao yhglobalReceivedPrepaidAccountDao;

    @Autowired
    YhglobalReceivedCouponRecordDao yhglobalReceivedCouponRecordDao;

    @Autowired
    YhglobalReceivedPrepaidRecordDao yhglobalReceivedPrepaidRecordDao;

    @Override
    public void getYhglobalReceivedAccountAmount(YhglobalAccountServiceStructure.GetYhglobalReceivedAccountAmountRequest request, StreamObserver<PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
        PaymentCommonGrpc.AccountAmountResponse response;
        PaymentCommonGrpc.AccountAmountResponse.Builder builder = PaymentCommonGrpc.AccountAmountResponse.newBuilder();
        // 拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][getYhglobalReceivedAccountAmount] params: currencyCode={}, projectId={}",
                rpcHeader.getTraceId(), currencyCode, projectId);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            double roundUpValue;
            AccountAmount accountAmount = new AccountAmount();
            YhglobalReceivedCouponAccount couponAccount = yhglobalReceivedCouponAccountDao.getAccount(prefix,projectId);
            accountAmount.setCouponAmount(couponAccount.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * couponAccount.getTotalAmount() / HUNDRED);
            accountAmount.setCouponAmountDouble(roundUpValue);
            YhglobalReceivedPrepaidAccount prepaidAccount = yhglobalReceivedPrepaidAccountDao.getAccount(prefix,projectId);
            accountAmount.setPrepaidAmount(prepaidAccount.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * prepaidAccount.getTotalAmount() / HUNDRED);
            accountAmount.setPrepaidAmountDouble(roundUpValue);
            logger.info("#traceId={}# [OUT]: get amount success, projectId={}", rpcHeader.getTraceId(), projectId);
            transAccountAmount(accountAmount, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectCouponReceivedRecords(YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowPageInfo response;
        PaymentCommonGrpc.FrontPageFlowPageInfo.Builder builder = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
        // 拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectCouponReceivedRecords] params:currencyCode{}, projectId{}, moneyFlow{}, beginDate{}, endDate{}, pageNum{}, pageSize={}",
                rpcHeader.getTraceId(), currencyCode, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(pageNum, pageSize);
            List<YhglobalReceivedCouponRecord> list = yhglobalReceivedCouponRecordDao.selectCouponReceivedRecords(prefix,currencyCode, projectId, moneyFlow, beginDate, endDate);
            PageInfo<YhglobalReceivedCouponRecord> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (YhglobalReceivedCouponRecord record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getRecordId() + "");
                pageFlow.setType(record.getRecordType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
                pageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreatedByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            pageInfo.setTotal(oldPageInfo.getTotal());
            logger.info("#traceId={}# [OUT]: select list success", rpcHeader.getTraceId());
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
    public void getCouponSubtotal(YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowSubtotal response;
        PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        logger.info("#traceId={}# [IN][getCouponSubtotal] params: currencyCode={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            List<FlowSubtotal> flowSubtotals = yhglobalReceivedCouponRecordDao.selectIncomeAndExpenditure(prefix,currencyCode, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal subtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: get subtotal success", rpcHeader.getTraceId());
            transFrontPageSubtotal(subtotal, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectPrepaidReceivedRecords(YhglobalAccountServiceStructure.SelectYhglobalFlowsRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowPageInfo response;
        PaymentCommonGrpc.FrontPageFlowPageInfo.Builder builder = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
        // 拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectPrepaidReceivedRecords] params: currencyCode={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(pageNum, pageSize);
            List<YhglobalReceivedPrepaidRecord> list = yhglobalReceivedPrepaidRecordDao.selectPrepaidReceivedRecords(prefix,currencyCode, projectId, moneyFlow, beginDate, endDate);
            PageInfo<YhglobalReceivedPrepaidRecord> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (YhglobalReceivedPrepaidRecord record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getRecordId() + "");
                pageFlow.setType(record.getRecordType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
                pageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreatedByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            pageInfo.setTotal(oldPageInfo.getTotal());
            logger.info("#traceId={}# [OUT]: select list success", rpcHeader.getTraceId());
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
    public void getPrepaidSubtotal(YhglobalAccountServiceStructure.GetYhglobalSubtotalRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowSubtotal response;
        PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
        // 拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        logger.info("#traceId={}# [IN][getPrepaidSubtotal] params: currencyCode={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            List<FlowSubtotal> flowSubtotals = yhglobalReceivedPrepaidRecordDao.selectIncomeAndExpenditure(prefix,currencyCode, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal subtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: get subtotal success", rpcHeader.getTraceId());
            transFrontPageSubtotal(subtotal, builder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void transferYhglobalReceivedCoupon(YhglobalAccountServiceStructure.TransferReceivedAccountRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        double amountDouble = request.getAmountDouble();
        logger.info("#traceId={}# [IN][transferYhglobalReceivedCoupon] params: projectId={}, amountDouble={}",
                rpcHeader.getTraceId(), projectId, amountDouble);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            long amount = Math.round(amountDouble * HUNDRED);
            YhglobalReceivedCouponAccount account = yhglobalReceivedCouponAccountDao.getAccount(prefix,projectId);
            if (amount > account.getTotalAmount()) {
                logger.error("#traceId={}# [OUT]: NO BALANCE", rpcHeader.getTraceId());
                //账户余额不足
                response = GrpcCommonUtil.fail(YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;

            }
            yhglobalReceivedCouponAccountService.updateYhglobalReceivedCouponAccount(prefix,rpcHeader, "CNY", projectId, -amount, new Date(), null, null);
            logger.info("#traceId={}# [OUT]: transfer success. projectId={}, amount={}", rpcHeader.getTraceId(), projectId, amountDouble);
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (DataIntegrityViolationException e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            response = GrpcCommonUtil.fail(YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void transferYhglobalReceivedPrepaid(YhglobalAccountServiceStructure.TransferReceivedAccountRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        double amountDouble = request.getAmountDouble();
        logger.info("#traceId={}# [IN][transferYhglobalReceivedPrepaid] params: projectId={}, amountDouble={}",
                rpcHeader.getTraceId(), projectId, amountDouble);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            long amount = Math.round(amountDouble * HUNDRED);
            YhglobalReceivedPrepaidAccount account = yhglobalReceivedPrepaidAccountDao.getAccount(prefix,projectId);
            if (amount > account.getTotalAmount()) {
                //账户余额不足
                logger.error("#traceId={}# [OUT]: NO BALANCE", rpcHeader.getTraceId());
                response = GrpcCommonUtil.fail(YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            yhglobalReceivedPrepaidAccountService.updateYhglobalReceivedPrepaidAccount(prefix,rpcHeader, "CNY", projectId, -amount, new Date(), null, null);
            logger.info("#traceId={}# [OUT]: transfer success. projectId={}, amount={}", rpcHeader.getTraceId(), projectId, amountDouble);
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (DataIntegrityViolationException e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            response = GrpcCommonUtil.fail(YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void updateYhglobalReceivedCouponAccount(PaymentCommonGrpc.WriteOffRequest request, StreamObserver<PaymentCommonGrpc.WriteOffResponse> responseObserver) {
        PaymentCommonGrpc.WriteOffResponse response;
        PaymentCommonGrpc.WriteOffResponse.Builder builder = PaymentCommonGrpc.WriteOffResponse.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        long amount = request.getAmount();
        long transactionTime = request.getTransactionTime();
        String writeOffFlowNo = request.getWriteOffFlowNo();
        logger.info("#traceId={}# [IN][updateYhglobalReceivedCouponAccount] params: currencyCode={}, projectId={}, amount={}, transactionTime={}, writeOffFlowNo={}",
                rpcHeader.getTraceId(), currencyCode, projectId, amount, transactionTime, writeOffFlowNo);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            YhglobalReceivedCouponAccount account = yhglobalReceivedCouponAccountDao.getAccount(prefix,projectId);
            if (amount > account.getTotalAmount()) {
                //账户余额不足
                logger.error("#traceId={}# [OUT]: NO BALANCE", rpcHeader.getTraceId());
                response = builder.setReturnCode(YHGLOBAL_RECEIVED_COUPON_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode()).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            WriteOffReturnObject writeOffReturnObject = yhglobalReceivedCouponAccountService.updateYhglobalReceivedCouponAccount(prefix,rpcHeader, "CNY", projectId, amount, new Date(transactionTime), writeOffFlowNo, null);
            if (writeOffReturnObject.getReturnCode() == 0) {
                logger.info("#traceId={}# [OUT]: update success. projectId={}, amount={}", rpcHeader.getTraceId(), projectId, amount);
                builder.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                builder.setAmountBeforeTransaction(writeOffReturnObject.getAmountBeforeTrans());
                builder.setAmountAfterTransaction(writeOffReturnObject.getAmountAfterTrans());
            } else {
                logger.info("#traceId={}# [OUT]: update FAILED. projectId={}, amount={}", rpcHeader.getTraceId(), projectId, amount);
                builder.setReturnCode(writeOffReturnObject.getReturnCode());
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
    public void updateYhglobalReceivedPrepaidAccount(PaymentCommonGrpc.WriteOffRequest request, StreamObserver<PaymentCommonGrpc.WriteOffResponse> responseObserver) {
        PaymentCommonGrpc.WriteOffResponse response;
        PaymentCommonGrpc.WriteOffResponse.Builder builder = PaymentCommonGrpc.WriteOffResponse.newBuilder();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        long amount = request.getAmount();
        long transactionTime = request.getTransactionTime();
        String writeOffFlowNo = request.getWriteOffFlowNo();
        logger.info("#traceId={}# [IN][updateYhglobalReceivedPrepaidAccount] params: currencyCode={}, projectId={}, amount={}, transactionTime={}, writeOffFlowNo={}",
                rpcHeader.getTraceId(), currencyCode, projectId, amount, transactionTime, writeOffFlowNo);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            YhglobalReceivedPrepaidAccount account = yhglobalReceivedPrepaidAccountDao.getAccount(prefix,projectId);
            if (amount > account.getTotalAmount()) {
                //账户余额不足
                logger.error("#traceId={}# [OUT]: NO BALANCE", rpcHeader.getTraceId());
                response = builder.setReturnCode(YHGLOBAL_RECEIVED_PREPAID_ACCOUNT_BALANCE_NOT_ENOUGH.getErrorCode()).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            WriteOffReturnObject writeOffReturnObject = yhglobalReceivedPrepaidAccountService.updateYhglobalReceivedPrepaidAccount(prefix,rpcHeader, "CNY", projectId, amount, new Date(transactionTime), writeOffFlowNo, null);
            if (writeOffReturnObject.getReturnCode() == 0) {
                logger.info("#traceId={}# [OUT]: update success. projectId={}, amount={}", rpcHeader.getTraceId(), projectId, amount);
                builder.setReturnCode(ErrorCode.SUCCESS.getErrorCode());
                builder.setAmountBeforeTransaction(writeOffReturnObject.getAmountBeforeTrans());
                builder.setAmountAfterTransaction(writeOffReturnObject.getAmountAfterTrans());
            } else {
                logger.info("#traceId={}# [OUT]: update FAILED. projectId={}, amount={}", rpcHeader.getTraceId(), projectId, amount);
                builder.setReturnCode(writeOffReturnObject.getReturnCode());
            }
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
