package com.yhglobal.gongxiao.transportation.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.transportataion.eventnotification.sales.model.CreateDispatchOrderResponse;
import com.yhglobal.gongxiao.transportation.dao.CancelledOutboundOrderDao;
import com.yhglobal.gongxiao.transportation.dao.TransportationOutboundOrderDao;
import com.yhglobal.gongxiao.transportation.model.TransportConfig;
import com.yhglobal.gongxiao.transportation.model.TransportationCancelledOutboundOrder;
import com.yhglobal.gongxiao.transportation.model.TransportationOutboundOrder;
import com.yhglobal.gongxiao.util.RpcHeaderUtil;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.HANDLED;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.HANDLING;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.UNHANDLED;
import static com.yhglobal.gongxiao.constant.sales.SalesOrderSyncEnum.UNKNOWN;

/**
 * 运输模块-出库单推送TMS
 * 定时任务
 *
 * @author 葛灿
 */
@Service
public class OutboundOrderScheduledTask {

    private static final String SEND_CAR_URL = "/api/TmsApi/CreateDispatchOrder";

    private static final String CANCEL_ORDER_URL = "/api/TmsApi/CancelDispatchOrder";

    private static Logger logger = LoggerFactory.getLogger(OutboundOrderScheduledTask.class);

    @Autowired
    private TransportConfig transportConfig;

    @Autowired
    private TransportationOutboundOrderDao transportationOutboundOrderDao;

    @Autowired
    private CancelledOutboundOrderDao cancelledOutboundOrderDao;

    /**
     * 通知TMS派车
     */
    @Scheduled(fixedDelayString = "${syncTmsRate}")
    private void informTmsToSendCar() {
        String outboundNo = null;
        TransportationOutboundOrder salesOutboundOrder = null;
        int syncTms;
        int update;
        String prefix = null;
        try {
            logger.info("[IN][informTmsToSendCar] start.");
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("transportation-sheduled-task", "0", "transportation");
            //调用基础模拟块的项目的grpc查询所有的项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.SelectProjectListReq selectProjectListReq = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader).build();
            ProjectStructure.SelectProjectListResp rpcResponse = projectService.selectProjectList(selectProjectListReq);
            List<ProjectStructure.Project> projectListList = rpcResponse.getProjectListList();
            // 分库执行定时任务
            for (ProjectStructure.Project project : projectListList) {
                prefix = project.getProjectTablePrefix();
                logger.info("sync prefix {}", prefix);

                //查询所有"待同步"的数据
                List<String> outboundOrderNos = transportationOutboundOrderDao.selectListBySyncTmsStatus(prefix, UNHANDLED.getStatus());
                for (String outboundOrderNo : outboundOrderNos) {
                    int maxRetryTimes = 6;
                    boolean updateSuccess = false;
                    outboundNo = outboundOrderNo;
                    syncTms = HANDLING.getStatus();
                    //修改订单至"同步中"状态
                    while (maxRetryTimes-- > 0) {
                        salesOutboundOrder = transportationOutboundOrderDao.getOrderByOrderNo(prefix, outboundOrderNo);
                        update = transportationOutboundOrderDao.updateSyncTmsStatus(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms, null);
                        if (update == 1) {
                            updateSuccess = true;
                            break;
                        }
                    }
                    if (!updateSuccess) {
                        logger.error("update syncTmsStatus {} FAILED. outboundOrderNo={}", syncTms, outboundOrderNo);
                        throw new RuntimeException("update syncTmsStatus " + syncTms + " FAILED. outboundOrderNo=" + outboundOrderNo);
                    }
                    //开始同步
                    String jsonStr = salesOutboundOrder.getRequestJson();
                    logger.info("sending request to TMS: {}", jsonStr);
                    MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
                    String tmsUrl = transportConfig.getTmsUrl();
                    RequestBody requestBody = RequestBody.create(jsonMediaType, jsonStr);
                    Request request = new Request.Builder()
                            .url(tmsUrl + SEND_CAR_URL)
                            .post(requestBody)
                            .build();
                    Response httpResponse = OkHttpManager.execute(request);
                    String content = httpResponse.body().string();
                    int statusCode = httpResponse.code();
                    CreateDispatchOrderResponse resp;
                    if (statusCode == 200) {
                        resp = JSON.parseObject(content, CreateDispatchOrderResponse.class);
                    } else {
                        String msg = String.format("got error http status code from TMS: statusCode=%d", statusCode);
                        logger.error(msg);
                        throw new RuntimeException(msg);
                    }
                    logger.info("#TMS response#: {}", content);
                    maxRetryTimes = 6;
                    while (maxRetryTimes-- > 0) {
                        salesOutboundOrder = transportationOutboundOrderDao.getOrderByOrderNo(prefix, outboundOrderNo);
                        if (resp != null) {
                            if (resp.isSuccess()) {
                                syncTms = HANDLED.getStatus();
                            } else {
                                syncTms = UNHANDLED.getStatus();
                            }
                        } else {
                            syncTms = UNHANDLED.getStatus();
                        }
                        update = transportationOutboundOrderDao.updateSyncTmsStatus(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms, content);
                        if (update == 1) {
                            break;
                        }
                    }
                    if (maxRetryTimes <= 0) {
                        logger.error("update salesOutboundOrder FAILED!");
                    }
                    logger.info(" [OUT]: outboundOrder {} sync success.", outboundOrderNo);
                }
            }
            logger.info(" [OUT]: sync finished.");
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("[OUT] errorMessage: " + e.getMessage(), e);
            int maxRetryTimes = 6;
            if (message.contains("Read")) {
                while (maxRetryTimes-- > 0) {
                    // 状态修改为 UNKNOWN 未知
                    salesOutboundOrder = transportationOutboundOrderDao.getOrderByOrderNo(prefix, outboundNo);
                    syncTms = UNKNOWN.getStatus();
                    update = transportationOutboundOrderDao.updateSyncTmsStatus(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms, null);
                    if (update == 1) {
                        break;
                    }
                }
            } else {
                while (maxRetryTimes-- > 0) {
                    // 回滚为未处理
                    salesOutboundOrder = transportationOutboundOrderDao.getOrderByOrderNo(prefix, outboundNo);
                    syncTms = UNHANDLED.getStatus();
                    update = transportationOutboundOrderDao.updateSyncTmsStatus(prefix, salesOutboundOrder.getOid(), salesOutboundOrder.getDataVersion(), syncTms, null);
                    if (update == 1) {
                        break;
                    }
                }
            }
        }
    }


    /**
     * 通知TMS派车
     */
    @Scheduled(fixedDelayString = "${syncTmsRate}")
    private void informTmsCancelOutboundOrder() {
        String outboundNo = null;
        TransportationCancelledOutboundOrder cancelledOrder = null;
        int syncTms;
        int update;
        String prefix = null;
        try {
            logger.info("[IN][informTmsCancelOutboundOrder] start.");
            GongxiaoRpc.RpcHeader rpcHeader = RpcHeaderUtil.createRpcHeader("transportation-sheduled-cancel", "0", "transportation");
            //调用基础模拟块的项目的grpc查询所有的项目信息
            ProjectServiceGrpc.ProjectServiceBlockingStub projectService = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            ProjectStructure.SelectProjectListReq selectProjectListReq = ProjectStructure.SelectProjectListReq.newBuilder().setRpcHeader(rpcHeader).build();
            ProjectStructure.SelectProjectListResp rpcResponse = projectService.selectProjectList(selectProjectListReq);
            List<ProjectStructure.Project> projectListList = rpcResponse.getProjectListList();
            // 分库执行定时任务
            for (ProjectStructure.Project project : projectListList) {
                prefix = project.getProjectTablePrefix();
                logger.info("sync prefix {}", prefix);

                //查询所有"待同步"的数据
                List<String> outboundOrderNos = cancelledOutboundOrderDao.selectListBySyncTmsStatus(prefix, UNHANDLED.getStatus());
                for (String outboundOrderNo : outboundOrderNos) {
                    TransportationOutboundOrder outboundOrder = transportationOutboundOrderDao.getOrderByOrderNo(prefix, outboundOrderNo);
                    // 条件 = 订单存在并且已经推送给tms
                    boolean condition = (outboundOrder != null && SalesOrderSyncEnum.HANDLED == SalesOrderSyncEnum.getEnum(outboundOrder.getSyncTmsStatus()));
                    // 如果不符合条件,跳过该单
                    if (!condition) {
                        continue;
                    }
                    int maxRetryTimes = 6;
                    boolean updateSuccess = false;
                    outboundNo = outboundOrderNo;
                    syncTms = HANDLING.getStatus();
                    //修改订单至"同步中"状态
                    while (maxRetryTimes-- > 0) {
                        cancelledOrder = cancelledOutboundOrderDao.getOrderByOutboundOrderNo(prefix, outboundOrderNo);
                        update = cancelledOutboundOrderDao.updateSyncTmsStatus(prefix, cancelledOrder.getOid(), cancelledOrder.getDataVersion(), syncTms, null);
                        if (update == 1) {
                            updateSuccess = true;
                            break;
                        }
                    }
                    if (!updateSuccess) {
                        logger.error("update syncTmsStatus {} FAILED. outboundOrderNo={}", syncTms, outboundOrderNo);
                        throw new RuntimeException("update syncTmsStatus " + syncTms + " FAILED. outboundOrderNo=" + outboundOrderNo);
                    }
                    //开始同步
                    String jsonStr = cancelledOrder.getRequestJson();
                    logger.info("sending request to TMS: {}", jsonStr);
                    MediaType jsonMediaType = MediaType.parse("application/json-patch+json");
                    String tmsUrl = transportConfig.getTmsUrl();
                    RequestBody requestBody = RequestBody.create(jsonMediaType, jsonStr);
                    Request request = new Request.Builder()
                            .url(tmsUrl + CANCEL_ORDER_URL)
                            .post(requestBody)
                            .build();
                    Response httpResponse = OkHttpManager.execute(request);
                    String content = httpResponse.body().string();
                    int statusCode = httpResponse.code();
                    CreateDispatchOrderResponse resp;
                    if (statusCode == 200) {
                        resp = JSON.parseObject(content, CreateDispatchOrderResponse.class);
                    } else {
                        String msg = String.format("got error http status code from TMS: statusCode=%d", statusCode);
                        logger.error(msg);
                        throw new RuntimeException(msg);
                    }
                    logger.info("#TMS response#: {}", content);
                    maxRetryTimes = 6;
                    while (maxRetryTimes-- > 0) {
                        cancelledOrder = cancelledOutboundOrderDao.getOrderByOutboundOrderNo(prefix, outboundOrderNo);
                        if (resp != null) {
                            if (resp.isSuccess()) {
                                syncTms = HANDLED.getStatus();
                            } else {
                                syncTms = UNHANDLED.getStatus();
                            }
                        } else {
                            syncTms = UNHANDLED.getStatus();
                        }
                        update = cancelledOutboundOrderDao.updateSyncTmsStatus(prefix, cancelledOrder.getOid(), cancelledOrder.getDataVersion(), syncTms, content);
                        if (update == 1) {
                            break;
                        }
                    }
                    if (maxRetryTimes <= 0) {
                        logger.error("update salesOutboundOrder FAILED!");
                    }
                    logger.info(" [OUT]: outboundOrder {} sync success.", outboundOrderNo);
                }
            }
            logger.info(" [OUT]: sync finished.");
        } catch (Exception e) {
            String message = e.getMessage();
            logger.error("[OUT] errorMessage: " + e.getMessage(), e);
            int maxRetryTimes = 6;
            if (message.contains("Read")) {
                while (maxRetryTimes-- > 0) {
                    // 状态修改为 UNKNOWN 未知
                    cancelledOrder = cancelledOutboundOrderDao.getOrderByOutboundOrderNo(prefix, outboundNo);
                    syncTms = UNKNOWN.getStatus();
                    update = cancelledOutboundOrderDao.updateSyncTmsStatus(prefix, cancelledOrder.getOid(), cancelledOrder.getDataVersion(), syncTms, null);
                    if (update == 1) {
                        break;
                    }
                }
            } else {
                while (maxRetryTimes-- > 0) {
                    // 回滚为未处理
                    cancelledOrder = cancelledOutboundOrderDao.getOrderByOutboundOrderNo(prefix, outboundNo);
                    syncTms = UNHANDLED.getStatus();
                    update = cancelledOutboundOrderDao.updateSyncTmsStatus(prefix, cancelledOrder.getOid(), cancelledOrder.getDataVersion(), syncTms, null);
                    if (update == 1) {
                        break;
                    }
                }
            }
        }
    }
}
