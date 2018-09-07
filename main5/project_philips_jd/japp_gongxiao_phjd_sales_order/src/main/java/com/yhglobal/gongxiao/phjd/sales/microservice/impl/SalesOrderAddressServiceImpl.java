package com.yhglobal.gongxiao.phjd.sales.microservice.impl;

import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.constant.UpdateStatus;
import com.yhglobal.gongxiao.constant.sales.SalesOrderStatusEnum;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOrderAddressDao;
import com.yhglobal.gongxiao.phjd.sales.dao.SalesOrderDao;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOrder;
import com.yhglobal.gongxiao.phjd.sales.model.SalesOrderAddressDO;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceGrpc;
import com.yhglobal.gongxiao.sales.microservice.SalesOrderAddressServiceStructure;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 销售订单地址相关GRPC
 *
 * @author weizecheng
 * @date 2018/8/28 11:20
 */
@Service
public class SalesOrderAddressServiceImpl  extends SalesOrderAddressServiceGrpc.SalesOrderAddressServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(SalesOrderAddressServiceImpl.class);

    @Autowired
    private SalesOrderDao salesOrderDao;
    @Autowired
    private SalesOrderAddressDao salesOrderAddressDao;
    /**
     * 销售地址更新
     *
     * @author weizecheng
     * @date 2018/8/28 11:21
     */
    @Override
    public void updateRecipientInfo(SalesOrderAddressServiceStructure.UpdateShippingAddressRequest request, StreamObserver<GongxiaoRpc.RpcResult> responseObserver) {
        GongxiaoRpc.RpcHeader rpcHeader = request.getRpcHeader();
        // 销售订单Id
        Long salesOrderId = request.getSalesOrderId();
        // 详细收货地址
        String receivingAddress =request.getReceivingAddress();
        // 收货电话
        String receiverTel =request.getReceiverTel();
        // 收货人
        String receiver =  request.getReceiver();
        // 收货最终抵达地
        String arrived = request.getArrived();
        // 项目Id
        long projectId = request.getProjectId();
        //创建结果对象，创建转换对象，创建Builder
        GongxiaoRpc.RpcResult rpcResult;
        try{
            logger.info("#traceId={}# [IN][updateRecipientInfo] params: salesOrderId={}, receivingAddress={}, receiverTel={},receiver={},arrived={} }",
                    rpcHeader.getTraceId(),
                    salesOrderId,
                    receivingAddress,
                    receiverTel,
                    receiver,
                    arrived
                    );
            // 0.查询表前缀
//            String prefix = TablePrefixUtil.getTablePrefixByProjectId(rpcHeader, projectId);
            String prefix ="phjd";
            SalesOrder salesOrder = salesOrderDao.getSalesOrderById(prefix,salesOrderId);
            SalesOrderAddressDO salesOrderAddressDO = salesOrderAddressDao.getAddressBySalesOrderId(prefix,salesOrderId);
            Integer update;
            if(salesOrder != null && salesOrderAddressDO != null){
                if(!SalesOrderStatusEnum.INIT.getStatus().equals(salesOrder.getSalesOrderStatus())){
                    rpcResult = GrpcCommonUtil.fail(ErrorCode.SALES_ORDER_STATUS_ERRO);
                    responseObserver.onNext(rpcResult);
                    responseObserver.onCompleted();
                    return;
                }
                // 更新销售订单地址
                update = salesOrderAddressDao.updateSalesOrderAddress(prefix,salesOrderAddressDO.getDataVersion(),salesOrderAddressDO.getId(),receivingAddress,receiverTel,receiver,arrived);
                if(UpdateStatus.UPDATE_SUCCESS.getStatus().equals(update)){
                    TraceLog traceLog = new TraceLog(System.currentTimeMillis(), rpcHeader.getUid(), rpcHeader.getUsername(), "修改收件人信息");
                    String traceLogs = TraceLogUtil.appendTraceLog(salesOrder.getTracelog(), traceLog);
                    // 更新操作日志
                    salesOrderDao.updateTracelog(prefix,salesOrder.getDataVersion(),salesOrder.getSalesOrderId(),traceLogs);
                }
            if (UpdateStatus.UPDATE_FAIL.getStatus().equals(update)) {
                logger.error("FAILED to update salesOrder recipient info. salesOrder={}", salesOrder.getSalesOrderNo());
                throw new RuntimeException("FAILED to update salesOrder recipient info");
            }
            logger.info("#traceId={}# [OUT]: update recipient info success. orderNo={}", rpcHeader.getTraceId(), salesOrder.getSalesOrderNo());
            }
            //model -> rpcModel
            rpcResult = GrpcCommonUtil.success();
            responseObserver.onNext(rpcResult);
            responseObserver.onCompleted();
        }catch (Exception e){
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }
}
