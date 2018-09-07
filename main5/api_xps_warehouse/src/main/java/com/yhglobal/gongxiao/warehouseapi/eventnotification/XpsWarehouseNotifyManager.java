package com.yhglobal.gongxiao.warehouseapi.eventnotification;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.ErrorCode;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.util.ValidationUtil;
import com.yhglobal.gongxiao.utils.GongxiaoResultUtil;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.InboundNotificationBackItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.PurchaseEasInboundModel;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.TransferClosedModel;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.TransferReceivedNotification;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.EasOutboundItem;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.EasOutboundOrderRequest;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.sales.model.SyncEasRequest;
import com.yhglobal.gongxiao.coreutil.OkHttpManager;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 葛灿
 */
public class XpsWarehouseNotifyManager {

    private static Logger logger = LoggerFactory.getLogger(XpsWarehouseNotifyManager.class);

    private static final String OUT_BOUND_FINISHED = "/salesorder/outboundFinished";

    private static final String OUT_BOUND_SYNC_EAS = "/salesorder/syncEas";

    private static final String OUT_BOUND_ORDER_SEND_CAR = "/salesorder/sendCar";

    private static final String CANCEL_OUTBOUND_ORDER = "/salesorder/cancelOutbound";


    private static final String GET_EAS_PARAM = "/notifyPurchase/getSynEasParam";

    private static final String NOTIFY_INBOUND = "/notifyPurchase/notifyPurchaseInbound";

    private static final String CLOSE_NOTIFY = "/notifyPurchase/transferClosedNotification";

    private static final String SALES_RETURN_INBOUND = "/salesReturnOrder/inboundFinished";


    /****************************************************************************************************
     sales
     *************************************************************************************************************/
    /**
     * 仓库通知退货入库到仓
     *
     * @param projectUrl
     * @param inboundOrderNo
     * @param traceNo
     * @param productCode
     * @param productName
     * @param productUnit
     * @param inStockQuantity
     * @return
     */
    public static GongxiaoResult notifySalesReturnInbound(String projectUrl,
                                                          String inboundOrderNo,
                                                          String traceNo,
                                                          String productCode,
                                                          String productName,
                                                          String productUnit,
                                                          String inStockQuantity,
                                                          long projectId) {
        GongxiaoResult gongxiaoResult;
        logger.info("[IN][notifySalesReturnInbound] params: projectUrl={}, inboundOrderNo={}, traceNo={}, productCode={}, productName={}, productUnit={}, inStockQuantity={}",
                projectUrl, inboundOrderNo, traceNo, productCode, productName, productUnit, inStockQuantity);
        //1. 参数校验
        if (projectUrl == null) {
            String msg = "projectUrl can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (inboundOrderNo == null) {
            String msg = "inboundOrderNo can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        try {
            logger.info("#xps warehouse request#: inboundOrderNo={}", inboundOrderNo);
            FormBody postRequest = new FormBody.Builder()
                    .add("projectId", projectId + "")
                    .add("inboundOrderNo", inboundOrderNo)
                    .add("traceNo", traceNo)
                    .add("productCode", productCode)
                    .add("productName", productName)
                    .add("productUnit", productUnit)
                    .add("inStockQuantity", inStockQuantity)
                    .build();
            Request request = new Request.Builder()
                    .url(projectUrl + SALES_RETURN_INBOUND)
                    .post(postRequest)
                    .build();
            try {
                Response httpResponse = OkHttpManager.execute(request);
                String content = httpResponse.body().string();
                logger.info("#transportation response#: {}", content);

                int statusCode = httpResponse.code();
                if (statusCode == 200) {
                    return JSON.parseObject(content, GongxiaoResult.class);
                } else {
                    String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                    logger.error(msg);
                    throw new RuntimeException(msg);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }

    /**
     * [仓库模块] -> [销售模块]
     * 通知销售模块 向tms发送派车请求
     *
     * @param projectUrl      项目的url
     * @param outboundOrderNo 出库单号
     * @return GongxiaoResult result中不存在object
     */
    public static GongxiaoResult notifySalesSendCar(String projectUrl,
                                                    long projectId,
                                                    String outboundOrderNo) {
        GongxiaoResult gongxiaoResult;
        logger.info("[itemsOutboundFinished] params: projectUrl={}, outboundOrderNo={}", projectUrl, outboundOrderNo);
        //1. 参数校验
        if (projectUrl == null) {
            String msg = "projectUrl can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (outboundOrderNo == null) {
            String msg = "outboundOrderNo can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        try {
            logger.info("#xps warehouse request#: outboundOrderNo={}", outboundOrderNo);
            FormBody postRequest = new FormBody.Builder()
                    .add("projectId", projectId + "")
                    .add("outboundOrderNo", outboundOrderNo)
                    .build();
            Request request = new Request.Builder()
                    .url(projectUrl + OUT_BOUND_ORDER_SEND_CAR)
                    .post(postRequest)
                    .build();
            try {
                Response httpResponse = OkHttpManager.execute(request);
                String content = httpResponse.body().string();
                logger.info("#transportation response#: {}", content);

                int statusCode = httpResponse.code();
                if (statusCode == 200) {
                    return JSON.parseObject(content, GongxiaoResult.class);
                } else {
                    String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                    logger.error(msg);
                    throw new RuntimeException(msg);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * [仓库模块] -> [销售模块]
     * 通知销售模块出库完成
     *
     * @param projectUrl      项目的url
     * @param outboundOrderNo 出库单号
     * @return GongxiaoResult result中不存在object
     */
    public static GongxiaoResult itemsOutboundFinished(String projectUrl,
                                                       String outboundOrderNo,
                                                       long projectId) {
        GongxiaoResult gongxiaoResult;
        logger.info("[itemsOutboundFinished] params: projectUrl={}, outboundOrderNo={}", projectUrl, outboundOrderNo);
        //1. 参数校验
        if (projectUrl == null) {
            String msg = "projectUrl can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (outboundOrderNo == null) {
            String msg = "outboundOrderNo can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        try {
            logger.info("#xps warehouse request#: outboundOrderNo={}", outboundOrderNo);
            FormBody postRequest = new FormBody.Builder()
                    .add("projectId", projectId + "")
                    .add("outboundOrderNo", outboundOrderNo)
                    .build();
            Request request = new Request.Builder()
                    .url(projectUrl + OUT_BOUND_FINISHED)
                    .post(postRequest)
                    .build();
            try {
                Response httpResponse = OkHttpManager.execute(request);
                String content = httpResponse.body().string();
                logger.info("#transportation response#: {}", content);

                int statusCode = httpResponse.code();
                if (statusCode == 200) {
                    return JSON.parseObject(content, GongxiaoResult.class);
                } else {
                    String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                    logger.error(msg);
                    throw new RuntimeException(msg);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }


    /**
     * 出库模块查询推送eas需要的信息
     *
     * @param projectUrl   项目url
     * @param salesOrderNo 销售单号
     * @param items        货品信息
     * @return EasOutboundOrderRequest
     */
    public static EasOutboundOrderRequest syncSalesOutboundOrderToEas(String projectUrl,
                                                                      String salesOrderNo,
                                                                      List<EasOutboundItem> items,
                                                                      long projectId) {

        EasOutboundOrderRequest result = new EasOutboundOrderRequest();
        logger.info("[syncSalesOutboundOrderToEas] params: projectUrl={}, salesOrderNo={}, items={}", projectUrl, salesOrderNo, items);

        //1. 参数校验
        if (projectUrl == null) {
            String msg = "projectUrl can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (salesOrderNo == null) {
            String msg = "salesOrderNo can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (items.size() == 0) {
            String msg = "items can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        try {
            SyncEasRequest syncEasRequest = new SyncEasRequest();
            syncEasRequest.setItems(items);
            syncEasRequest.setSalesOrderNo(salesOrderNo);
            syncEasRequest.setProjectId(projectId);
            String jsonString = JSON.toJSONString(syncEasRequest);
            MediaType jsonMediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
            Request request = new Request.Builder()
                    .url(projectUrl + OUT_BOUND_SYNC_EAS)
                    .post(requestBody)
                    .build();
            try {
                Response httpResponse = OkHttpManager.execute(request);
                String content = httpResponse.body().string();
                logger.info("#warehouse response#: {}", content);

                int statusCode = httpResponse.code();
                if (statusCode == 200) {
                    return JSON.parseObject(content, EasOutboundOrderRequest.class);
                } else {
                    String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                    logger.error(msg);
                    throw new RuntimeException(msg);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            result = new EasOutboundOrderRequest();
            result.setErrorCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
        }
        return result;
    }


    /**
     * [仓库模块] -> [销售模块]
     * 通知销售模块出库订单取消
     *
     * @param projectUrl      项目的url
     * @param outboundOrderNo 出库单号
     * @return GongxiaoResult result中不存在object
     */
    public static GongxiaoResult cancelOutbound(String projectUrl,
                                                String outboundOrderNo,
                                                long projectId) {
        GongxiaoResult gongxiaoResult;
        logger.info("[itemsOutboundFinished] params: projectUrl={}, outboundOrderNo={}", projectUrl, outboundOrderNo);
        //1. 参数校验
        if (projectUrl == null) {
            String msg = "projectUrl can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        if (outboundOrderNo == null) {
            String msg = "outboundOrderNo can not be empty";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
        try {
            logger.info("#xps warehouse request#: outboundOrderNo={}", outboundOrderNo);
            FormBody postRequest = new FormBody.Builder()
                    .add("projectId", projectId + "")
                    .add("outboundOrderNo", outboundOrderNo)
                    .build();
            Request request = new Request.Builder()
                    .url(projectUrl + CANCEL_OUTBOUND_ORDER)
                    .post(postRequest)
                    .build();
            try {
                Response httpResponse = OkHttpManager.execute(request);
                String content = httpResponse.body().string();
                logger.info("#project response#: {}", content);

                int statusCode = httpResponse.code();
                if (statusCode == 200) {
                    return JSON.parseObject(content, GongxiaoResult.class);
                } else {
                    String msg = String.format("got error http status code from project: statusCode=%d", statusCode);
                    logger.error(msg);
                    throw new RuntimeException(msg);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            gongxiaoResult = new GongxiaoResult(ErrorCode.SUCCESS.getErrorCode(), ErrorCode.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
            gongxiaoResult = GongxiaoResultUtil.createGongxiaoErrorResult(ErrorCode.UNKNOWN_ERROR);
        }
        return gongxiaoResult;
    }
    /****************************************************************************************************
     sale  end
     *************************************************************************************************************/


    //**********************************************purchase  start *******************************************************

    /**
     * 获取采购入库同步EAS参数
     *
     * @param url
     * @param projectId
     * @param traceNo
     * @param gongxiaoInboundOrderNo
     * @param inboundNotificationBackItemList
     * @param uniqueNumber
     * @return
     */
    public static PurchaseEasInboundModel transferReceivedNotification(String url,
                                                                       String projectId,
                                                                       String traceNo,
                                                                       String gongxiaoInboundOrderNo,
                                                                       List<InboundNotificationBackItem> inboundNotificationBackItemList,
                                                                       String uniqueNumber) {
        PurchaseEasInboundModel purchaseEasInboundModel = new PurchaseEasInboundModel();
        //1参数校验
        boolean parameterNotNull = ValidationUtil.isNotEmpty(projectId, gongxiaoInboundOrderNo, traceNo);
        if (!parameterNotNull) {
            purchaseEasInboundModel.setErrorCode(ErrorCode.ARGUMENTS_INVALID.getErrorCode());
            return purchaseEasInboundModel;
        }
        //2构建请求参数
        TransferReceivedNotification transferReceivedNotification = new TransferReceivedNotification();
        transferReceivedNotification.setProjectId(projectId);
        transferReceivedNotification.setTraceNo(traceNo);
        transferReceivedNotification.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
        transferReceivedNotification.setUniqueNumbe(uniqueNumber);
        transferReceivedNotification.setInboundNotificationBackItemList(inboundNotificationBackItemList);
        String jsonString = JSON.toJSONString(transferReceivedNotification);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(url + GET_EAS_PARAM)
                .post(requestBody)
                .build();

        try {
            Response httpResponse = OkHttpManager.execute(request);
            //3组装返回值
            String content = httpResponse.body().string();
            logger.info("#transportation response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, PurchaseEasInboundModel.class);
            } else {
                String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            purchaseEasInboundModel.setErrorCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
            return purchaseEasInboundModel;
        }

    }

    /**
     * 通知采购已入库
     *
     * @param url
     * @param projectId
     * @param traceNo
     * @param gongxiaoInboundOrderNo
     * @param inboundNotificationBackItemList
     * @param uniqueNumber
     * @return
     */
    public static RpcResult notifyPurchaseInbound(String url,
                                                  String projectId,
                                                  String traceNo,
                                                  String gongxiaoInboundOrderNo,
                                                  List<InboundNotificationBackItem> inboundNotificationBackItemList,
                                                  String uniqueNumber) {
        RpcResult rpcResult = new RpcResult();
        //1参数校验
        boolean parameterNotNull = ValidationUtil.isNotEmpty(projectId, gongxiaoInboundOrderNo, traceNo);
        if (!parameterNotNull) {
            rpcResult.setCode(ErrorCode.ARGUMENTS_INVALID.getErrorCode());
            return rpcResult;
        }
        //2构建请求参数
        TransferReceivedNotification transferReceivedNotification = new TransferReceivedNotification();
        transferReceivedNotification.setProjectId(projectId);
        transferReceivedNotification.setTraceNo(traceNo);
        transferReceivedNotification.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
        transferReceivedNotification.setUniqueNumbe(uniqueNumber);
        transferReceivedNotification.setInboundNotificationBackItemList(inboundNotificationBackItemList);
        String jsonString = JSON.toJSONString(transferReceivedNotification);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(url + NOTIFY_INBOUND)
                .post(requestBody)
                .build();

        try {
            Response httpResponse = OkHttpManager.execute(request);
            //3组装返回值
            String content = httpResponse.body().string();
            logger.info("#transportation response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, RpcResult.class);
            } else {
                String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            rpcResult.setCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
            return rpcResult;
        }

    }

    /**
     * 通知采购入库完成
     *
     * @param url
     * @param projectId
     * @param traceNo
     * @param gongxiaoInboundOrderNo
     * @param batchNo
     * @param uniqueNumber
     * @return
     */
    public static RpcResult transferClosedNotification(String url,
                                                       String projectId,
                                                       String traceNo,
                                                       String gongxiaoInboundOrderNo,
                                                       String batchNo,
                                                       String uniqueNumber) {
        RpcResult rpcResult = new RpcResult();
        //1参数校验
        boolean parameterNotNull = ValidationUtil.isNotEmpty(projectId, gongxiaoInboundOrderNo, traceNo, batchNo, uniqueNumber);
        if (!parameterNotNull) {
            rpcResult.setCode(ErrorCode.ARGUMENTS_INVALID.getErrorCode());
            return rpcResult;
        }
        //2构建请求参数
        TransferClosedModel transferClosedModel = new TransferClosedModel();
        transferClosedModel.setProjectId(projectId);
        transferClosedModel.setBatchNo(batchNo);
        transferClosedModel.setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo);
        transferClosedModel.setUniqueNumbe(uniqueNumber);
        transferClosedModel.setTraceNo(traceNo);
        String jsonString = JSON.toJSONString(transferClosedModel);
        MediaType jsonMediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonMediaType, jsonString);
        Request request = new Request.Builder()
                .url(url + CLOSE_NOTIFY)
                .post(requestBody)
                .build();
        //3构建返回值
        try {
            Response httpResponse = OkHttpManager.execute(request);
            //3组装返回值
            String content = httpResponse.body().string();
            logger.info("#transportation response#: {}", content);

            int statusCode = httpResponse.code();
            if (statusCode == 200) {
                return JSON.parseObject(content, RpcResult.class);
            } else {
                String msg = String.format("got error http status code from transportation: statusCode=%d", statusCode);
                logger.error(msg);
                throw new RuntimeException(msg);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            rpcResult.setCode(ErrorCode.UNKNOWN_ERROR.getErrorCode());
            return rpcResult;
        }

    }

    //**********************************************purchase  end *******************************************************

    public static void main(String[] args) {
//        String url = "http://127.0.0.1:8480";
//        String projectId = "146798161";
//        String traceNo = "PO2018072717251701";
//        String gongxiaoInboundOrderNo = "123456";
//        String batchNo = "1234";
//        String uniqueNumber = "123";
////        transferClosedNotification(url,projectId,traceNo,gongxiaoInboundOrderNo,batchNo,uniqueNumber);
//        List<InboundNotificationBackItem> inboundNotificationBackItemList = new ArrayList<>();
//        InboundNotificationBackItem inboundNotificationBackItem = new InboundNotificationBackItem();
//        inboundNotificationBackItem.setBusinessItemId("7118");
//        inboundNotificationBackItem.setInboundNotificationItemId("123123");
//        inboundNotificationBackItem.setProductCode("880252912710");
//        inboundNotificationBackItem.setInStockQuantity(1000);
//        inboundNotificationBackItem.setImperfectQuantity(100);
//        inboundNotificationBackItem.setWarehouseId("2");
//        inboundNotificationBackItem.setBatchNo("a123");
//        inboundNotificationBackItem.setGift(false);
//        inboundNotificationBackItemList.add(inboundNotificationBackItem);
//
//        PurchaseEasInboundModel purchaseEasInboundModel = transferReceivedNotification(url, projectId, traceNo, gongxiaoInboundOrderNo, inboundNotificationBackItemList, uniqueNumber);
//        System.out.println(1);
//        notifySalesSendCar("http://127.0.0.1:12010", "SOOUT2018072617154000");
        GongxiaoResult gongxiaoResult = cancelOutbound("http://127.0.0.1:12010", "XPS_shaver_SOOUT2018082817211100", 146798161);
        System.out.println(JSON.toJSONString(gongxiaoResult));
    }
}
