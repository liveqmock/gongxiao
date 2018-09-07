package com.yhglobal.gongxiao.payment.microservice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.common.spring.ApplicationContextProvider;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.YhGlobalInoutFlowConstant;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.bo.WriteOffReturnObject;
import com.yhglobal.gongxiao.payment.dao.JmgoSupplierSellHighFlowDao;
import com.yhglobal.gongxiao.payment.microservice.PaymentCommonGrpc;
import com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceStructure;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.payment.bo.AccountAmount;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlow;
import com.yhglobal.gongxiao.payment.bo.FrontPageFlowSubtotal;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponAccountDao;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponBufferToDistributorDao;
import com.yhglobal.gongxiao.payment.dao.SupplierCouponBufferToDistributorFlowDao;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidAccountDao;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidBufferToDistributorDao;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidFlowDao;
import com.yhglobal.gongxiao.payment.dao.SupplierPrepaidBufferToDistributorFlowDao;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferAccountDao;
import com.yhglobal.gongxiao.payment.dao.SupplierSellHeightTransferRecordDao;
import com.yhglobal.gongxiao.payment.dto.PurchaseFlowCollections;
import com.yhglobal.gongxiao.payment.model.*;
import com.yhglobal.gongxiao.payment.service.SupplierCouponAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierCouponBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToDistributorService;
import com.yhglobal.gongxiao.payment.service.SupplierPrepaidBufferToYhglobalService;
import com.yhglobal.gongxiao.payment.service.SupplierSellHeightTransferAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedCouponAccountService;
import com.yhglobal.gongxiao.payment.service.YhglobalReceivedPrepaidAccountService;
import com.yhglobal.gongxiao.payment.service.processor.SupplierAccountServiceTransactionProcessor;
import com.yhglobal.gongxiao.payment.task.SyncCouponPrepaidToYhglobalReceivedTask;
import com.yhglobal.gongxiao.payment.util.AccountSubtotalUtil;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH;
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED;
import static com.yhglobal.gongxiao.constant.FXConstant.HUNDRED_DOUBLE;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transAccountAmount;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transFrontPageFlow;
import static com.yhglobal.gongxiao.payment.util.AccountProtoUtil.transFrontPageSubtotal;

/**
 * @author: 葛灿
 */
@Service
public class SupplierAccountServiceImpl extends com.yhglobal.gongxiao.payment.microservice.SupplierAccountServiceGrpc.SupplierAccountServiceImplBase {


    private static final Logger logger = LoggerFactory.getLogger(SupplierAccountServiceImpl.class);

    @Autowired
    SupplierCouponAccountService supplierCouponAccountService;

    @Autowired
    SupplierPrepaidAccountService supplierPrepaidAccountService;

    @Autowired
    SupplierCouponBufferToYhglobalService supplierCouponBufferToYhglobalService;

    @Autowired
    SupplierPrepaidBufferToYhglobalService supplierPrepaidBufferToYhglobalService;

    @Autowired
    SupplierCouponBufferToDistributorService supplierCouponBufferToDistributorService;

    @Autowired
    SupplierPrepaidBufferToDistributorService supplierPrepaidBufferToDistributorService;

    @Autowired
    SupplierSellHeightTransferAccountService supplierSellHeightTransferAccountService;

    @Autowired
    SupplierSellHeightTransferAccountDao supplierSellHeightTransferAccountDao;

    @Autowired
    SupplierSellHeightTransferRecordDao supplierSellHeightTransferRecordDao;

    @Autowired
    SupplierCouponAccountDao supplierCouponAccountDao;

    @Autowired
    SupplierPrepaidAccountDao supplierPrepaidAccountDao;

    @Autowired
    SupplierCouponBufferToDistributorDao supplierCouponBufferToDistributorDao;

    @Autowired
    SupplierPrepaidBufferToDistributorDao supplierPrepaidBufferToDistributorDao;

    @Autowired
    SupplierCouponBufferToDistributorFlowDao supplierCouponBufferToDistributorFlowDao;

    @Autowired
    SupplierPrepaidBufferToDistributorFlowDao supplierPrepaidBufferToDistributorFlowDao;

    @Autowired
    SupplierPrepaidFlowDao supplierPrepaidFlowDao;

    @Autowired
    private YhglobalReceivedCouponAccountService yhglobalReceivedCouponAccountService;
    @Autowired
    private YhglobalReceivedPrepaidAccountService yhglobalReceivedPrepaidAccountService;

    @Autowired
    private SupplierAccountServiceTransactionProcessor supplierAccountServiceTransactionProcessor;

    @Autowired
    private JmgoSupplierSellHighFlowDao jmgoSupplierSellHighFlowDao;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolExecutor;

    @Override
    public void getSupplierCouponBufferToDistributor(SupplierAccountServiceStructure.GetSupplierCouponBufferToDistributorRequest request, StreamObserver<PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
        PaymentCommonGrpc.AccountAmountResponse response;
        PaymentCommonGrpc.AccountAmountResponse.Builder builder = PaymentCommonGrpc.AccountAmountResponse.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][getSupplierCouponBufferToDistributor] params: currencyCode={}, projectId={}",
                rpcHeader.getTraceId(), currencyCode, projectId);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //新建返回模型
            double roundUpValue;
            AccountAmount accountAmount = new AccountAmount();
            //供应商返利过账到ad账户
            SupplierCouponBufferToDistributor couponBufferToDistributor = supplierCouponBufferToDistributorDao.getAccount(prefix, projectId);
            accountAmount.setCouponAmount(couponBufferToDistributor.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * couponBufferToDistributor.getTotalAmount() / HUNDRED);
            accountAmount.setCouponAmountDouble(roundUpValue);
            //供应商代垫过账到ad账户
            SupplierPrepaidBufferToDistributor prepaidBufferToDistributor = supplierPrepaidBufferToDistributorDao.getAccount(prefix, projectId);
            accountAmount.setPrepaidAmount(prepaidBufferToDistributor.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * prepaidBufferToDistributor.getTotalAmount() / HUNDRED);
            accountAmount.setPrepaidAmountDouble(roundUpValue);
            logger.info("#traceId={}# [OUT]: get amount success.", rpcHeader.getTraceId());
            //拼装结果对象
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
    public void getSupplierAccountAmount(SupplierAccountServiceStructure.GetSupplierAccountAmountRequest request, StreamObserver<PaymentCommonGrpc.AccountAmountResponse> responseObserver) {
        PaymentCommonGrpc.AccountAmountResponse response;
        PaymentCommonGrpc.AccountAmountResponse.Builder builder = PaymentCommonGrpc.AccountAmountResponse.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][getSupplierAccountAmount] params: currencyCode={}, projectId={}",
                rpcHeader.getTraceId(), currencyCode, projectId);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            double roundUpValue;
            AccountAmount accountAmount = new AccountAmount();
            SupplierCouponAccount couponAccount = supplierCouponAccountDao.getAccount(prefix, projectId);
            accountAmount.setCouponAmount(couponAccount.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * couponAccount.getTotalAmount() / HUNDRED);
            accountAmount.setCouponAmountDouble(roundUpValue);
            SupplierPrepaidAccount prepaidAccount = supplierPrepaidAccountDao.getAccount(prefix, projectId);
            accountAmount.setPrepaidAmount(prepaidAccount.getTotalAmount());
            roundUpValue = DoubleScale.getRoundUpValue(2, 1.0 * prepaidAccount.getTotalAmount() / HUNDRED);
            accountAmount.setPrepaidAmountDouble(roundUpValue);
            logger.info("#traceId={}# [OUT]: get amount success.", rpcHeader.getTraceId());
            //拼装结果对象
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
    public void postSupplierCouponAccount(SupplierAccountServiceStructure.PostSupplierCouponAccountRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        long couponAmount = request.getAmount();
        String remark = request.getRemark();
        logger.info("#traceId={}# [IN][postSupplierCouponAccount] params: currencyCode={}, projectId={}, couponAmount={}, remark={}",
                rpcHeader.getTraceId(), currencyCode, projectId, couponAmount, remark);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //上账账户转入
            String flowNo = supplierCouponAccountService.updateSupplierCouponAccount(prefix, rpcHeader, currencyCode, projectId, couponAmount, new Date(), remark, null, null);
            logger.info("#traceId={}# [OUT]: post coupon success.", rpcHeader.getTraceId());
            builder.setReturnCode(0);
            builder.setMsg("");
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void postSupplierPrepaidAccount(SupplierAccountServiceStructure.PostSupplierPrepaidAccountRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        long prepaidAmount = request.getAmount();
        String remark = request.getRemark();
        logger.info("#traceId={}# [IN][postSupplierCouponAccount] params: currencyCode={}, projectId={}, prepaidAmount={}, remark={}",
                rpcHeader.getTraceId(), currencyCode, projectId, prepaidAmount, remark);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //上账账户转入
            supplierPrepaidAccountService.updateSupplierPrepaidAccount(prefix, rpcHeader, currencyCode, projectId, prepaidAmount, new Date(), remark, null, null);
            logger.info("#traceId={}# [OUT]: approve success.", rpcHeader.getTraceId());
            builder.setReturnCode(0);
            builder.setMsg("");
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void payForPurchase(SupplierAccountServiceStructure.PayForPurchaseRequest request, StreamObserver<SupplierAccountServiceStructure.PayForPurchaseResponse> responseObserver) {
        SupplierAccountServiceStructure.PayForPurchaseResponse response;
        SupplierAccountServiceStructure.PayForPurchaseResponse.Builder builder = SupplierAccountServiceStructure.PayForPurchaseResponse.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        long couponToYh = request.getCouponToYh();
        long couponToAd = request.getCouponToAd();
        long prepaidToYh = request.getPrepaidToYh();
        long prepaidToAd = request.getPrepaidToAd();
        String purchaseOrderNo = request.getPurchaseOrderNo();
        long transactionTime = request.getTransactionTime();
        logger.info("#traceId={}# [IN][payForPurchase] params: currencyCode={}, projectId={}, couponToYh={}, couponToAd={}, prepaidToYh={}, prepaidToAd={}, purchaseOrderNo={}, transactionTime={}",
                rpcHeader.getTraceId(), currencyCode, projectId, couponToYh, couponToAd, prepaidToYh, prepaidToAd, purchaseOrderNo, transactionTime);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            RpcResult<PurchaseFlowCollections> rpcResult = supplierAccountServiceTransactionProcessor.payForPurchase(prefix, rpcHeader, currencyCode, projectId, couponToYh, couponToAd, prepaidToYh, prepaidToAd, purchaseOrderNo, new Date(transactionTime));
            if (rpcResult.getSuccess()) {
                //新起线程，把越海的返利代垫，从缓冲账户同步到实收账户
                SyncCouponPrepaidToYhglobalReceivedTask task = new SyncCouponPrepaidToYhglobalReceivedTask(prefix, ApplicationContextProvider.getApplicationContext(), rpcHeader,
                        currencyCode, projectId, couponToYh, prepaidToYh, new Date(transactionTime), purchaseOrderNo, supplierCouponBufferToYhglobalService,
                        yhglobalReceivedCouponAccountService,
                        supplierPrepaidBufferToYhglobalService,
                        yhglobalReceivedPrepaidAccountService);
                threadPoolExecutor.submit(task);
            }
            logger.info("#traceId={}# [OUT]: pay for purchase success.", rpcHeader.getTraceId());
            builder.setCode(rpcResult.getCode());
            builder.setMsg(rpcResult.getMessage());
            SupplierAccountServiceStructure.PurchaseFlowCollections.Builder flowBuilder;
            if (rpcResult.getSuccess()) {
                flowBuilder = SupplierAccountServiceStructure.PurchaseFlowCollections.newBuilder()
                        .setCouponFlowNoToDistributor(GrpcCommonUtil.getProtoParam(rpcResult.getResult().getCouponFlowNoToDistributor()))
                        .setPrepaidFlowNoToDistributor(GrpcCommonUtil.getProtoParam(rpcResult.getResult().getPrepaidFlowNoToDistributor()))
                        .setCouponFlowNoToYhglobal(GrpcCommonUtil.getProtoParam(rpcResult.getResult().getCouponFlowNoToYhglobal()))
                        .setPrepaidFlowNoToYhglobal(GrpcCommonUtil.getProtoParam(rpcResult.getResult().getPrepaidFlowNoToYhglobal()));
            } else {
                flowBuilder = SupplierAccountServiceStructure.PurchaseFlowCollections.newBuilder();
            }
            builder.setResult(flowBuilder);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectBufferCouponFlowList(SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowPageInfo response;
        PaymentCommonGrpc.FrontPageFlowPageInfo.Builder builder = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectBufferCouponFlowList] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader.getTraceId(), currencyCode, null, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(pageNum, pageSize);
            List<SupplierCouponBufferToDistributorFlow> list = supplierCouponBufferToDistributorFlowDao.selectBufferCouponFlowList(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
            PageInfo<SupplierCouponBufferToDistributorFlow> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (SupplierCouponBufferToDistributorFlow record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getFlowId() + "");
                pageFlow.setType(record.getFlowType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
                pageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreatedByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            pageInfo.setTotal(oldPageInfo.getTotal());
            logger.info("#traceId={}# [OUT]: get buffer coupon flow list success.", rpcHeader.getTraceId());
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
    public void selectBufferPrepaidFlowList(SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowPageInfo response;
        PaymentCommonGrpc.FrontPageFlowPageInfo.Builder builder = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectBufferPrepaidFlowList] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader.getTraceId(), currencyCode, null, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(pageNum, pageSize);
            List<SupplierPrepaidBufferToDistributorFlow> list = supplierPrepaidBufferToDistributorFlowDao.selectBufferPrepaidFlowList(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
            PageInfo<SupplierPrepaidBufferToDistributorFlow> oldPageInfo = new PageInfo<>(list);
            ArrayList<FrontPageFlow> frontPageFlows = new ArrayList<>();
            for (SupplierPrepaidBufferToDistributorFlow record : list) {
                FrontPageFlow pageFlow = new FrontPageFlow();
                pageFlow.setFlowNo(record.getFlowId() + "");
                pageFlow.setType(record.getFlowType());
                pageFlow.setCurrencyCode(record.getCurrencyCode());
                pageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
                pageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
                pageFlow.setCreateTime(record.getCreateTime());
                pageFlow.setCreateByName(record.getCreatedByName());
                frontPageFlows.add(pageFlow);
            }
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlows);
            pageInfo.setTotal(oldPageInfo.getTotal());
            logger.info("#traceId={}# [OUT]: get buffer prepaid flow list success.", rpcHeader.getTraceId());
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
    public void getCouponBufferToDistributorSubtotal(SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowSubtotal response;
        PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        logger.info("#traceId={}# [IN][getCouponBufferToDistributorSubtotal] params: currencyCode={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            List<FlowSubtotal> flowSubtotals = supplierCouponBufferToDistributorFlowDao.selectIncomeAndExpenditure(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
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


    @Override
    public void getPrepaidBufferToDistributorSubtotal(SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowSubtotal response;
        PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        logger.info("#traceId={}# [IN][getCouponBufferToDistributorSubtotal] params: currencyCode={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            List<FlowSubtotal> flowSubtotals = supplierPrepaidBufferToDistributorFlowDao.selectIncomeAndExpenditure(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
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


    @Override
    public void getSellHighAccount(SupplierAccountServiceStructure.GetSellHighAccountRequest request, StreamObserver<SupplierAccountServiceStructure.GetSellHighAccountResponse> responseObserver) {
        SupplierAccountServiceStructure.GetSellHighAccountResponse response;
        SupplierAccountServiceStructure.GetSellHighAccountResponse.Builder builder = SupplierAccountServiceStructure.GetSellHighAccountResponse.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        logger.info("#traceId={}# [IN][getSellHighAccount] params: currencyCode={}, projectId={}",
                rpcHeader.getTraceId(), currencyCode, projectId);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(prefix, projectId);
            account.setTotalAmount(account.getTotalAmount());
            account.setTotalAmountDouble(1.0 * account.getTotalAmount() / HUNDRED);
            logger.info("#traceId={}# [OUT] get account success.", rpcHeader.getTraceId());
            builder.setTotalAmount(account.getTotalAmount());
            builder.setTotalAmountDoubleStr(DoubleScale.keepTwoBits(account.getTotalAmount()));
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void selectSupplierSellHighFlowList(SupplierAccountServiceStructure.SelectSupplierAccountFlowListRequest request, StreamObserver<SupplierAccountServiceStructure.SelectSupplierSellHighFlowListResponse> responseObserver) {
        SupplierAccountServiceStructure.SelectSupplierSellHighFlowListResponse response;
        SupplierAccountServiceStructure.SelectSupplierSellHighFlowListResponse.Builder builder = SupplierAccountServiceStructure.SelectSupplierSellHighFlowListResponse.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectSupplierSellHighRecordList] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}, pageNum={}, pageSize={}",
                rpcHeader.getTraceId(), currencyCode, null, projectId, moneyFlow, beginDate, endDate, pageNum, pageSize);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(pageNum, pageSize);
            List<JmgoSupplierSellHighFlow> list = jmgoSupplierSellHighFlowDao.selectSupplierSellHighFlowList(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
            PageInfo<JmgoSupplierSellHighFlow> jmgoSupplierSellHighFlowPageInfo = new PageInfo<>(list);
            for (JmgoSupplierSellHighFlow record : list) {
                SupplierAccountServiceStructure.SelectSupplierSellHighFlow selectSupplierSellHighFlow = SupplierAccountServiceStructure.SelectSupplierSellHighFlow.newBuilder()
                        .setFlowNo(record.getFlowNo())
                        .setType(record.getFlowType())
                        .setCurrencyCode(record.getCurrencyCode())
                        .setTransactionAmount(DoubleScale.keepTwoBits(record.getTransactionAmount()))
                        .setAmountAfterTransaction(DoubleScale.keepTwoBits(record.getAmountAfterTransaction()))
                        .setCreateTime(record.getCreateTime().getTime())
                        .setCreateByName(record.getCreatedByName())
                        .setDistirbutorName(record.getDistributorName())
                        .setRemark(GrpcCommonUtil.getProtoParam(record.getRemark()))
                        .build();
                builder.addFlows(selectSupplierSellHighFlow);
            }
            builder.setTotal(jmgoSupplierSellHighFlowPageInfo.getTotal());
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public void getSellHighSubtotal(SupplierAccountServiceStructure.GetSupplierAccountSubtotalRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
        PaymentCommonGrpc.FrontPageFlowSubtotal response;
        PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        long projectId = request.getProjectId();
        int moneyFlow = request.getMoneyFlow();
        String beginDate = request.getBeginDate();
        String endDate = request.getEndDate();
        logger.info("#traceId={}# [IN][getSellHighSubtotal] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, null, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            List<FlowSubtotal> flowSubtotals = supplierSellHeightTransferRecordDao.selectIncomeAndExpenditure(prefix, currencyCode, projectId, moneyFlow, beginDate, endDate);
            FrontPageFlowSubtotal subtotal = AccountSubtotalUtil.getSubtotal(flowSubtotals);
            logger.info("#traceId={}# [OUT]: select record subtotal success.", rpcHeader.getTraceId());
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
    public void selectPrepaidBySupplierId(SupplierAccountServiceStructure.SelectPrepaidBySupplierIdRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowPageInfo> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        Long supplierId = request.getSupplierId();
        Long projectId = request.getProjectId();
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
        logger.info("#traceId={}# [IN][selectPrepaidFlow] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PaymentCommonGrpc.FrontPageFlowPageInfo.Builder builder = PaymentCommonGrpc.FrontPageFlowPageInfo.newBuilder();
            PaymentCommonGrpc.FrontPageFlowPageInfo response;
            PageHelper.startPage(pageNum, pageSize);
            List<SupplierPrepaidFlow> supplierCouponTransferRecordList = supplierPrepaidFlowDao.selectAllBySupplierId(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            PageInfo<SupplierPrepaidFlow> oldPageInfo = new PageInfo<>(supplierCouponTransferRecordList);
            List<FrontPageFlow> frontPageFlowList = new ArrayList<>();
            for (SupplierPrepaidFlow record : supplierCouponTransferRecordList) {
                FrontPageFlow frontPageFlow = generateFrontPageFlow(record);
                frontPageFlowList.add(frontPageFlow);
            }
            // 用分页包装
            PageInfo<FrontPageFlow> pageInfo = new PageInfo<>(frontPageFlowList);
            pageInfo.setTotal(oldPageInfo.getTotal());
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
    FrontPageFlow generateFrontPageFlow(SupplierPrepaidFlow record) {
        FrontPageFlow frontPageFlow = new FrontPageFlow();
        // 前台显示订单号
        frontPageFlow.setFlowNo(record.getRecordId() + "");
        frontPageFlow.setAmountAfterTransaction(1.0 * record.getAmountAfterTransaction() / HUNDRED);
        frontPageFlow.setCreateTime(record.getCreateTime());
        frontPageFlow.setCurrencyCode(record.getCurrencyCode());
        frontPageFlow.setTransactionAmount(1.0 * record.getTransactionAmount() / HUNDRED);
        frontPageFlow.setType(record.getFlowType());
        frontPageFlow.setCreateByName(record.getCreatedByName());
        return frontPageFlow;
    }

    @Override
    public void selectIncomeAndExpenditure(SupplierAccountServiceStructure.SelectIncomeAndExpenditureRequest request, StreamObserver<PaymentCommonGrpc.FrontPageFlowSubtotal> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String currencyCode = request.getCurrencyCode();
        Long supplierId = request.getSupplierId();
        Long projectId = request.getProjectId();
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
        logger.info("#traceId={}# [IN][getPrepaidSubtotal] params: currencyCode={}, supplierId={}, projectId={}, moneyFlow={}, beginDate={}, endDate={}",
                rpcHeader.getTraceId(), currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
        try {
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PaymentCommonGrpc.FrontPageFlowSubtotal.Builder builder = PaymentCommonGrpc.FrontPageFlowSubtotal.newBuilder();
            PaymentCommonGrpc.FrontPageFlowSubtotal response;
            FrontPageFlowSubtotal frontPageFlowSubtotal = new FrontPageFlowSubtotal();
            List<FlowSubtotal> flowSubtotals = supplierPrepaidFlowDao.selectIncomeAndExpenditure(prefix, currencyCode, supplierId, projectId, moneyFlow, beginDate, endDate);
            for (FlowSubtotal flowSubtotal : flowSubtotals) {
                if (YhGlobalInoutFlowConstant.FLOW_TYPE_IN.getNum() == flowSubtotal.getRecordType()) {
                    frontPageFlowSubtotal.setIncomeQuantity(flowSubtotal.getCount());
                    frontPageFlowSubtotal.setIncomeAmount(1.0 * flowSubtotal.getAmountCount() / HUNDRED);
                } else if (YhGlobalInoutFlowConstant.FLOW_TYPE_OUT.getNum() == flowSubtotal.getRecordType()) {
                    frontPageFlowSubtotal.setExpenditureQuantity(flowSubtotal.getCount());
                    frontPageFlowSubtotal.setExpenditureAmount(1.0 * flowSubtotal.getAmountCount() / HUNDRED);
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

    @Override
    public void updateSupplierSellHighAccountOnJmgo(SupplierAccountServiceStructure.UpdateSupplierSellHighAccountOnJmgoRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult rpcResult;
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        String currencyCode = request.getCurrencyCode();
        long distributorId = request.getDistributorId();
        String distributorName = request.getDistributorName();
        long amount = request.getAmount();
        String salesOrderNo = request.getSalesOrderNo();
        long transactionTime = request.getTransactionTime();
        String remark = request.getRemark();
        try {
            logger.info("#traceId={}# [IN][updateSupplierSellHeightTransferAccount] params: projectId={}, amount={}",
                    rpcHeader.getTraceId(), projectId, amount);
            // 获取表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            int maxRetryTimes = 6;
            String flowNo = null;
            boolean updateSuccess = false;
            long originalAmount = 0L;
            while (maxRetryTimes-- > 0) {
                //查找账户
                SupplierSellHeightAccount account = supplierSellHeightTransferAccountDao.getAccount(prefix, projectId);
                //改变余额
                originalAmount = account.getTotalAmount();
                long amountAfterTransaction = originalAmount + amount;
                if (amountAfterTransaction < 0) {
                    continue;
                }
                account.setTotalAmount(amountAfterTransaction);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), amount > 0 ? "转入" : "转出");
                String appendTraceLog = TraceLogUtil.appendTraceLog(account.getTracelog(), traceLog);
                account.setTracelog(appendTraceLog);
                //更新账户
                int update = supplierSellHeightTransferAccountDao.update(prefix, account);
                //插入记录
                if (update == 1) {
                    // 更新成功，插入流水,跳出循环
                    flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.PAYMENT_SUPPLIER_SELL_HIGH_FLOW);
                    JmgoSupplierSellHighFlow jmgoSupplierSellHighFlow = new JmgoSupplierSellHighFlow();
                    jmgoSupplierSellHighFlow.setCurrencyCode(currencyCode);
                    jmgoSupplierSellHighFlow.setAmountBeforeTransaction(originalAmount);
                    jmgoSupplierSellHighFlow.setTransactionAmount(amount);
                    jmgoSupplierSellHighFlow.setAmountAfterTransaction(originalAmount + amount);
                    jmgoSupplierSellHighFlow.setTransactionTime(new Date(transactionTime));
                    jmgoSupplierSellHighFlow.setProjectId(projectId);
                    jmgoSupplierSellHighFlow.setProjectName(account.getProjectName());
                    jmgoSupplierSellHighFlow.setDistributorId(distributorId);
                    jmgoSupplierSellHighFlow.setDistributorName(distributorName);
                    jmgoSupplierSellHighFlow.setSalesOrderNo(salesOrderNo);
                    jmgoSupplierSellHighFlow.setCreatedById(Long.parseLong(rpcHeader.getUid()));
                    jmgoSupplierSellHighFlow.setCreatedByName(rpcHeader.getUsername());
                    jmgoSupplierSellHighFlow.setFlowNo(flowNo);
                    jmgoSupplierSellHighFlow.setFlowType(amount > 0 ? FlowTypeEnum.IN.getType() : FlowTypeEnum.OUT.getType());
                    jmgoSupplierSellHighFlow.setRemark(remark);
                    jmgoSupplierSellHighFlowDao.insertFlow(prefix, jmgoSupplierSellHighFlow);
                    updateSuccess = true;
                    break;
                }
            }
            if (!updateSuccess) {
                //更新失败 账户余额不足
                logger.info("sell high account balance NOT enough");
                rpcResult = GrpcCommonUtil.fail(SELL_HIGH_ACCOUNT_BALANCE_NOT_ENOUGH);
            } else {
                //返回流水号
                logger.info("#traceId={}# [OUT]: update sell high account success. amount={} flowNo={}", rpcHeader.getTraceId(), amount, flowNo);
                rpcResult = GrpcCommonUtil.success();
            }
            responseObserver.onNext(rpcResult);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
