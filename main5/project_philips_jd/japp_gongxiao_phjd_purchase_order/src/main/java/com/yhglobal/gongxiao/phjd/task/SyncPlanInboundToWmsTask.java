package com.yhglobal.gongxiao.phjd.task;

import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.GongxiaoResult;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.phjd.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.phjd.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.phjd.dao.PurchaseScheduleDeliveryDao;
import com.yhglobal.gongxiao.phjd.model.PlanInboundItemSummary;
import com.yhglobal.gongxiao.phjd.model.PurchaseOrder;
import com.yhglobal.gongxiao.phjd.model.PurchaseScheduleDelivery;
import com.yhglobal.gongxiao.phjd.purchase.microservice.PurchaseStructure;
import com.yhglobal.gongxiao.phjd.util.PropertyUtil;
import com.yhglobal.gongxiao.type.WmsInventoryType;
import com.yhglobal.gongxiao.type.WmsOrderType;
import com.yhglobal.gongxiao.util.DateUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouseapi.XpsWarehouseManager;
import com.yhglobal.gongxiao.warehouseapi.model.PlanInboundOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购订单 创建 预约入库单后，需同步到WMS
 */
public class SyncPlanInboundToWmsTask implements Runnable{

    private Logger logger = LoggerFactory.getLogger(SyncPlanInboundToWmsTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;

    private ApplicationContext applicationContext;

    private PurchaseStructure.PlanInboundBasicInfo planInboundBasicInfo;

    private List<PurchaseStructure.PlanInboundItem> planInboundItemList;

    private String streetAddress;

    public SyncPlanInboundToWmsTask(GongxiaoRpc.RpcHeader rpcHeader,
                                    ApplicationContext applicationContext,
                                    PurchaseStructure.PlanInboundBasicInfo planInboundBasicInfo,
                                    List<PurchaseStructure.PlanInboundItem> planInboundItemList,
                                    String streetAddress){
        this.rpcHeader=rpcHeader;
        this.applicationContext=applicationContext;
        this.planInboundBasicInfo=planInboundBasicInfo;
        this.planInboundItemList=planInboundItemList;
        this.streetAddress = streetAddress;
    }


    @Override
    public void run() {
        try {

            logger.warn("==traceid={}===planInboundBasicInfo===== ={}",rpcHeader.getTraceId(),planInboundBasicInfo.toString());
            PurchaseOrderDao purchaseOrderDao = applicationContext.getBean("purchaseOrderDao", PurchaseOrderDao.class);
            PurchaseOrderItemDao purchaseOrderItemDao = applicationContext.getBean("purchaseOrderItemDao", PurchaseOrderItemDao.class);
            PurchaseScheduleDeliveryDao purchaseScheduleDeliveryDao = applicationContext.getBean("purchaseScheduleDeliveryDao", PurchaseScheduleDeliveryDao.class); //预约入库表的dao
            PropertyUtil propertyUtil = applicationContext.getBean("propertyUtil", PropertyUtil.class);

            String projectId = planInboundBasicInfo.getProjectId();
            String purchaseOrderNo = planInboundBasicInfo.getPurchaseOrderNo();
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(Long.parseLong(projectId));

            //1)获取采购单信息
            PurchaseOrder purchaseOrder = purchaseOrderDao.selectByPurchaseNo(tablePrefix,purchaseOrderNo);
            Map itemMap = new HashMap(); //保存商品明细 各商品编码对应的采购数量
            for (PurchaseStructure.PlanInboundItem purchaseOrderItem : planInboundItemList) {
                String productCode = purchaseOrderItem.getProductCode();
                int totalQuantity = purchaseOrderItem.getPlanInstockNumber();
                itemMap.put(productCode, totalQuantity);
            }
            //2)拼装xps warehouse接口需要的数据
            String warehouseId = purchaseOrder.getWarehouseId()+"";
            String warehouseName = purchaseOrder.getWarehouseName();
            String brandName = purchaseOrder.getBrandName();
            Long createdById = purchaseOrder.getCreatedById();//todo:目前先用采购单创建人ID,后面需改为计划单的创建人,包括联系电话
            String createdByName = purchaseOrder.getCreatedByName();
            Byte shippingMode = purchaseOrder.getShippingMode();

            int quantity=0;
            List<PlanInboundOrderItem> itemList = new ArrayList<>(); //保存待入库商品的list 以便传递给warehouse接口
            for (PurchaseStructure.PlanInboundItem planInboundItem : planInboundItemList) {
                int planInstockNumber = planInboundItem.getPlanInstockNumber();
                if(planInstockNumber==0){ //若预约入库数量为0 则跳过
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
                inboundOrderItem.setCostPrice((long)Double.parseDouble(planInboundItem.getCostValue())* FXConstant.MILLION);
                inboundOrderItem.setPurchasePrice((long)Double.parseDouble(planInboundItem.getGuidePrice())* FXConstant.MILLION );

                itemList.add(inboundOrderItem);
            }
            String warehouseUrl = propertyUtil.getWarehouseUrl();
            //3)调用xps warehouse接口得到相关订单号
            GongxiaoResult gongxiaoResult = XpsWarehouseManager.createInboundOrder(warehouseUrl,
                    propertyUtil.getChannel(),
                    "",
                    DateUtil.long2Date(planInboundBasicInfo.getRequireInboundDate()),
                    purchaseOrder.getPurchaseType(),
                    purchaseOrder.getPurchaseOrderNo(),
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
            if (returnCode==5){ //TODO: 定义为ENUM
                logger.info("重复请求");
                return;
            }
            logger.info("同步到FX-WMS成功");
            String gongxiaoInboundOrderNo = (String) gongxiaoResult.getData();

            //6)记录预约入库流水
            PurchaseScheduleDelivery purchaseScheduleDelivery = new PurchaseScheduleDelivery();
            purchaseScheduleDelivery.setScheduleStatus((byte) 0);
            purchaseScheduleDelivery.setSyncToGongxiaoWarehouseFlag((byte) 1);
            purchaseScheduleDelivery.setGongxiaoWarehouseInboundOrderNo(gongxiaoInboundOrderNo);
            purchaseScheduleDelivery.setPurchaseOrderNo(purchaseOrderNo);
            purchaseScheduleDelivery.setWarehouseId(warehouseId);
            purchaseScheduleDelivery.setWarehouseName(warehouseName);
            purchaseScheduleDelivery.setShippingMode(purchaseOrder.getShippingMode());
            purchaseScheduleDelivery.setTotalQuantity(quantity);
            List<PlanInboundItemSummary> planItemSummaries = new ArrayList<>();
            for (PurchaseStructure.PlanInboundItem planInboundItem : planInboundItemList) {
                PlanInboundItemSummary planInboundItemSummary = new PlanInboundItemSummary();
                planInboundItemSummary.setProductId(planInboundItem.getProductId());
                planInboundItemSummary.setProductCode(planInboundItem.getProductCode());
                planInboundItemSummary.setCurrencyCode(planInboundItem.getCurrencyCode());
                planInboundItemSummary.setPlanInboundQuantity(planInboundItem.getPurchaseNumber());
                planInboundItemSummary.setActualInboundQuantity(0);
                planInboundItemSummary.setOrderStatus((byte) 0);
                planItemSummaries.add(planInboundItemSummary);
            }
            String productJson = JSON.toJSONString(planItemSummaries);
            purchaseScheduleDelivery.setProductInfo(productJson);
            purchaseScheduleDelivery.setTablePrefix(tablePrefix);
            purchaseScheduleDeliveryDao.insert(purchaseScheduleDelivery);
            logger.info("记录预约入库流水成功");
        } catch (Exception e) {
            logger.error("# [OUT] errorMessage: " + e.getMessage(), e);
        }
    }
}
