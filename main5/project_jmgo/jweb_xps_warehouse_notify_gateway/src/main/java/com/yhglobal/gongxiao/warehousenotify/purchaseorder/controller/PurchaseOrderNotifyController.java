package com.yhglobal.gongxiao.warehousenotify.purchaseorder.controller;

import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrderInbound;
import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrderItemInbound;
import com.yhglobal.gongxiao.grpc.client.RpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.RpcResult;
import com.yhglobal.gongxiao.purchase.microservice.NotifiInboundServiceGrpc;
import com.yhglobal.gongxiao.purchase.microservice.NotifiInboundStructure;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.util.GrpcCommonUtil;
import com.yhglobal.gongxiao.warehouseapi.eventnotification.purchase.model.PurchaseEasInboundModel;
import com.yhglobal.gongxiao.warehousenotify.purchaseorder.controller.model.InboundNotificationBackItem;
import com.yhglobal.gongxiao.warehousenotify.purchaseorder.controller.model.TransferClosedModel;
import com.yhglobal.gongxiao.warehousenotify.purchaseorder.controller.model.TransferReceivedNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 采购模块 接收XPS warehouse的回告信息 入口
 */
@Controller
@RequestMapping("/notifyPurchase")
public class PurchaseOrderNotifyController {

    private Logger logger = LoggerFactory.getLogger(PurchaseOrderNotifyController.class);

    /**
     * 注： 请求结果 需要按 xps warehouse的要求返回去
     */
    @RequestMapping(value = "/getSynEasParam",method = RequestMethod.POST)
    @ResponseBody
    public PurchaseEasInboundModel getSynEasParam(@RequestBody TransferReceivedNotification request) {
        try {
            String gongxiaoInboundOrderNo = request.getGongxiaoInboundOrderNo();
            String projectId = request.getProjectId();
            String traceNo = request.getTraceNo();
            String uniqueNumber = request.getUniqueNumbe();
            List<InboundNotificationBackItem> inboundNotificationBackItemList = request.getInboundNotificationBackItemList();
            logger.info("#[IN] transferReceivedNotificationControllerIn param: projectId={}, traceNo={}, gongxiaoInboundOrderNo={}, uniqueNumber={}", projectId, traceNo, gongxiaoInboundOrderNo, uniqueNumber);
            NotifiInboundServiceGrpc.NotifiInboundServiceBlockingStub notifiInboundServiceBlockingStub = RpcStubStore.getRpcStub(NotifiInboundServiceGrpc.NotifiInboundServiceBlockingStub.class);
            NotifiInboundStructure.TransferReceivedNotificationReq.Builder transferReceivedNotificationReq = NotifiInboundStructure.TransferReceivedNotificationReq.newBuilder()
                    .setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo)
                    .setProjectId(projectId)
                    .setUniqueNumber(uniqueNumber)
                    .setTraceNo(traceNo);
            for (InboundNotificationBackItem inboundNotificationBackItem : inboundNotificationBackItemList) {
                NotifiInboundStructure.InboundNotificationBackItem inboundNotificationBackItem1 = NotifiInboundStructure.InboundNotificationBackItem.newBuilder()
                        .setBusinessItemId(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getBusinessItemId()))
                        .setInboundNotificationItemId(inboundNotificationBackItem.getInboundNotificationItemId())
                        .setProductCode(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getProductCode()))
                        .setInStockQuantity(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getInStockQuantity()))
                        .setImperfectQuantity(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getImperfectQuantity()))
                        .setWarehouseId(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getWarehouseId()))
                        .setBatchNo(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getBatchNo()))
                        .setIsGift(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.isGift()))
                        .build();
                transferReceivedNotificationReq.addInboundNotificationBackItem(inboundNotificationBackItem1);
            }
            NotifiInboundStructure.TransferReceivedNotificationResp transferReceivedNotificationResp = notifiInboundServiceBlockingStub.transferReceivedNotification(transferReceivedNotificationReq.build());

            NotifiInboundStructure.PurchaseBasicOrderInbound purchaseBasicOrderInbound = transferReceivedNotificationResp.getPurchaseBasicOrderInbound();
            List<NotifiInboundStructure.PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundList = transferReceivedNotificationResp.getPurchaseBasicOrderItemInboundList();
            int errorCode = transferReceivedNotificationResp.getErrorCode();

            PurchaseEasInboundModel purchaseEasInboundModel = new PurchaseEasInboundModel();
            List<PurchaseBasicOrderItemInbound> purchaseBasicOrderItemInboundListResp = new ArrayList<>();
            PurchaseBasicOrderInbound purchaseBasicOrderInboundResp = new PurchaseBasicOrderInbound();

            purchaseEasInboundModel.setErrorCode(errorCode);

            purchaseBasicOrderInboundResp.setProjectId(purchaseBasicOrderInbound.getProjectId());
            purchaseBasicOrderInboundResp.setSupplierCode(purchaseBasicOrderInbound.getSupplierCode());
            purchaseBasicOrderInboundResp.setNumber(purchaseBasicOrderInbound.getNumber());
            purchaseBasicOrderInboundResp.setTotalTax(purchaseBasicOrderInbound.getTotalTax());
            purchaseBasicOrderInboundResp.setTotalTaxAmount(purchaseBasicOrderInbound.getTotalTaxAmount());
            purchaseBasicOrderInboundResp.setId(purchaseBasicOrderInbound.getId());
            purchaseBasicOrderInboundResp.setPurchaseOrderNum(purchaseBasicOrderInbound.getPurchaseOrderNum());
            purchaseBasicOrderInboundResp.setBusinessDate(DateUtil.long2Date(purchaseBasicOrderInbound.getBusinessDate()));
            purchaseBasicOrderInboundResp.setRequireArrivalDate(DateUtil.long2Date(purchaseBasicOrderInbound.getRequireArrivalDate()));

            purchaseEasInboundModel.setPurchaseBasicOrderInbound(purchaseBasicOrderInboundResp);

            for (NotifiInboundStructure.PurchaseBasicOrderItemInbound purchaseBasicOrderItemInbound : purchaseBasicOrderItemInboundList) {
                PurchaseBasicOrderItemInbound purchaseBasicOrderItemInbound1 = new PurchaseBasicOrderItemInbound();
                purchaseBasicOrderItemInbound1.setTaxRate(purchaseBasicOrderItemInbound.getTaxRate());
                purchaseBasicOrderItemInbound1.setTaxPrice(purchaseBasicOrderItemInbound.getTaxPrice());
                purchaseBasicOrderItemInbound1.setWarehouseCode(purchaseBasicOrderItemInbound.getWarehouseCode());
                purchaseBasicOrderItemInbound1.setLocationId(purchaseBasicOrderItemInbound.getLocationId());
                purchaseBasicOrderItemInbound1.setLot(purchaseBasicOrderItemInbound.getLot());
                purchaseBasicOrderItemInbound1.setNumber(purchaseBasicOrderItemInbound.getNumber());
                purchaseBasicOrderItemInbound1.setDiscount(purchaseBasicOrderItemInbound.getDiscount());
                purchaseBasicOrderItemInbound1.setMaterialId(purchaseBasicOrderItemInbound.getMaterialId());
                purchaseBasicOrderItemInbound1.setPurchaseOrderId(purchaseBasicOrderItemInbound.getPurchaseOrderId());
                purchaseBasicOrderItemInbound1.setPurchaseOrderEntryId(purchaseBasicOrderItemInbound.getPurchaseOrderEntryId());
                purchaseBasicOrderItemInbound1.setUnit(purchaseBasicOrderItemInbound.getUnit());
                purchaseBasicOrderItemInboundListResp.add(purchaseBasicOrderItemInbound1);
            }
            purchaseEasInboundModel.setPurchaseBasicOrderItemInboundList(purchaseBasicOrderItemInboundListResp);
            logger.info("#[OUT] transferReceivedNotificationControllerOut param: projectId={}, traceNo={}, gongxiaoInboundOrderNo={}, uniqueNumber={}", projectId, traceNo, gongxiaoInboundOrderNo, uniqueNumber);
            return purchaseEasInboundModel;
        } catch (Exception e) {
            logger.error("#transferReceivedNotification [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }

    }

    /**
     * 注： 请求结果 需要按 xps warehouse的要求返回去
     */
    @RequestMapping(value = "/notifyPurchaseInbound",method = RequestMethod.POST)
    @ResponseBody
    public RpcResult notifyPurchaseInbound(@RequestBody TransferReceivedNotification request) {
        String gongxiaoInboundOrderNo = request.getGongxiaoInboundOrderNo();
        String projectId = request.getProjectId();
        String uniqueNumber = request.getUniqueNumbe();
        String traceNo = request.getTraceNo();
        List<InboundNotificationBackItem> inboundNotificationBackItemList = request.getInboundNotificationBackItemList();
        try {
            logger.info("#[IN] notifyPurchaseInboundControllerIn param: projectId={}, traceNo={}, gongxiaoInboundOrderNo={}, uniqueNumber={}", projectId, traceNo, gongxiaoInboundOrderNo, uniqueNumber);
            NotifiInboundServiceGrpc.NotifiInboundServiceBlockingStub notifiInboundServiceBlockingStub = RpcStubStore.getRpcStub(NotifiInboundServiceGrpc.NotifiInboundServiceBlockingStub.class);
            NotifiInboundStructure.NotifyPurchaseInboundReq.Builder notifyReq = NotifiInboundStructure.NotifyPurchaseInboundReq.newBuilder()
                    .setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo)
                    .setProjectId(projectId)
                    .setUniqueNumber(uniqueNumber)
                    .setTraceNo(traceNo);
            for (InboundNotificationBackItem inboundNotificationBackItem : inboundNotificationBackItemList) {
                NotifiInboundStructure.InboundNotificationBackItem inboundNotificationBackItem1 = NotifiInboundStructure.InboundNotificationBackItem.newBuilder()
                        .setBusinessItemId(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getBusinessItemId()))
                        .setInboundNotificationItemId(inboundNotificationBackItem.getInboundNotificationItemId())
                        .setProductCode(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getProductCode()))
                        .setInStockQuantity(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getInStockQuantity()))
                        .setImperfectQuantity(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getImperfectQuantity()))
                        .setWarehouseId(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getWarehouseId()))
                        .setBatchNo(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.getBatchNo()))
                        .setIsGift(GrpcCommonUtil.getProtoParam(inboundNotificationBackItem.isGift()))
                        .build();
                notifyReq.addInboundNotificationBackItem(inboundNotificationBackItem1);
            }
            NotifiInboundStructure.NotifyPurchaseInboundResp notifyPurchaseInboundResp = notifiInboundServiceBlockingStub.notifyPurchaseInbound(notifyReq.build());
            GongxiaoRpc.RpcResult rpcResult = notifyPurchaseInboundResp.getRpcResult();

            RpcResult rpcResult1 = new RpcResult();
            rpcResult1.setCode(rpcResult.getReturnCode());
            rpcResult1.setMessage(rpcResult.getMsg());
            logger.info("#[OUT] notifyPurchaseInboundControllerOut param: projectId={}, traceNo={}, gongxiaoInboundOrderNo={}, uniqueNumber={}", projectId, traceNo, gongxiaoInboundOrderNo, uniqueNumber);

            return rpcResult1;
        } catch (Exception e) {
            logger.error("# notifyPurchaseInbound [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 注： 请求结果 需要按 xps warehouse的要求返回去
     */
    @RequestMapping(value = "/transferClosedNotification",method = RequestMethod.POST)
    @ResponseBody
    public RpcResult transferClosedNotification(@RequestBody TransferClosedModel transferClosedModel) {
        try {
            String batchNo = transferClosedModel.getBatchNo();
            String projectId = transferClosedModel.getProjectId();
            String traceNo = transferClosedModel.getTraceNo();
            String gongxiaoInboundOrderNo = transferClosedModel.getGongxiaoInboundOrderNo();
            String uniqueNumber = transferClosedModel.getUniqueNumbe();
            logger.info("#[In] transferClosedNotificationControllerIn param: projectId={}, traceNo={}, gongxiaoInboundOrderNo={}, uniqueNumber={}", projectId, traceNo, gongxiaoInboundOrderNo, uniqueNumber);

            NotifiInboundServiceGrpc.NotifiInboundServiceBlockingStub notifiInboundServiceBlockingStub = RpcStubStore.getRpcStub(NotifiInboundServiceGrpc.NotifiInboundServiceBlockingStub.class);
            NotifiInboundStructure.TransferClosedNotificationReq transferClosedNotificationReq = NotifiInboundStructure.TransferClosedNotificationReq.newBuilder()
                    .setProjectId(projectId)
                    .setTraceNo(traceNo)
                    .setGongxiaoInboundOrderNo(gongxiaoInboundOrderNo)
                    .setBatchNo(batchNo)
                    .setUniqueNumber(uniqueNumber)
                    .build();
            NotifiInboundStructure.TransferClosedNotificationResp transferClosedNotificationResp = notifiInboundServiceBlockingStub.transferClosedNotification(transferClosedNotificationReq);
            GongxiaoRpc.RpcResult rpcResult = transferClosedNotificationResp.getRpcResult();

            RpcResult rpcResult1 = new RpcResult();
            rpcResult1.setCode(rpcResult.getReturnCode());
            rpcResult1.setMessage(rpcResult.getMsg());
            logger.info("#[OUT] transferClosedNotificationControllerOut param: projectId={}, traceNo={}, gongxiaoInboundOrderNo={}, uniqueNumber={}", projectId, traceNo, gongxiaoInboundOrderNo, uniqueNumber);
            return rpcResult1;
        } catch (Exception e) {
            logger.error("# transferClosedNotification [OUT] errorMessage: " + e.getMessage(), e);
            throw e;
        }
    }

}