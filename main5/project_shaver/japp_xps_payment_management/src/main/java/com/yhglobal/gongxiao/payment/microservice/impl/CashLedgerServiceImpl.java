package com.yhglobal.gongxiao.payment.microservice.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.dao.CashConfirmDao;
import com.yhglobal.gongxiao.payment.dao.CashLedgerDao;
import com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure;
import com.yhglobal.gongxiao.payment.microservice.CashLedgerServiceGrpc;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import com.yhglobal.gongxiao.payment.model.CashConfirmStatusEnum;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderServiceStructure;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.constant.ErrorCode.DATE_VERSION_DIFFER;
import static com.yhglobal.gongxiao.constant.ErrorCode.LEDGER_CAN_NOT_CANCEL_APPROVE;

/**
 * @author: 葛灿
 */
@Service
public class CashLedgerServiceImpl extends CashLedgerServiceGrpc.CashLedgerServiceImplBase {

    private static Logger logger = LoggerFactory.getLogger(CashLedgerServiceImpl.class);
    @Autowired
    CashLedgerDao cashLedgerDao;
    @Autowired
    CashConfirmDao salesCashConfirmDao;


    @Override
    public void selectCashLedgerList(CashLedgerSerivceStructure.SelectCashLedgerListRequest request, StreamObserver<CashLedgerSerivceStructure.SelectCashLedgerListResponse> responseObserver) {
        CashLedgerSerivceStructure.SelectCashLedgerListResponse response;
        CashLedgerSerivceStructure.SelectCashLedgerListResponse.Builder builder = CashLedgerSerivceStructure.SelectCashLedgerListResponse.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        long projectId = request.getProjectId();
        String bankName = request.getBankName();
        String flowNo = request.getFlowNo();
        String confirmBegin = request.getConfirmBegin();
        String confirmEnd = request.getConfirmEnd();
        String receiveBegin = request.getReceiveBegin();
        String receiveEnd = request.getReceiveEnd();
        String approveStatus = request.getApproveStatus();
        int pageNum = request.getPageNum();
        int pageSize = request.getPageSize();
        String clientName = request.getClientName();
        logger.info("#traceId={}# [IN][selectCashLedgerList] params: projectId={}, bankName={}, flowNo={}, confirmBegin={}, confirmEnd={}, receiveBegin={}, receiveEnd={}, approveStatus={}, pageNum={}, pageSize={}",
                rpcHeader.getTraceId(), projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus, pageNum, pageSize);
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            PageHelper.startPage(pageNum, pageSize);
            List<CashLedger> list = cashLedgerDao.selectCashLedgerList(prefix, projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus, clientName);
            for (CashLedger cashLedger : list) {
                String confirmAmount = DoubleScale.keepTwoBits(cashLedger.getConfirmAmount());
                cashLedger.setConfirmAmountDouble(confirmAmount);
                String recipientAmount = DoubleScale.keepTwoBits(cashLedger.getRecipientAmount());
                cashLedger.setRecipientAmountDouble(recipientAmount);
            }
            PageInfo<CashLedger> pageInfo = new PageInfo<>(list);
            logger.info("#traceId={}# [OUT]: select success.", rpcHeader.getTraceId());
            builder.setPageNum(pageInfo.getPageNum());
            builder.setPageSize(pageInfo.getPageSize());
            builder.setTotal(pageInfo.getTotal());
            if (pageInfo.getList() != null) {
                for (CashLedger javaObject : pageInfo.getList()) {
                    CashLedgerSerivceStructure.CashLedgerResponse.Builder protoObject = CashLedgerSerivceStructure.CashLedgerResponse.newBuilder()
                            .setFlowNo(javaObject.getFlowNo())
                            .setApprovalStatus(javaObject.isApprovalStatus())
                            .setDistributorName(javaObject.getDistributorName())
                            .setConfirmAmountDoubleStr(javaObject.getConfirmAmountDouble())
                            .setRecipientAmountDoubleStr(javaObject.getRecipientAmountDouble())
                            .setConfirmTime(javaObject.getConfirmTime().getTime())
                            .setReceiveTime(GrpcCommonUtil.getProtoParam(javaObject.getReceiveTime()))
                            .setBankName(GrpcCommonUtil.getProtoParam(javaObject.getBankName()))
                            .setBankFlowNo(GrpcCommonUtil.getProtoParam(javaObject.getBankFlowNo()))
                            .setClientName(GrpcCommonUtil.getProtoParam(javaObject.getClientName()))
                            .setCreatedByName(GrpcCommonUtil.getProtoParam(javaObject.getCreatedByName()))
                            .setProjectName(javaObject.getProjectName());
                    if (javaObject.getApprovalUserName() != null) {
                        protoObject.setApprovalUserName(javaObject.getApprovalUserName());
                    }
                    if (javaObject.getApproveTime() != null) {
                        protoObject.setApproveTime(javaObject.getApproveTime().getTime());
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
    public void cancelConfirm(CashLedgerSerivceStructure.CashLedgerRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        long projectId = request.getProjectId();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String flowNo = request.getFlowNo();
        logger.info("#traceId={}# [IN][cancelConfirm] params: flowNo={}",
                rpcHeader.getTraceId(), flowNo);
        int update;
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            // 查询能否确认
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(prefix, flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            boolean canceled = cashLedger.isCanceled();
            if (approvalStatus || canceled) {
                logger.info("#traceId={}# [OUT]: flowNo '{}' cancel FAILED!", rpcHeader.getTraceId(), flowNo);
                response = GrpcCommonUtil.fail(DATE_VERSION_DIFFER);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            cashLedger.setCanceled(true);
            update = cashLedgerDao.updateWhenCancelConfirm(prefix, cashLedger.getLedgerId(), cashLedger.getDataVersion(), true);
            //根据流水号，找到对应的现金确认list，作废
            List<CashConfirm> cashConfirms = salesCashConfirmDao.selectConfirmListByFlowNo(prefix, flowNo);
            for (CashConfirm cashConfirm : cashConfirms) {
                String salesOrderNo = cashConfirm.getSalesOrderNo();
                //如果只存在一条，修改原单数据
                int count = salesCashConfirmDao.getUncanceledCountBySalesOrderNo(prefix, salesOrderNo);
                if (count == 1) {
//                    cashConfirm.setConfirmStatus(CashConfirmStatusEnum.UNCONFIRM.getStatus());
//                    cashConfirm.setUnreceiveAmount(cashConfirm.getUnreceiveAmount() + cashConfirm.getConfirmAmount());
//                    cashConfirm.setRecipientStatus(false);
//                    cashConfirm.setConfirmAmount(0);
//                    cashConfirm.setRecipientAmount(0);
//                    cashConfirm.setFlowNo(null);
//                    cashConfirm.setConfirmTime(null);
//                    cashConfirm.setBankName(null);
//                    cashConfirm.setReceiveTime(null);
//                    cashConfirm.setBankFlowNo(null);
                    TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "作废");
                    String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                    cashConfirm.setTracelog(appendTraceLog);
                    update = salesCashConfirmDao.updateWhenConfirm(prefix, cashConfirm.getConfirmId(), cashConfirm.getDataVersion(), CashConfirmStatusEnum.UNCONFIRM.getStatus(),
                            0, 0, cashConfirm.getUnreceiveAmount() + cashConfirm.getConfirmAmount(), null,
                            null, null, null, null, null, null, appendTraceLog);
                } else {
                    salesCashConfirmDao.cancelComfirmOrder(prefix, cashConfirm);
                    if (cashConfirm.getUnreceiveAmount() == 0) {
                        //如果未收款金额=0，新插入一条待确认记录
                        CashConfirm clone = cashConfirm.clone();
                        clone.setConfirmId(null);
                        clone.setConfirmAmount(0);
                        clone.setRecipientAmount(0);
                        clone.setFlowNo(null);
                        clone.setBankName(null);
                        clone.setConfirmTime(null);
                        clone.setReceiveTime(null);
                        clone.setBankFlowNo(null);
                        clone.setCreateTime(new Date());
                        //操作日志
                        TraceLog newTraceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消后新增");
                        String traceLogJson = TraceLogUtil.appendTraceLog(null, newTraceLog);
                        clone.setTracelog(traceLogJson);
                        salesCashConfirmDao.insert(prefix, clone);
                    }
                }
                //回滚其他应收单的“未收金额”，如果未收==应收，状态改为未确认，否则，状态改为部分确认
                long confirmAmount = cashConfirm.getConfirmAmount();
                long unreceivedAmount = cashConfirm.getUnreceiveAmount() + confirmAmount;
                CashConfirmStatusEnum confirmStatus;
                if (unreceivedAmount == cashConfirm.getShouldReceiveAmount()) {
                    confirmStatus = CashConfirmStatusEnum.UNCONFIRM;
                } else {
                    confirmStatus = CashConfirmStatusEnum.PART_CONFIRM;
                }
                //修改这个销售单相关的所有收款确认，修改未收金额和收款状态
                salesCashConfirmDao.updateBySalesOrder(prefix, salesOrderNo, confirmStatus.getStatus(), unreceivedAmount);
            }
            logger.info("#traceId={}# [OUT]: cancel confirm success. flowNo={}", rpcHeader.getTraceId(), flowNo);
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            try {
                throw e;
            } catch (CloneNotSupportedException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void approveLedger(CashLedgerSerivceStructure.CashLedgerRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        //拆分请求参数
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String flowNo = request.getFlowNo();
        logger.info("#traceId={}# [IN][approveLedger] params: flowNo={}",
                rpcHeader.getTraceId(), flowNo);
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //遍历流水号，分别更新各个信息
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(prefix, flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            if (approvalStatus) {
                //数据校验，如果该单已审批，无法再次审批
                response = GrpcCommonUtil.fail(DATE_VERSION_DIFFER);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            cashLedger.setApprovalStatus(true);
            cashLedger.setApproveTime(new Date());
            cashLedger.setApprovalUserId(Long.parseLong(rpcHeader.getUid()));
            cashLedger.setApprovalUserName(rpcHeader.getUsername());
            int update = cashLedgerDao.updateWhenApprove(prefix, cashLedger.getLedgerId(), cashLedger.getDataVersion(), true, new Date(), Long.parseLong(rpcHeader.getUid()), rpcHeader.getUsername());
            //根据流水号查找对应的收款确认订单list
            List<CashConfirm> cashConfirmList = salesCashConfirmDao.selectConfirmListByFlowNo(prefix, flowNo);
            for (CashConfirm cashConfirm : cashConfirmList) {
                //遍历时，如果未收金额<=0，改变收款状态为true
                long unreceivedAmount = cashConfirm.getUnreceiveAmount();
                boolean recipientStatus = false;
                if (unreceivedAmount <= 0) {
                    //前往销售模块，修改对应销售单的收款状态
                    SalesOrderServiceGrpc.SalesOrderServiceBlockingStub salesOrderService = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
                    SalesOrderServiceStructure.CommonSalesOrderRequest rpcResponse = SalesOrderServiceStructure.CommonSalesOrderRequest
                            .newBuilder()
                            .setProjectId(projectId)
                            .setRpcHeader(rpcHeader)
                            .setSalesOrderNo(cashConfirm.getSalesOrderNo())
                            .build();
                    GongxiaoRpc.RpcResult rpcResult = salesOrderService.confirmSalesOrderAmount(rpcResponse);
                    recipientStatus = true;
                }
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "收款审批");
                String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                update = salesCashConfirmDao.updateWhenApprove(prefix, cashConfirm.getConfirmId(), cashConfirm.getDataVersion(), recipientStatus, appendTraceLog);
            }
            logger.info("#traceId={}# [OUT]: approve success. flowNo={}", rpcHeader.getTraceId(), flowNo);
            response = GrpcCommonUtil.success();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void cancelApproveLedger(CashLedgerSerivceStructure.CashLedgerRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        //拆分请求参数
        long projectId = request.getProjectId();
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String flowNo = request.getFlowNo();
        logger.info("#traceId={}# [IN][cancelApproveLedger] params: flowNo={}",
                rpcHeader.getTraceId(), flowNo);
        try {
            // 查询表前缀
            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);

            //数据校验
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(prefix, flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            //如果未审批，无法取消审批
            if (!approvalStatus) {
                logger.info("#traceId={}# [OUT]:  flowNo'{}' can NOT cancel .", rpcHeader.getTraceId(), flowNo);
                response = GrpcCommonUtil.fail(DATE_VERSION_DIFFER);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            //如果有销售单已经进行发货，无法取消审批
            List<CashConfirm> cashConfirmList = salesCashConfirmDao.selectConfirmListByFlowNo(prefix, flowNo);
            for (CashConfirm cashConfirm : cashConfirmList) {
                String salesOrderNo = cashConfirm.getSalesOrderNo();
                //rpc调用
                SalesOrderServiceStructure.CommonSalesOrderRequest commonSalesOrderRequest = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                        .setProjectId(projectId)
                        .setRpcHeader(rpcHeader).setSalesOrderNo(salesOrderNo).build();
                SalesOrderServiceGrpc.SalesOrderServiceBlockingStub salesOrderService = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
                SalesOrderServiceStructure.WhetherOutboundResponse whetherOutboundResponse = salesOrderService.whetherOutbound(commonSalesOrderRequest);
                boolean whetherOutbound = whetherOutboundResponse.getOutbound();
                //如果存在已经发货的订单，无法取消
                if (whetherOutbound) {
                    logger.info("#traceId={}# [OUT]:  flowNo'{}' can NOT cancel .", rpcHeader.getTraceId(), flowNo);
                    response = GrpcCommonUtil.fail(LEDGER_CAN_NOT_CANCEL_APPROVE);
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
            }

            //更改台账信息
            cashLedger.setApprovalStatus(false);
            cashLedger.setApproveTime(null);
            cashLedger.setApprovalUserId(null);
            cashLedger.setApprovalUserName(null);
            //添加操作日志
            TraceLog traceLog2 = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消审批");
            String appendTraceLog2 = TraceLogUtil.appendTraceLog(cashLedger.getTracelog(), traceLog2);
            cashLedger.setTracelog(appendTraceLog2);
            for (CashConfirm cashConfirm : cashConfirmList) {
                //销售单修改状态为未收款
                String salesOrderNo = cashConfirm.getSalesOrderNo();
                //销售调用
                SalesOrderServiceStructure.CommonSalesOrderRequest commonSalesOrderRequest = SalesOrderServiceStructure.CommonSalesOrderRequest.newBuilder()
                        .setProjectId(projectId)
                        .setRpcHeader(rpcHeader).setSalesOrderNo(salesOrderNo).build();
                SalesOrderServiceGrpc.SalesOrderServiceBlockingStub salesOrderService = RpcStubStore.getRpcStub(SalesOrderServiceGrpc.SalesOrderServiceBlockingStub.class);
                GongxiaoRpc.RpcResult rpcResult = salesOrderService.cancelReceivedCash(commonSalesOrderRequest);
                boolean success = rpcResult.getReturnCode() == 0;
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消审批");
                String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                int update = salesCashConfirmDao.updateWhenApprove(prefix, cashConfirm.getConfirmId(), cashConfirm.getDataVersion(), false, appendTraceLog);
            }
            int update = cashLedgerDao.updateWhenApprove(prefix, cashLedger.getLedgerId(), cashLedger.getDataVersion(), false, null, null, null);
            if (update == 1) {
                logger.info("#traceId={}# [OUT]: flowNo{} cancel success.", rpcHeader.getTraceId(), flowNo);
                response = GrpcCommonUtil.success();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                logger.error("#traceId={}# [OUT]: flowNo{} cancel FAILED.", rpcHeader.getTraceId(), flowNo);
                response = GrpcCommonUtil.fail(DATE_VERSION_DIFFER);
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
