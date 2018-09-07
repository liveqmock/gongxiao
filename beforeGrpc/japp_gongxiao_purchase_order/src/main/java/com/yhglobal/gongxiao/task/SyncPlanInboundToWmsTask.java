package com.yhglobal.gongxiao.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.dao.PurchaseScheduleDeliveryDao;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.model.PurchaseOrder;
import com.yhglobal.gongxiao.model.PurchaseOrderItem;
import com.yhglobal.gongxiao.model.PurchaseScheduleDelivery;
import com.yhglobal.gongxiao.purchase.bo.PlanInboundBasicInfo;
import com.yhglobal.gongxiao.purchase.bo.PlanInboundItem;
import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderBackWrite;
import com.yhglobal.gongxiao.purchase.dto.PurchaseOrderItemBackWrite;
import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;
import com.yhglobal.gongxiao.purchase.temp.PlanInboundItemSummary;
import com.yhglobal.gongxiao.status.PurchasePlanStatus;
import com.yhglobal.gongxiao.warehouse.service.InboundService;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import com.yhglobal.gongxiao.warehouse.type.WmsSourceChannel;
import com.yhglobal.gongxiao.warehousemanagement.dto.PlanInboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.*;

public class SyncPlanInboundToWmsTask implements Runnable{

    private Logger logger = LoggerFactory.getLogger(SyncPlanInboundToWmsTask.class);

    private RpcHeader rpcHeader;

    private ApplicationContext applicationContext;

    private PlanInboundBasicInfo planInboundBasicInfo;

    private List<PlanInboundItem> planInboundItemList;

    private InboundService inboundService;

    private String streetAddress;

    public SyncPlanInboundToWmsTask(RpcHeader rpcHeader,
                                    ApplicationContext applicationContext,
                                    PlanInboundBasicInfo planInboundBasicInfo,
                                    List<PlanInboundItem> planInboundItemList,
                                    InboundService inboundService,
                                    String streetAddress){
        this.rpcHeader=rpcHeader;
        this.applicationContext=applicationContext;
        this.planInboundBasicInfo=planInboundBasicInfo;
        this.planInboundItemList=planInboundItemList;
        this.inboundService=inboundService;
        this.streetAddress = streetAddress;
    }


    @Override
    public void run() {
        try {
            logger.warn("==traceid={}===planInboundBasicInfo===== ={}",rpcHeader.getTraceId(),planInboundBasicInfo.toString());
            PurchaseOrderDao purchaseOrderDao = applicationContext.getBean("purchaseOrderDao", PurchaseOrderDao.class);
            PurchaseOrderItemDao purchaseOrderItemDao = applicationContext.getBean("purchaseOrderItemDao", PurchaseOrderItemDao.class);
            PurchaseScheduleDeliveryDao purchaseScheduleDeliveryDao = applicationContext.getBean("purchaseScheduleDeliveryDao", PurchaseScheduleDeliveryDao.class);

            String projectId = planInboundBasicInfo.getProjectId();
            String purchaseOrderNo = planInboundBasicInfo.getPurchaseOrderNo();
            //1)获取采购单信息
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(purchaseOrderNo);
            List<PurchaseOrderItem> purchaseOrderItemList = purchaseOrderItemDao.selectByOrderNo(purchaseOrderNo);
            Map itemMap = new HashMap();
            for (PlanInboundItem purchaseOrderItem : planInboundItemList) {
                String productCode = purchaseOrderItem.getProductCode();
                int totalQuantity = purchaseOrderItem.getPlanInstockNumber();
                itemMap.put(productCode, totalQuantity);
            }
            //2)拼装gxWMS接口需要的数据
            String warehouseId = purchaseOrder.getWarehouseId()+"";
            String warehouseName = purchaseOrder.getWarehouseName();
            String brandName = purchaseOrder.getBrandName();
            Long createdById = purchaseOrder.getCreatedById();//todo:目前先用采购单创建人ID,后面需改为计划单的创建人,包括联系电话
            String createdByName = purchaseOrder.getCreatedByName();
            Byte shippingMode = purchaseOrder.getShippingMode();
            int totalQuantity = planInboundBasicInfo.getTotalQuantity();

            int quantity=0;
            List<PlanInboundOrderItem> itemList = new ArrayList<>();
            for (PlanInboundItem planInboundItem : planInboundItemList) {
                int planInstockNumber = planInboundItem.getPlanInstockNumber();
                if(planInstockNumber==0){
                    continue;
                }
                quantity+=planInboundItem.getPlanInstockNumber();
                PlanInboundOrderItem inboundOrderItem = new PlanInboundOrderItem();
                inboundOrderItem.setPurchaseOrderNo(purchaseOrderNo);
                inboundOrderItem.setWarehouseId(warehouseId);
                inboundOrderItem.setWarehouseName(warehouseName);
                inboundOrderItem.setBrandId(purchaseOrder.getBrandId() + "");
                inboundOrderItem.setBrandName(brandName);
                inboundOrderItem.setProductId(planInboundItem.getProductId());
                inboundOrderItem.setProductCode(planInboundItem.getProductCode());
                inboundOrderItem.setProductName(planInboundItem.getProductName());
                inboundOrderItem.setProductUnit(planInboundItem.getProductUnit());
                inboundOrderItem.setTotalQuantity(planInboundItem.getPlanInstockNumber());
                inboundOrderItem.setInventoryType(WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType());

                itemList.add(inboundOrderItem);
            }
            //3)调用仓管接口得到相关订单号
            GongxiaoResult gongxiaoResult = inboundService.createInboundOrder(rpcHeader,
                    planInboundBasicInfo.getRequireInboundDate(),
                    purchaseOrder.getPurchaseType(),
                    purchaseOrder.getPurchaseOrderNo(),
                    WmsSourceChannel.CHANNEL_YHGLOBAL.getChannelId(),
                    WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode(),
                    projectId,
                    purchaseOrderNo,
                    createdByName,
                    "123456",
                    warehouseId,
                    warehouseName,
                    streetAddress,
                    shippingMode,
                    "飞鸟快递",
                    "123456",
                    purchaseOrder.getRemark(),
                    quantity,
                    itemList,
                    "FX预约入库",
                    rpcHeader.getTraceId());
            int returnCode = gongxiaoResult.getReturnCode();
            if (returnCode==5){
                logger.info("重复请求");
                return;
            }
            logger.info("同步到FX-WMS成功");
            String gongxiaoInboundOrderNo = (String) gongxiaoResult.getData();

            //6)记录预约入库流水
            PurchaseScheduleDelivery purchaseScheduleDelivery = new PurchaseScheduleDelivery();
            purchaseScheduleDelivery.setScheduleStatus( PurchasePlanStatus.INIT.getStatus());
            purchaseScheduleDelivery.setSyncToGongxiaoWarehouseFlag((byte) 1);
            purchaseScheduleDelivery.setGongxiaoWarehouseInboundOrderNo(gongxiaoInboundOrderNo);
            purchaseScheduleDelivery.setPurchaseOrderNo(purchaseOrderNo);
            purchaseScheduleDelivery.setWarehouseId(warehouseId);
            purchaseScheduleDelivery.setWarehouseName(warehouseName);
            purchaseScheduleDelivery.setShippingMode(purchaseOrder.getShippingMode());
            purchaseScheduleDelivery.setTotalQuantity(quantity);
            List<PlanInboundItemSummary> planItemSummaries = new ArrayList<>();
            for (PlanInboundItem planInboundItem : planInboundItemList) {
                PlanInboundItemSummary planInboundItemSummary = new PlanInboundItemSummary();
                planInboundItemSummary.setProductId(planInboundItem.getProductId());
                planInboundItemSummary.setProductCode(planInboundItem.getProductCode());
                planInboundItemSummary.setCurrencyCode(planInboundItem.getCurrencyCode());
                planInboundItemSummary.setPlanInboundQuantity(planInboundItem.getPurchaseNumber());
                planInboundItemSummary.setActualInboundQuantity(0);
                planInboundItemSummary.setOrderStatus(PurchasePlanStatus.INIT.getStatus());
                planItemSummaries.add(planInboundItemSummary);
            }
            String productJson = JSON.toJSONString(planItemSummaries);
            purchaseScheduleDelivery.setProductInfo(productJson);
            purchaseScheduleDeliveryDao.insert(purchaseScheduleDelivery);
            logger.info("记录预约入库流水成功");
            logger.info("#traceId={}# [OUT] planPurchaseOrder success: ", rpcHeader.traceId);
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }
}
