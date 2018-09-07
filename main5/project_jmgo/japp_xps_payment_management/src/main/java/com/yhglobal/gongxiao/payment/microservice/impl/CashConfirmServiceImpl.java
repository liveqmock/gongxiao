package com.yhglobal.gongxiao.payment.microservice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.protobuf.ProtocolStringList;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.id.DateTimeIdGenerator;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.dao.CashConfirmDao;
import com.yhglobal.gongxiao.payment.dao.CashLedgerDao;
import com.yhglobal.gongxiao.payment.microservice.CashConfirmSerivceStructure;
import com.yhglobal.gongxiao.payment.microservice.CashConfirmServiceGrpc;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import com.yhglobal.gongxiao.payment.model.CashConfirmStatusEnum;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import com.yhglobal.gongxiao.payment.service.DistributorCashAccountService;
import com.yhglobal.gongxiao.payment.service.processor.CashConfirmTransactionProcessor;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.ARGUMENTS_INVALID;
import static com.yhglobal.gongxiao.constant.ErrorCode.BANK_FLOW_NUMBER_REPEATED;
import static com.yhglobal.gongxiao.constant.ErrorCode.CASH_CONFIRM_CAN_NOT_CANCEL;

/**
 * @author: 葛灿
 */
@Service
public class CashConfirmServiceImpl extends CashConfirmServiceGrpc.CashConfirmServiceImplBase {
    private static Logger logger = LoggerFactory.getLogger(CashConfirmServiceImpl.class);
    @Autowired
    CashConfirmDao salesCashConfirmDao;

    @Autowired
    CashLedgerDao cashLedgerDao;

    @Autowired
    DistributorCashAccountService distributorCashAccountService;

    @Autowired
    CashConfirmTransactionProcessor cashConfirmTransactionProcessor;

    @Override
    public void createSalesOrderCashConfirmOrder(CashConfirmSerivceStructure.CreateSalesOrderCashConfirmOrderRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        // 拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long distributorId = request.getDistributorId();
        String distributorName = request.getDistributorName();
        long projectId = request.getProjectId();
        String projectName = request.getProjectName();
        String currencyCode = request.getCurrencyCode();
        long cashAmount = request.getCashAmount();
        long createTime = request.getCreateTime();
        logger.info("#traceId={}# [IN][createSalesOrderCashConfirmOrder] params: salesOrderNo={}, distributorId={}, distributorName={}, projectId={}, projectName={}, currencyCode={}, cashAmount={}, createTime={}",
                rpcHeader.getTraceId(), salesOrderNo, distributorId, distributorName, projectId, projectName, currencyCode, cashAmount, createTime);
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            CashConfirm cashConfirm = new CashConfirm();
            cashConfirm.setSalesOrderNo(salesOrderNo);
            cashConfirm.setFlowNo(null);
            cashConfirm.setRecipientStatus(false);
            cashConfirm.setConfirmStatus(CashConfirmStatusEnum.UNCONFIRM.getStatus());
            cashConfirm.setDistributorId(distributorId);
            cashConfirm.setDistributorName(distributorName);
            cashConfirm.setProjectId(projectId);
            cashConfirm.setProjectName(projectName);
            cashConfirm.setShouldReceiveCurrency(currencyCode);
            cashConfirm.setShouldReceiveAmount(cashAmount);
            cashConfirm.setUnreceiveCurrency(currencyCode);
            cashConfirm.setUnreceiveAmount(cashAmount);
            cashConfirm.setConfirmCurrency(currencyCode);
            cashConfirm.setConfirmAmount(0);
            cashConfirm.setRecipientAmount(0);
            cashConfirm.setRecipientCurrency(currencyCode);
            cashConfirm.setConfirmTime(null);
            cashConfirm.setReceiveTime(null);
            cashConfirm.setPayer(null);
            cashConfirm.setCreateTime(new Date());
            cashConfirm.setOrderTime(new Date(createTime));
//            com.yhglobal.gongxiao.common.TraceLog newTraceLog = new com.yhglobal.gongxiao.common.TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "新建");
//            String traceLogJson = TraceLogUtil.appendTraceLog(null, newTraceLog);
//            cashConfirm.setTracelog(traceLogJson);
            if (cashConfirm.getShouldReceiveAmount() == 0) {
                //如果为0,自动确认,插入审核后的台账数据
                String flowNo = DateTimeIdGenerator.nextId(prefix, BizNumberType.SALES_CASH_CONFIRM);
                cashConfirm.setFlowNo(flowNo);
                cashConfirm.setConfirmStatus(CashConfirmStatusEnum.CONFIRM.getStatus());
                cashConfirm.setRecipientStatus(true);
                cashConfirm.setConfirmTime(new Date());

                CashLedger cashLedger = new CashLedger();
                cashLedger.setFlowNo(flowNo);
                cashLedger.setApprovalStatus(true);
                cashLedger.setDistributorId(cashConfirm.getDistributorId());
                cashLedger.setDistributorName(cashConfirm.getDistributorName());
                cashLedger.setProjectId(cashConfirm.getProjectId());
                cashLedger.setProjectName(cashConfirm.getProjectName());
                cashLedger.setConfirmAmount(0L);
                cashLedger.setRecipientAmount(0L);
                cashLedger.setConfirmCurrency(cashConfirm.getConfirmCurrency());
                cashLedger.setRecipientCurrency(cashLedger.getRecipientCurrency());
                cashLedger.setClientName(cashConfirm.getDistributorName());
                cashLedger.setConfirmTime(new Date());
                cashLedger.setApproveTime(new Date());
                cashLedger.setApprovalUserName("系统");
                cashLedger.setCreateTime(new Date());
                cashLedger.setDataVersion(0L);
//                cashLedger.setTracelog(traceLogJson);
                cashLedger.setBankFlowNo("");
                int insert = cashLedgerDao.insert(prefix, cashLedger);
            }
            int insert = salesCashConfirmDao.insert(prefix, cashConfirm);
            logger.info("#traceId={}# [OUT]: confirm success", rpcHeader.getTraceId());
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void selectListSelective(CashConfirmSerivceStructure.SelectListSelectiveRequest request, StreamObserver<CashConfirmSerivceStructure.SelectListSelectiveResponse> responseObserver) {
        CashConfirmSerivceStructure.SelectListSelectiveResponse response;
        CashConfirmSerivceStructure.SelectListSelectiveResponse.Builder builder = CashConfirmSerivceStructure.SelectListSelectiveResponse.newBuilder();
        // 拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        long projectId = request.getProjectId();
        String distributorName = request.getDistributorName();
        String bankName = request.getBankName();
        String orderBegin = request.getOrderBegin();
        String orderEnd = request.getOrderEnd();
        String confirmBegin = request.getConfirmBegin();
        String confirmEnd = request.getConfirmEnd();
        String orderStatus = request.getOrderStatus();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        logger.info("#traceId={}# [IN][selectListSelective] params: salesOrderNo={}, projectId={}, distributorName={}, bankName={}, orderBegin={}, orderEnd={}, confirmBegin={}, confirmEnd={}, orderStatus={}",
                rpcHeader.getTraceId(), salesOrderNo, projectId, distributorName, bankName, orderBegin, orderEnd, confirmBegin, confirmEnd, orderStatus);
        PageInfo<CashConfirm> pageInfo;
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            String floatString;
            //查询用户信息
            PageHelper.startPage(pageNum, pageSize);
            List<CashConfirm> confirmList = salesCashConfirmDao.selectListSelectively(prefix, salesOrderNo, projectId, distributorName, bankName, orderBegin, orderEnd, confirmBegin, confirmEnd, orderStatus);
            for (CashConfirm record : confirmList) {
                floatString = DoubleScale.keepTwoBits(record.getConfirmAmount());
                record.setConfirmAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(record.getUnreceiveAmount());
                record.setUnreceiveAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(record.getShouldReceiveAmount());
                record.setShouldReceiveAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(record.getRecipientAmount());
                record.setRecipientAmountDouble(floatString);
            }
            pageInfo = new PageInfo<CashConfirm>(confirmList);
            logger.info("#traceId={}# [OUT]: get confirm list success", rpcHeader.getTraceId());
            builder.setPageNum(pageInfo.getPageNum());
            builder.setPageSize(pageInfo.getPageSize());
            builder.setTotal(pageInfo.getTotal());
            if (pageInfo.getList() != null) {
                for (CashConfirm javaObject : pageInfo.getList()) {
                    CashConfirmSerivceStructure.CashConfirmSelectiveResponse.Builder protoObject = CashConfirmSerivceStructure.CashConfirmSelectiveResponse.newBuilder()
                            .setConfirmId(javaObject.getConfirmId())
                            .setSalesOrderNo(javaObject.getSalesOrderNo())
                            .setRecipientStatus(javaObject.isRecipientStatus())
                            .setConfirmStatus(javaObject.getConfirmStatus())
                            .setDistributorName(javaObject.getDistributorName())
                            .setShouldReceiveAmountDouble(javaObject.getShouldReceiveAmountDouble())
                            .setUnreceiveAmountDouble(javaObject.getUnreceiveAmountDouble())
                            .setConfirmAmountDouble(javaObject.getConfirmAmountDouble())
                            .setRecipientAmountDouble(javaObject.getRecipientAmountDouble())
                            .setOrderTime(javaObject.getOrderTime().getTime())
                            .setBankFlowNo(GrpcCommonUtil.getProtoParam(javaObject.getBankFlowNo()));
                    if (javaObject.getFlowNo() != null) {
                        protoObject.setFlowNo(javaObject.getFlowNo());
                    }
                    if (javaObject.getBankName() != null) {
                        protoObject.setBankName(javaObject.getBankName());
                    }
                    if (javaObject.getConfirmTime() != null) {
                        protoObject.setConfirmTime(javaObject.getConfirmTime().getTime());
                    }
                    if (javaObject.getReceiveTime() != null) {
                        protoObject.setReceiveTime(javaObject.getReceiveTime().getTime());
                    }
                    if (javaObject.getBankName() != null) {
                        protoObject.setBankName(javaObject.getBankName());
                    }
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
    public void selectConfirmList(CashConfirmSerivceStructure.SelectConfirmListRequest request, StreamObserver<CashConfirmSerivceStructure.SelectConfirmListResponse> responseObserver) {
        CashConfirmSerivceStructure.SelectConfirmListResponse response;
        long projectId = request.getProjectId();
        CashConfirmSerivceStructure.SelectConfirmListResponse.Builder builder = CashConfirmSerivceStructure.SelectConfirmListResponse.newBuilder();
        // 拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        ProtocolStringList salesOrderNoListList = request.getSalesOrderNoListList();
        logger.info("#traceId={}# [IN][selectConfirmList] params: salesOrderNoList={}",
                rpcHeader.getTraceId(), salesOrderNoListList);
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            String floatString;
            List<CashConfirm> list = new ArrayList<>();
            for (String salesOrderNo : salesOrderNoListList) {
                CashConfirm cashConfirm = salesCashConfirmDao.getConfirmOrder(prefix, salesOrderNo);
                floatString = DoubleScale.keepTwoBits(cashConfirm.getUnreceiveAmount());
                cashConfirm.setUnreceiveAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(cashConfirm.getShouldReceiveAmount());
                cashConfirm.setShouldReceiveAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(cashConfirm.getUnreceiveAmount());
                cashConfirm.setConfirmAmountDouble(floatString);
                floatString = DoubleScale.keepTwoBits(cashConfirm.getUnreceiveAmount());
                cashConfirm.setRecipientAmountDouble(floatString);
                list.add(cashConfirm);
            }
            logger.info("#traceId={}# [OUT]: select list success", rpcHeader.getTraceId());
            for (CashConfirm javaObject : list) {
                CashConfirmSerivceStructure.CashConfirmResponse.Builder protoObject = CashConfirmSerivceStructure.CashConfirmResponse.newBuilder()
                        .setConfirmId(javaObject.getConfirmId())
                        .setSalesOrderNo(javaObject.getSalesOrderNo())
                        .setConfirmStatus(javaObject.getConfirmStatus())
                        .setShouldReceiveAmountDouble(javaObject.getShouldReceiveAmountDouble())
                        .setUnreceiveAmountDouble(javaObject.getUnreceiveAmountDouble())
                        .setConfirmAmountDouble(javaObject.getConfirmAmountDouble())
                        .setRecipientAmountDouble(javaObject.getRecipientAmountDouble())
                        .setCurrencyCode(javaObject.getShouldReceiveCurrency())
                        .setDataVersion(javaObject.getDataVersion());
                builder.addList(protoObject);
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
    public void confirmCash(CashConfirmSerivceStructure.ConfirmCashRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        // 拆分请求参数
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String bankName = request.getBankName();
        long recipientDate = request.getRecipientDate();
        String bankFlowNo = request.getBankFlowNo();
        String clientName = request.getClientName();
        String remark = request.getRemark();
        List<CashConfirmSerivceStructure.CashConfirmItems> itemsList = request.getItemsList();
        ArrayList<CashConfirm> items = new ArrayList<>();
        for (CashConfirmSerivceStructure.CashConfirmItems protoItem : itemsList) {
            CashConfirm cashConfirm = new CashConfirm();
            cashConfirm.setConfirmId(protoItem.getConfirmId());
            cashConfirm.setConfirmStatus(protoItem.getConfirmStatus());
            cashConfirm.setConfirmAmountDouble(protoItem.getConfirmAmountDouble());
            cashConfirm.setRecipientAmountDouble(protoItem.getRecipientAmountDouble());
            cashConfirm.setDataVersion(protoItem.getDataVersion());
            items.add(cashConfirm);
        }
        logger.info("#traceId={}# [IN][confirmCash] params: payer={}, recipientDate={}, items.size={}",
                rpcHeader.getTraceId(), bankName, recipientDate, items.size());
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            if (items.size() < 1) {
                logger.info("#traceId={}# [OUT]: list's length is ZERO !", rpcHeader.getTraceId());
                response = GrpcCommonUtil.fail(ARGUMENTS_INVALID);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            //查询银行流水号是否存在,存在则return
            if (!StringUtils.isEmpty(bankFlowNo)) {
                int count = salesCashConfirmDao.selectCountByBankFlowNo(prefix, bankFlowNo);
                if (count != 0) {
                    logger.info("#traceId={}# [OUT]: bankFlowNo already exist !", rpcHeader.getTraceId());
                    response = GrpcCommonUtil.fail(BANK_FLOW_NUMBER_REPEATED);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
            }
            //执行事务
            cashConfirmTransactionProcessor.cashConfirmTransaction(prefix, rpcHeader, bankName, GrpcCommonUtil.getJavaParam(bankFlowNo), new Date(recipientDate), items,
                    clientName, GrpcCommonUtil.getJavaParam(remark));

            logger.info("#traceId={}# [OUT]: confirm success", rpcHeader.getTraceId());
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (CloneNotSupportedException e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw new RuntimeException("clone not support");
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void cancelCashConfirm(CashConfirmSerivceStructure.CancelCashConfirmRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        // 拆分请求参数
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            logger.info("#traceId={}# [IN][cancelCashConfirm] params: salesOrderNo={}",
                    rpcHeader.getTraceId(), salesOrderNo);
            int maxRetryTimes = 6;
            CashConfirm confirmOrder = null;
            while (maxRetryTimes-- > 0) {
                //根据订单号，查找 未收金额=0 && 未作废 的确认单
                confirmOrder = salesCashConfirmDao.getConfirmOrder(prefix, salesOrderNo);
                if (confirmOrder == null || confirmOrder.getConfirmStatus() != CashConfirmStatusEnum.UNCONFIRM.getStatus()) {
                    //如果未找到 || 订单状态不为“未确认”，则说明无法取消
                    logger.info("#traceId={}# [OUT]: cancel cash confirm fail. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                    response = GrpcCommonUtil.fail(CASH_CONFIRM_CAN_NOT_CANCEL);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
                //操作日志添加
                TraceLog newTracelog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "作废");
                String traceLogJson = TraceLogUtil.appendTraceLog(confirmOrder.getTracelog(), newTracelog);
                confirmOrder.setTracelog(traceLogJson);
                //作废确认单
                int update = salesCashConfirmDao.cancelComfirmOrder(prefix, confirmOrder);
                if (update == 1) {
                    break;
                }
            }
            // 如果更新失败，抛出位置系统异常
            if (maxRetryTimes <= 0) {
                logger.error("FAILED to cancel cashConfirm. salesOrderNo={}", salesOrderNo);
                throw new RuntimeException("FAILED to cancel cashConfirm");
            }
            logger.info("#traceId={}# [OUT]: cancel cash confirm success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void confirmNegativeAmountAutomatically(CashConfirmSerivceStructure.ConfirmNegativeAmountAutomaticallyRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        // 拆分请求参数
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String salesOrderNo = request.getSalesOrderNo();
        logger.info("#traceId={}# [IN][confirmNegativeAmountAutomatically] params: salesOrderNo={}",
                rpcHeader.getTraceId(), salesOrderNo);
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            CashConfirm cashConfirm = salesCashConfirmDao.getConfirmOrder(prefix, salesOrderNo);
            //如果已经全部确认，无需再确认
            if (cashConfirm == null || cashConfirm.getConfirmStatus() != CashConfirmStatusEnum.PART_CONFIRM.getStatus()) {
                logger.info("#traceId={}# [OUT]: cancel cash confirm success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                response = GrpcCommonUtil.success();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            long unreceiveAmount = cashConfirm.getUnreceiveAmount();
            Date receiveTime = cashConfirm.getReceiveTime();
            //如果存在 未收金额>0 或者 没有审批收款 的情况，为账期收款，无需自动确认
            if (unreceiveAmount >= 0 || receiveTime == null) {
                logger.info("#traceId={}# [OUT]: cancel cash confirm success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
                response = GrpcCommonUtil.success();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            String automaticFlow = distributorCashAccountService.updateDistributorCashAccount(prefix, rpcHeader, cashConfirm.getUnreceiveCurrency(), cashConfirm.getDistributorId(), cashConfirm.getProjectId(), -unreceiveAmount, salesOrderNo, new Date(), null);
            List<CashConfirm> cashConfirms = salesCashConfirmDao.selectListBySalesOrderNo(prefix, salesOrderNo);
            for (CashConfirm confirm : cashConfirms) {
                confirm.setUnreceiveAmount(0L);
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "转入ad现金账户");
                String appendTraceLog = TraceLogUtil.appendTraceLog(confirm.getTracelog(), traceLog);
                int update = salesCashConfirmDao.updateWhenSystemConfirm(prefix, confirm.getConfirmId(), confirm.getDataVersion(), CashConfirmStatusEnum.CONFIRM.getStatus(),0L,appendTraceLog);
            }
            logger.info("#traceId={}# [OUT]: cancel cash confirm success. salesOrderNo={}", rpcHeader.getTraceId(), salesOrderNo);
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
