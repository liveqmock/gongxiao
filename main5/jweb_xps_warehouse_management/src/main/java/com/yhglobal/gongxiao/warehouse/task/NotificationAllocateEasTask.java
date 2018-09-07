package com.yhglobal.gongxiao.warehouse.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.diaobo.AllocateEasBasicOrder;
import com.yhglobal.gongxiao.diaobo.AllocateEasItemOrder;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.grpc.client.GlobalRpcStubStore;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.EasResult;
import com.yhglobal.gongxiao.model.EasResultItem;
import com.yhglobal.gongxiao.type.PurchaseTypeStatus;
import com.yhglobal.gongxiao.type.SyncEasEnum;
import com.yhglobal.gongxiao.type.SyncWmsEnum;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.util.TablePrefixUtil;
import com.yhglobal.gongxiao.warehouse.dao.*;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrder;
import com.yhglobal.gongxiao.warehouse.model.AllocationOrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationAllocateEasTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(NotificationAllocateEasTask.class);

    private GongxiaoRpc.RpcHeader rpcHeader;
    private AllocationOrder allocationOrder;
    private List<AllocationOrderItem> allocationOrderItemList;
    private AllocateOrderDao allocateOrderDao;
    private AllocateOrderItemDao allocateOrderItemDao;
    private InBoundOrderDao inBoundOrderDao;
    private OutBoundOrderDao outBoundOrderDao;


    public NotificationAllocateEasTask(GongxiaoRpc.RpcHeader rpcHeader, AllocationOrder allocationOrder, List<AllocationOrderItem> allocationOrderItemList, AllocateOrderDao allocateOrderDao, AllocateOrderItemDao allocateOrderItemDao, InBoundOrderDao inBoundOrderDao, OutBoundOrderDao outBoundOrderDao) {
        this.rpcHeader = rpcHeader;
        this.allocationOrder = allocationOrder;
        this.allocationOrderItemList = allocationOrderItemList;
        this.allocateOrderDao = allocateOrderDao;
        this.allocateOrderItemDao = allocateOrderItemDao;
        this.inBoundOrderDao = inBoundOrderDao;
        this.outBoundOrderDao = outBoundOrderDao;
    }

    @Override
    public void run() {
        logger.info("#traceId={}# [IN][run] start NotificationAllocateEasTask.run() params: allocationOrder={},allocationOrderItemList={}", rpcHeader.getTraceId(), JSON.toJSONString(allocationOrder), JSON.toJSONString(allocationOrderItemList));

        try {
            //更新调拨单的状态为同步中,根据更新的结果来判断是否抢到数据
           int i =  allocateOrderDao.updateAllocateEasIng(allocationOrder.getAllocateNo(),SyncEasEnum.SYNC_EAS_ING.getStatus(), allocationOrder.getDataVersion());
           if (i == 1){
               ProjectServiceGrpc.ProjectServiceBlockingStub projectRpcStub = GlobalRpcStubStore.getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
               WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseRpcStub =  GlobalRpcStubStore.getRpcStub((WarehouseServiceGrpc.WarehouseServiceBlockingStub.class));
               ProductServiceGrpc.ProductServiceBlockingStub productStub =  GlobalRpcStubStore.getRpcStub((ProductServiceGrpc.ProductServiceBlockingStub.class));


               ProjectStructure.GetByProjectIdReq getByEntryProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder()
                       .setRpcHeader(rpcHeader)
                       .setProjectId(allocationOrder.getProjectIdEnter() + "")
                       .build();

               ProjectStructure.GetByProjectIdReq getByOutProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder()
                       .setRpcHeader(rpcHeader)
                       .setProjectId(allocationOrder.getProjectIdEnter() + "")
                       .build();

               WarehouseStructure.GetWarehouseByIdReq getWarehouseInReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                       .setRpcHeader(rpcHeader)
                       .setWarehouseId(allocationOrder.getWarehouseEnterId() + "")
                       .build();

               WarehouseStructure.GetWarehouseByIdReq getWarehouseOutReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                       .setRpcHeader(rpcHeader)
                       .setWarehouseId(allocationOrder.getWarehouseOutId() + "")
                       .build();

               ProjectStructure.GetByProjectIdResp entryProjectResp = projectRpcStub.getByProjectId(getByEntryProjectIdReq);
               ProjectStructure.Project entryProject = entryProjectResp.getProject();

               ProjectStructure.GetByProjectIdResp outProjectResp = projectRpcStub.getByProjectId(getByEntryProjectIdReq);
               ProjectStructure.Project outProject = outProjectResp.getProject();

               WarehouseStructure.GetWarehouseByIdResp warehouseInResp = warehouseRpcStub.getWarehouseById(getWarehouseInReq);
               WarehouseStructure.Warehouse warehouseIn = warehouseInResp.getWarehouse();

               WarehouseStructure.GetWarehouseByIdResp warehouseOutResp = warehouseRpcStub.getWarehouseById(getWarehouseOutReq);
               WarehouseStructure.Warehouse warehouseOut = warehouseOutResp.getWarehouse();
               logger.info("traceId={} 获取基础资料数据完成",rpcHeader.getTraceId());


               logger.info("开始同步到EAS");
               //2)同步EAS
               AllocateEasBasicOrder allocateEasBasicOrder = new AllocateEasBasicOrder();
               List<AllocateEasItemOrder> allocateEasItemOrderList = new ArrayList<>();
               int seq = 1;
               for (AllocationOrderItem allocationOrderItem : allocationOrderItemList) {
                   double discount =  0.0;
                   //2.1 设置EAS货品信息
                   ProductStructure.GetByProductIdReq getByProductIdReq = ProductStructure.GetByProductIdReq.newBuilder()
                           .setRpcHeader(rpcHeader)
                           .setProjectId(Long.parseLong(allocationOrderItem.getProjectIdOut()))  //调出项目id
                           .setProductId(Long.parseLong(allocationOrderItem.getProductId()))
                           .build();
                   ProductStructure.GetByProductIdResp productResp = productStub.getByProductId(getByProductIdReq);
                   ProductStructure.ProductBusiness productBasic = productResp.getProductBusiness();

                   AllocateEasItemOrder allocateEasItemOrder = new AllocateEasItemOrder();
                   allocateEasItemOrder.setAllocateNo(allocationOrder.getAllocateNo());
                   allocateEasItemOrder.setProductCode(allocationOrderItem.getProductCode());
                   allocateEasItemOrder.setSeq(seq);
                   allocateEasItemOrder.setAsscoefficient(0.0);
                   allocateEasItemOrder.setAssociateQty(0);
                   allocateEasItemOrder.setAssistQty(allocationOrderItem.getAlloteQuantity());
                   allocateEasItemOrder.setMaterialID(productBasic.getEasCode());
                   allocateEasItemOrder.setUnitID("TAI");
                   allocateEasItemOrder.setBaseUnitID("TAI");
                   allocateEasItemOrder.setIssueWarehouseID(warehouseOut.getEasWarehouseCode());
                   allocateEasItemOrder.setReceiptWarehouseID(warehouseIn.getEasWarehouseCode());
                   allocateEasItemOrder.setLot(allocationOrderItem.getBatchNo());
                   allocateEasItemOrder.setQty(allocationOrderItem.getAlloteQuantity());
                   allocateEasItemOrder.setBaseQty(allocationOrderItem.getAlloteQuantity());
                   allocateEasItemOrder.setIssueQty(allocationOrderItem.getAlloteQuantity());
                   allocateEasItemOrder.setReceiptPlanDate(allocationOrder.getDeadline());
                   allocateEasItemOrder.setPrice(allocationOrderItem.getPuchasePrice()/1000000.0);
                   if(allocationOrderItem.getPurchaseType() == PurchaseTypeStatus.GIFT_PURCHASE.getStatus()){
                       allocateEasItemOrder.setIsPresent(1);
                   }else {
                       allocateEasItemOrder.setIsPresent(0);
                   }
                   allocateEasItemOrder.setTaxPrice(allocationOrderItem.getPuchasePrice()/FXConstant.MILLION);
                   allocateEasItemOrder.setTaxAmount((allocationOrderItem.getAlloteQuantity()*allocationOrderItem.getPuchasePrice())/FXConstant.MILLION);
                   allocateEasItemOrder.setPruinvoiceAmount(0);
                   allocateEasItemOrder.setSaleinvoiceAmount(0);
                   allocateEasItemOrder.setTotalAccountPayable(0);
                   allocateEasItemOrder.setTotalAccountReceivable(0);
                   allocateEasItemOrder.setIssueLocationID("002.01");
                   allocateEasItemOrder.setReceiptLocationID("001.01");
                   allocateEasItemOrder.setIssueStorageOrgUnitID("02029901");
                   allocateEasItemOrder.setIssueCompanyOrgUnitID("0202");
                   allocateEasItemOrder.setReceiveStorageOrgUnitID("02029901");
                   allocateEasItemOrder.setReceiveCompanyOrgUnitID("0202");
                   allocateEasItemOrder.setMaterialID(productBasic.getEasCode());
                   allocateEasItemOrder.setBalancecostPrice(allocationOrderItem.getPuchasePrice()/1000000.0);
                   allocateEasItemOrder.setBalancecostRate(1.0);
                   allocateEasItemOrder.setActualPrice(allocationOrderItem.getCostPrice()/1000000.0);
                   allocateEasItemOrder.setDiscountType(0);
                   allocateEasItemOrder.setDiscountRate(1.0);
                   allocateEasItemOrder.setActualTaxPrice(allocationOrderItem.getCostPrice()/FXConstant.TAX_RATE);
                   allocateEasItemOrder.setLocalAmount(allocationOrderItem.getPuchasePrice()*allocationOrderItem.getAlloteQuantity());
                   allocateEasItemOrder.setLocalTaxAmount(allocationOrderItem.getPuchasePrice()*allocationOrderItem.getAlloteQuantity()/FXConstant.TAX_RATE);
                   allocateEasItemOrder.setDiscountAmoumt(0);
                   allocateEasItemOrder.setMfg(allocationOrderItem.getCreateTime().toString());
                   allocateEasItemOrder.setExp(allocationOrder.getDeadline());
                   allocateEasItemOrder.setIssuePlanDate(allocationOrder.getRequireTime());
                   allocateEasItemOrder.setStoreTypeID("G");
                   allocateEasItemOrder.setIsmrpcal(0);

                   allocateEasItemOrderList.add(allocateEasItemOrder);
                   seq++;
               }
               //2.2设置EAS订单信息
               allocateEasBasicOrder.setAllocateNo(allocationOrder.getAllocateNo());
               allocateEasBasicOrder.setCreatorID("user");
               allocateEasBasicOrder.setCreateTime(allocationOrder.getCreateTime());
               allocateEasBasicOrder.setLastUpdateTime(allocationOrder.getLastUpdateTime());
               allocateEasBasicOrder.setBizDate(allocationOrder.getCreateTime());
               allocateEasBasicOrder.setHasEffected(1);
               allocateEasBasicOrder.setBizType("331");
               allocateEasBasicOrder.setIssueCompanyorgUnitID("0202");
               allocateEasBasicOrder.setReceIptCompanyorgUnitID("0202");
               allocateEasBasicOrder.setIssueStorageorgUnitID("02029901");
               allocateEasBasicOrder.setIssueAdminorgUnitID("");
               allocateEasBasicOrder.setReceIptStorageorgUnitID("02029901");
               allocateEasBasicOrder.setIsShipment(0);
               allocateEasBasicOrder.setModificationTime(allocationOrder.getLastUpdateTime());
               allocateEasBasicOrder.setCurrencyID("BB01");
               allocateEasBasicOrder.setExchangeRate("1");
               allocateEasBasicOrder.setIsIntax(0);

               //2.3同步到EAS
               try {
                   String easInfoString = EasUtil.sendTransforWare2Eas(allocateEasBasicOrder, allocateEasItemOrderList);
                   EasResult easResult = JSON.parseObject(easInfoString, new TypeReference<EasResult>() {
                   });
                   List<EasResultItem> entryList = easResult.getEntryList();
                   //2.4 向EAS返回的数据中插入分销系统的产品编码
                   for (EasResultItem easResultItem : entryList) {
                       int itemSeq = Integer.parseInt(easResultItem.getSeq());
                       for (AllocateEasItemOrder allocateEasItemOrder : allocateEasItemOrderList) {
                           if (itemSeq == allocateEasItemOrder.getSeq()) {
                               easResultItem.setProductCode(allocateEasItemOrder.getProductCode());
                           }
                       }
                   }
                   //2.3)更新调拨单EAS相关信息
                   for (AllocationOrderItem allocationOrderItem : allocationOrderItemList) {
                       String productCode1 = allocationOrderItem.getProductCode();
                       for (EasResultItem easResultItem : entryList) {
                           String productCode = easResultItem.getProductCode();
                           if (productCode.equals(productCode1)) {
                               allocationOrderItem.setEntryId(easResultItem.getEntryId());
                               allocationOrderItem.setMaterialCode(easResultItem.getMaterialCode());
                           }
                       }
                       allocateOrderItemDao.updateItemEasInfo(allocationOrderItem.getId(), allocationOrderItem.getEntryId(), allocationOrderItem.getMaterialCode());
                   }
                   allocationOrder.setEasOrderNumber(easResult.getOrderNumber());
                   allocationOrder.setEasId(easResult.getId());
                   allocationOrder.setSyncEas(SyncEasEnum.SYNC_EAS_SUCCESS.getStatus());
                   allocateOrderDao.syncAllocateEasSuccess(allocationOrder.getId(), easResult.getId(), easResult.getOrderNumber(), SyncEasEnum.SYNC_EAS_SUCCESS.getStatus());

                   //修改入库单和出库单的状态，将调拨入库通知单和调拨出库通知单 推送给wms
                   String entryTablePrefix = TablePrefixUtil.getTablePrefixByProjectId(entryProject.getProjectId());
                   String outTablePrefix = TablePrefixUtil.getTablePrefixByProjectId(outProject.getProjectId());
                   inBoundOrderDao.updateTrsInWmsStatus(allocationOrder.getGongxiaoInboundOrderNo(), SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), entryTablePrefix);     //将调拨入库通知单 推送wms的状态更新为需要同步到wms
                   outBoundOrderDao.updateOutboundWmsStatus(allocationOrder.getGongxiaoOutboundOrderNo(), SyncWmsEnum.SYNC_WMS_FAIL.getStatus(), outTablePrefix);  //更新调拨出库单推送wms的状态

                   logger.info("调拨单号:{} ,EAS调拨单号:{} 更新EAS回执到采购单完成", allocationOrder.getAllocateNo(), allocationOrder.getEasOrderNumber());
                   logger.info("#traceId={}# [OUT] generatePurchaseOrder success: ", rpcHeader.getTraceId());
               } catch (Exception e) {
                   String message = e.getMessage();
                   if (message != null && message.toLowerCase().contains("read")) {
                       logger.error("# errorMessage:" + e.getMessage(), e);
                       logger.info("调拨单号:{} ,处理超时", allocationOrder.getAllocateNo());
                       allocateOrderDao.syncAllocateEasException(allocationOrder.getId(), SyncEasEnum.SYNC_EAS_EXCEPTION.getStatus());
                   } else {
                       logger.error("# errorMessage:" + e.getMessage(), e);
                       allocateOrderDao.syncAllocateEasFail(allocationOrder.getId(),  SyncEasEnum.SYNC_EAS_FAIL.getStatus());
                   }
               }
           }else {
               logger.info("#traceId={}# [OUT] get NotificationAllocateEasTask success,allocateOrderNo={},线程没有抢到数据，线程结束", rpcHeader.getTraceId(), allocationOrder.getAllocateNo());
           }

        } catch (Exception e) {
            allocateOrderDao.syncAllocateEasFail(allocationOrder.getId(), SyncEasEnum.SYNC_EAS_FAIL.getStatus());
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }

        logger.info("#traceId={}# [OUT] get NotificationAllocateEasTask.notifyWarehouseStockInbound() success", rpcHeader.getTraceId());
    }
}
