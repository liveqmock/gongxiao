package com.yhglobal.gongxiao.warehouse.service.impl;

import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.TraceLog;
import com.yhglobal.gongxiao.type.InboundOrderStatusEnum;
import com.yhglobal.gongxiao.util.TraceLogUtil;
import com.yhglobal.gongxiao.warehouse.dao.InBoundOrderDao;
import com.yhglobal.gongxiao.warehouse.dao.InboundOrderItemDao;
import com.yhglobal.gongxiao.warehouse.model.InboundOrder;
import com.yhglobal.gongxiao.warehouse.model.InboundOrderItem;
import com.yhglobal.gongxiao.warehouse.service.ModifyInboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ModifyInboundServiceImpl implements ModifyInboundService {
    private Logger logger = LoggerFactory.getLogger(ModifyInboundServiceImpl.class);

    @Autowired
    InBoundOrderDao inBoundOrderDao;

    @Autowired
    InboundOrderItemDao inboundOrderItemDao;


    //更新明细表
    public int modifyInboundItermByWms(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoInBoundOrderNo, String productCode, int imperfectQuantity, int inStockQuantity, int inTransitQuantity, int realInStockQuantity) {
        logger.info("#traceId={}# [IN][modifyInboundItermByWms] params: gongxiaoInBoundOrderNo={},productCode={},imperfectQuantity={},inStockQuantity={},inTransitQuantity={},realInStockQuantity={}", rpcHeader.getTraceId(),gongxiaoInBoundOrderNo,productCode,imperfectQuantity,inStockQuantity,inTransitQuantity,realInStockQuantity);
        try {
            //调用基础模块的项目的grpc查询项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
            ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = rpcResponse.getProject();
            String prefix = project.getProjectTablePrefix();
            InboundOrderItem order = inboundOrderItemDao.selectOrderItemByNo(gongxiaoInBoundOrderNo,productCode,prefix);
            int afterImperfectQuantity = order.getImperfectQuantity()+imperfectQuantity;
            int afterInStockQuantity = order.getInStockQuantity()+inStockQuantity;
            int afterInTransitQuantity = order.getInTransitQuantity()+inTransitQuantity;
            int afterRealInStockQuantity = order.getRealInStockQuantity()+realInStockQuantity;
            int i = inboundOrderItemDao.updateInStorageDetailInfo(order.getRowId(),afterImperfectQuantity,afterInStockQuantity,afterInTransitQuantity,afterRealInStockQuantity,prefix);
            logger.info("#traceId={}# [OUT] get modifyInboundItermByWms success: inboundOrder={}", rpcHeader.getTraceId(), gongxiaoInBoundOrderNo);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 特别注意：本函数的返回值为 更新后入库单是否已收货完成
     */
    public boolean modifyInboundOderByWms(GongxiaoRpc.RpcHeader rpcHeader, String projectId, String gongxiaoInboundOrderNo, int realInStockQuantity, int imperfectQuantity, int inStockQuantity, int inTransitQuantity) {
        logger.info("#traceId={}# [IN][modifyInboundOderByWms] params: gongxiaoInboundOrderNo={},realInStockQuantity={},imperfectQuantity={},inStockQuantity={},inTransitQuantity={}", rpcHeader.getTraceId(), gongxiaoInboundOrderNo,realInStockQuantity,imperfectQuantity,inStockQuantity,inTransitQuantity);
        //调用基础模块的项目的grpc查询项目信息
        ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
        ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder().setRpcHeader(rpcHeader).setProjectId(projectId).build();
        ProjectStructure.GetByProjectIdResp rpcResponse = projectService.getByProjectId(getByProjectIdReq);
        ProjectStructure.Project project = rpcResponse.getProject();
        String prefix = project.getProjectTablePrefix();
        int maxTryTimes = 3; //当前最大尝试的次数
        boolean updateSuccess = false; //标记是否更新DB成功
        while (!updateSuccess && maxTryTimes > 0) { //若尚未更新db成功 并且剩余重试数大于0
            try {
                InboundOrder order = inBoundOrderDao.getInboundRecordByOrderNo(gongxiaoInboundOrderNo,prefix);
                String olderTraceLog = order.getTracelog();
                TraceLog traceLog = new TraceLog();
                Date dateTime = new Date();
                traceLog.setOpTime(dateTime.getTime());
                traceLog.setOpUid(rpcHeader.getUid());
                traceLog.setOpName(rpcHeader.getUsername());
                traceLog.setContent("WMS已经收货");
                int afterRealInStockQuantity = order.getRealInStockQuantity() + realInStockQuantity;
                int afterImperfectQuantity = order.getImperfectQuantity() + imperfectQuantity;
                int afterInStockQuantity = order.getInStockQuantity() + inStockQuantity;
                int afterInTransitQuantity = order.getInTransitQuantity() + inTransitQuantity;
                String tracelog = TraceLogUtil.appendTraceLog(olderTraceLog, traceLog);

                boolean isFinished = order.getTotalQuantity() <= order.getRealInStockQuantity() + realInStockQuantity;
                int orderStatus = isFinished? InboundOrderStatusEnum.INBOUND_ORDER_RECEIVE_FINISH.getStatus() : InboundOrderStatusEnum.INBOUND_ORDER_RECEIVE_PART.getStatus();
                int row = inBoundOrderDao.updateInStorageInfo(order.getRowId(), afterRealInStockQuantity, afterImperfectQuantity, afterInStockQuantity, afterInTransitQuantity, tracelog, orderStatus, order.getDataVersion(),prefix);
                if (row == 1)    return isFinished; //如更新成功则返回
                logger.info("#traceId={}# fail to modifyInboundOderByWms: gongxiaoInboundOrderNo={}", rpcHeader.getTraceId(), gongxiaoInboundOrderNo);
            } catch (Exception e) {
                logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
                throw e;
            }
        }
        if (!updateSuccess && maxTryTimes<=0) {
            logger.info("#traceId={}# fail to modifyInboundOderByWms after maxTryTimes: gongxiaoInboundOrderNo={}", rpcHeader.getTraceId(), gongxiaoInboundOrderNo);
        }
        return false;
    }

}
