package com.yhglobal.gongxiao.payment.jdservice;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.payment.dao.CashConfirmDao;
import com.yhglobal.gongxiao.payment.dao.CashLedgerDao;
import com.yhglobal.gongxiao.payment.microservice.CashLedgerSerivceStructure;
import com.yhglobal.gongxiao.payment.microservice.CashLedgerServiceGrpc;
import com.yhglobal.gongxiao.payment.model.CashConfirm;
import com.yhglobal.gongxiao.payment.model.CashConfirmStatusEnum;
import com.yhglobal.gongxiao.payment.model.CashLedger;
import com.yhglobal.gongxiao.util.DoubleScale;
import com.yhglobal.gongxiao.util.TraceLogUtil;
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
        logger.info("#traceId={}# [IN][selectCashLedgerList] params: projectId={}, bankName={}, flowNo={}, confirmBegin={}, confirmEnd={}, receiveBegin={}, receiveEnd={}, approveStatus={}, pageNum={}, pageSize={}",
                rpcHeader.getTraceId(), projectId, bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus, pageNum, pageSize);
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<CashLedger> list = cashLedgerDao.selectCashLedgerList(projectId + "", bankName, flowNo, confirmBegin, confirmEnd, receiveBegin, receiveEnd, approveStatus);
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
                            .setReceiveTime(javaObject.getReceiveTime().getTime())
                            .setBankName(javaObject.getBankName())
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
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String flowNo = request.getFlowNo();
        logger.info("#traceId={}# [IN][cancelConfirm] params: flowNo={}",
                rpcHeader.getTraceId(), flowNo);
        try {
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            boolean canceled = cashLedger.isCanceled();
            if (approvalStatus || canceled) {
                logger.info("#traceId={}# [OUT]: flowNo '{}' cancel fail", rpcHeader.getTraceId(), flowNo);
                builder.setMsg(DATE_VERSION_DIFFER.getMessage());
                builder.setReturnCode(DATE_VERSION_DIFFER.getErrorCode());
                response = builder.build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            cashLedger.setCanceled(true);
            int update = cashLedgerDao.update(cashLedger);
            //根据流水号，找到对应的现金确认list，作废
            List<CashConfirm> cashConfirms = salesCashConfirmDao.selectConfirmListByFlowNo(flowNo);
            for (CashConfirm cashConfirm : cashConfirms) {
                String salesOrderNo = cashConfirm.getSalesOrderNo();
                //如果只存在一条，修改原单数据
                cashConfirm.setConfirmStatus(CashConfirmStatusEnum.UNCONFIRM.getStatus());
                cashConfirm.setUnreceiveAmount(cashConfirm.getUnreceiveAmount() + cashConfirm.getConfirmAmount());
                cashConfirm.setConfirmAmount(0);
                cashConfirm.setRecipientAmount(0);
                cashConfirm.setFlowNo(null);
                cashConfirm.setConfirmTime(null);
                cashConfirm.setBankName(null);
                cashConfirm.setReceiveTime(null);
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "作废");
                String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                cashConfirm.setTracelog(appendTraceLog);
                int update1 = salesCashConfirmDao.update(cashConfirm);
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
                salesCashConfirmDao.updateBySalesOrder(salesOrderNo, confirmStatus.getStatus(), unreceivedAmount);
            }
            logger.info("#traceId={}# [OUT]: cancel confirm success. flowNo={}", rpcHeader.getTraceId(), flowNo);
            builder.setMsg("");
            builder.setReturnCode(0);
            response = builder.build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void approveLedger(CashLedgerSerivceStructure.CashLedgerRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcResult response;
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String flowNo = request.getFlowNo();
        logger.info("#traceId={}# [IN][approveLedger] params: flowNo={}",
                rpcHeader.getTraceId(), flowNo);
        try {
            //遍历流水号，分别更新各个信息
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            if (approvalStatus) {
                //数据校验，如果该单已审批，无法再次审批
                builder.setMsg(DATE_VERSION_DIFFER.getMessage());
                builder.setReturnCode(DATE_VERSION_DIFFER.getErrorCode());
                response = builder.build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            cashLedger.setApprovalStatus(true);
            cashLedger.setApproveTime(new Date());
            cashLedger.setReceiveTime(new Date());
            cashLedger.setApprovalUserId(Long.parseLong(rpcHeader.getUid()));
            cashLedger.setApprovalUserName(rpcHeader.getUsername());
            int update = cashLedgerDao.update(cashLedger);
            //根据流水号查找对应的收款确认订单list
            List<CashConfirm> cashConfirmList = salesCashConfirmDao.selectConfirmListByFlowNo(flowNo);
            for (CashConfirm cashConfirm : cashConfirmList) {
                //遍历时，如果未收金额<=0，改变收款状态为true
                long unreceiveAmount = cashConfirm.getUnreceiveAmount();
                if (unreceiveAmount <= 0) {
                    //前往销售模块，修改对应销售单的收款状态 todo rpc调用
//                    RpcResult rpcResult = salesOrderService.confirmSalesOrderAmount(rpcHeader, cashConfirm.getSalesOrderNo());
                    cashConfirm.setRecipientStatus(true);
                }
                cashConfirm.setReceiveTime(new Date());
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "收款审批");
                String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                cashConfirm.setTracelog(appendTraceLog);
                int update1 = salesCashConfirmDao.update(cashConfirm);
            }
            logger.info("#traceId={}# [OUT]: approve success. flowNo={}", rpcHeader.getTraceId(), flowNo);
            builder.setMsg("");
            builder.setReturnCode(0);
            response = builder.build();
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
        GongxiaoRpc.RpcResult.Builder builder = GongxiaoRpc.RpcResult.newBuilder();
        //拆分请求参数
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        String flowNo = request.getFlowNo();
        logger.info("#traceId={}# [IN][cancelApproveLedger] params: flowNo={}",
                rpcHeader.getTraceId(), flowNo);
        try {
            //数据校验
            CashLedger cashLedger = cashLedgerDao.getByFlowNo(flowNo);
            boolean approvalStatus = cashLedger.isApprovalStatus();
            //如果未审批，无法取消审批
            if (!approvalStatus) {
                logger.info("#traceId={}# [OUT]:  flowNo'{}' can NOT cancel .", rpcHeader.getTraceId(), flowNo);
                builder.setMsg(DATE_VERSION_DIFFER.getMessage());
                builder.setReturnCode(DATE_VERSION_DIFFER.getErrorCode());
                response = builder.build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                return;
            }
            //如果有销售单已经进行发货，无法取消审批
            ArrayList<String> salesOrderNoList = new ArrayList<>();
            List<CashConfirm> cashConfirmList = salesCashConfirmDao.selectConfirmListByFlowNo(flowNo);
            for (CashConfirm cashConfirm : cashConfirmList) {
                String salesOrderNo = cashConfirm.getSalesOrderNo();
                //todo rpc调用
//                boolean whetherOutbound = salesOrderService.whetherOutbound(rpcHeader, salesOrderNo);
                boolean whetherOutbound = true;
                //如果存在已经发货的订单，无法取消
                if (whetherOutbound) {
                    logger.info("#traceId={}# [OUT]:  flowNo'{}' can NOT cancel .", rpcHeader.getTraceId(), flowNo);
                    builder.setMsg(LEDGER_CAN_NOT_CANCEL_APPROVE.getMessage());
                    builder.setReturnCode(LEDGER_CAN_NOT_CANCEL_APPROVE.getErrorCode());
                    response = builder.build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                    return;
                }
            }

            //更改台账信息，分别更新各个信息
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
                //todo rpc调用
//                boolean success = salesOrderService.cancelReceivedCash(rpcHeader, salesOrderNo).getSuccess();
                //修改收款状态、收款时间
                cashConfirm.setReceiveTime(null);
                cashConfirm.setRecipientStatus(false);
                //添加操作日志
                TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "取消审批");
                String appendTraceLog = TraceLogUtil.appendTraceLog(cashConfirm.getTracelog(), traceLog);
                cashConfirm.setTracelog(appendTraceLog);
                int update1 = salesCashConfirmDao.update(cashConfirm);
            }
            int update = cashLedgerDao.update(cashLedger);
            if (update == 1) {
                logger.info("#traceId={}# [OUT]: flowNo{} cancel success.", rpcHeader.getTraceId(), flowNo);
                builder.setMsg("");
                builder.setReturnCode(0);
                response = builder.build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            } else {
                logger.error("#traceId={}# [OUT]: flowNo{} cancel FAILED.", rpcHeader.getTraceId(), flowNo);
                builder.setMsg(DATE_VERSION_DIFFER.getMessage());
                builder.setReturnCode(DATE_VERSION_DIFFER.getErrorCode());
                response = builder.build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
