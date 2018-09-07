package com.yhglobal.gongxiao.payment.jdservice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedCouponRecordDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.dao.YhglobalReceivedPrepaidRecordDao;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceGrpc;
import com.yhglobal.gongxiao.payment.microservice.YhglobalAccountServiceStructure;
import com.yhglobal.gongxiao.payment.model.*;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidAccountService;
import com.yhglobal.gongxiao.payment.util.AccountSubtotalUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.*;

/**
 * @author: 葛灿
 */
@Service
public class YhglobalAccountServiceImpl extends YhglobalAccountServiceGrpc.YhglobalAccountServiceImplBase {

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
            double roundUpValue;
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            AccountAmount accountAmount = new AccountAmount();
            YhglobalReceivedCouponAccount couponAccount = yhglobalReceivedCouponAccountDao.getAccount(prefix, projectId);
            accountAmount.setCouponAmountDouble(couponAccount.getTotalAmount().doubleValue());
            //roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * couponAccount.getTotalAmount() / HUNDRED);
            //accountAmount.setCouponAmountDouble(roundUpValue);
            YhglobalReceivedPrepaidAccount prepaidAccount = yhglobalReceivedPrepaidAccountDao.getAccount(prefix,projectId);
            accountAmount.setPrepaidAmountDouble(prepaidAccount.getTotalAmount().doubleValue());
//            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * prepaidAccount.getTotalAmount() / HUNDRED);
//            accountAmount.setPrepaidAmountDouble(roundUpValue);
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
            PageHelper.startPage(pageNum, pageSize);
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            List<YhglobalReceivedCouponRecord> list = yhglobalReceivedCouponRecordDao.selectCouponReceivedRecords(prefix, currencyCode,
                    projectId, moneyFlow, beginDate, endDate);
            PageInfo<YhglobalReceivedCouponRecord> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (YhglobalReceivedCouponRecord record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getRecordId() + "");
                pageFlow.setType(record.getRecordType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(record.getTransactionAmount().doubleValue());
                pageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction().doubleValue());
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
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            List<FlowSubtotal> flowSubtotals = yhglobalReceivedCouponRecordDao.selectIncomeAndExpenditure(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
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
            PageHelper.startPage(pageNum, pageSize);
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            List<YhglobalReceivedPrepaidRecord> list = yhglobalReceivedPrepaidRecordDao.selectPrepaidReceivedRecords(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
            PageInfo<YhglobalReceivedPrepaidRecord> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (YhglobalReceivedPrepaidRecord record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getRecordId() + "");
                pageFlow.setType(record.getRecordType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(record.getTransactionAmount().doubleValue());
                pageFlow.setAmountAfterTransaction(record.getAmountAfterTransaction().doubleValue());
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
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);
            List<FlowSubtotal> flowSubtotals = yhglobalReceivedPrepaidRecordDao.selectIncomeAndExpenditure(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
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
}
