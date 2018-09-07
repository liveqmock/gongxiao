package com.yhglobal.gongxiao.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrder;
import com.yhglobal.gongxiao.eas.model.PurchaseBasicOrderItem;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductServiceGrpc;
import com.yhglobal.gongxiao.foundation.product.microservice.ProductStructure;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectServiceGrpc;
import com.yhglobal.gongxiao.foundation.project.microservice.ProjectStructure;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierServiceGrpc;
import com.yhglobal.gongxiao.foundation.supplier.microservice.SupplierStructure;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseServiceGrpc;
import com.yhglobal.gongxiao.foundation.warehouse.microservice.WarehouseStructure;
import com.yhglobal.gongxiao.microservice.GongxiaoRpc;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.status.PurchaseEasStatus;
import com.yhglobal.gongxiao.util.EasUtil;
import com.yhglobal.gongxiao.utils.TablePrefixUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.yhglobal.gongxiao.grpc.client.RpcStubStore.*;

/**
 * 把采购单同步到EAS
 */
public class SyncPurchaseOrderToEasTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SyncPurchaseOrderToEasTask.class);


    private ApplicationContext applicationContext;

    private GongxiaoRpc.RpcHeader rpcHeader;

    private PurchaseOrder purchaseOrderInfo;

    private List<PurchaseOrderItem> purchaseOrderItemList;

    public SyncPurchaseOrderToEasTask(ApplicationContext applicationContext,
                                      GongxiaoRpc.RpcHeader rpcHeader,
                                      PurchaseOrder purchaseOrderInfo,
                                      List<PurchaseOrderItem> purchaseOrderItemList) {
        this.applicationContext = applicationContext;
        this.rpcHeader = rpcHeader;
        this.purchaseOrderInfo = purchaseOrderInfo;
        this.purchaseOrderItemList = purchaseOrderItemList;
    }

    @Override
    public void run() {
        logger.info("traceId={} 开启同步EAS的线程",rpcHeader.getTraceId());
        try {
            Date date = new Date();

            PurchaseOrderItemDao purchaseOrderItemDao = applicationContext.getBean("purchaseOrderItemDao", PurchaseOrderItemDao.class);
            PurchaseOrderDao purchaseOrderDao = applicationContext.getBean("purchaseOrderDao", PurchaseOrderDao.class);

            ProjectServiceGrpc.ProjectServiceBlockingStub projectRpcStub = getRpcStub(ProjectServiceGrpc.ProjectServiceBlockingStub.class);
            SupplierServiceGrpc.SupplierServiceBlockingStub supplierRpcStub = getRpcStub(SupplierServiceGrpc.SupplierServiceBlockingStub.class);
            WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseRpcStub = getRpcStub(WarehouseServiceGrpc.WarehouseServiceBlockingStub.class);
            ProductServiceGrpc.ProductServiceBlockingStub productStub = getRpcStub(ProductServiceGrpc.ProductServiceBlockingStub.class);


            ProjectStructure.GetByProjectIdReq getByProjectIdReq = ProjectStructure.GetByProjectIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setProjectId(purchaseOrderInfo.getProjectId() + "")
                    .build();

            SupplierStructure.GetSupplierByIdReq getSupplierByIdReq = SupplierStructure.GetSupplierByIdReq.newBuilder()
                    .setSupplierId(purchaseOrderInfo.getSupplierId() + "")
                    .setRpcHeader(rpcHeader)
                    .build();

            WarehouseStructure.GetWarehouseByIdReq getWarehouseByIdReq = WarehouseStructure.GetWarehouseByIdReq.newBuilder()
                    .setRpcHeader(rpcHeader)
                    .setWarehouseId(purchaseOrderInfo.getWarehouseId() + "")
                    .build();
            ProjectStructure.GetByProjectIdResp projectResp = projectRpcStub.getByProjectId(getByProjectIdReq);
            ProjectStructure.Project project = projectResp.getProject();
            SupplierStructure.GetSupplierByIdResp supplierResp = supplierRpcStub.getSupplierById(getSupplierByIdReq);
            SupplierStructure.Supplier supplier = supplierResp.getSupplier();
            WarehouseStructure.GetWarehouseByIdResp warehouseResp = warehouseRpcStub.getWarehouseById(getWarehouseByIdReq);
            WarehouseStructure.Warehouse warehouse = warehouseResp.getWarehouse();
            logger.info("traceId={} 获取基础资料数据完成",rpcHeader.getTraceId());

            long projectId = project.getProjectId();
            String tablePrefix = TablePrefixUtil.getTablePrefixByProjectId(projectId);

            double totalAmount = 0;
            logger.info("开始同步到EAS");
            //2)同步EAS
            PurchaseBasicOrder purchaseBasicOrder = new PurchaseBasicOrder();
            List<PurchaseBasicOrderItem> purchaseBasicOrderItemList = new ArrayList<>();
            for (PurchaseOrderItem createPurchaseItemInfo : purchaseOrderItemList) {
                double discount =  (1-((double)createPurchaseItemInfo.getCostPrice()/createPurchaseItemInfo.getPurchasePrice()))*100;
                //2.1 设置EAS货品信息
                ProductStructure.GetByProductIdReq getByProductIdReq = ProductStructure.GetByProductIdReq.newBuilder()
                        .setRpcHeader(rpcHeader)
                        .setProjectId(projectId)
                        .setProductId(Long.parseLong(createPurchaseItemInfo.getProductId()))
                        .build();
                ProductStructure.GetByProductIdResp productResp = productStub.getByProductId(getByProductIdReq);
                ProductStructure.ProductBusiness productBasic = productResp.getProductBusiness();
                totalAmount += (createPurchaseItemInfo.getPurchaseNumber() * createPurchaseItemInfo.getPurchasePrice() / FXConstant.MILLION);
                PurchaseBasicOrderItem purchaseBasicOrderItem = new PurchaseBasicOrderItem();
                purchaseBasicOrderItem.setNumber(createPurchaseItemInfo.getPurchaseNumber());
                purchaseBasicOrderItem.setTaxPrice((double) createPurchaseItemInfo.getPurchasePrice() / FXConstant.MILLION);
                purchaseBasicOrderItem.setDiscountRate(discount);
                purchaseBasicOrderItem.setTaxRate(FXConstant.TAX_RATE);
                purchaseBasicOrderItem.setDeliveryDate(purchaseOrderInfo.getOrderDeadlineDate());
                purchaseBasicOrderItem.setBizDate(purchaseOrderInfo.getBusinessDate());
                purchaseBasicOrderItem.setWarehouseId(warehouse.getEasWarehouseCode());
                purchaseBasicOrderItem.setMaterialId(productBasic.getEasCode());
                purchaseBasicOrderItem.setBizDate(purchaseOrderInfo.getBusinessDate());
                purchaseBasicOrderItem.setUnit(productBasic.getEasUnitCode());
                purchaseBasicOrderItem.setProductCode(createPurchaseItemInfo.getProductCode());
                purchaseBasicOrderItem.setUnit(productBasic.getEasUnitCode());
                purchaseBasicOrderItemList.add(purchaseBasicOrderItem);
            }
            //2.2设置EAS订单信息
            purchaseBasicOrder.setTotalTaxAmount(totalAmount);
            double totalTax = totalAmount * FXConstant.TAX_RATE / 100.0 / (1 + FXConstant.TAX_RATE / 100.0);
            purchaseBasicOrder.setTotalTax(totalTax);
            purchaseBasicOrder.setSupplierCode(supplier.getEasSupplierCode());
            purchaseBasicOrder.setProjectId(project.getEasProjectCode());
            purchaseBasicOrder.setNumber(purchaseOrderInfo.getTotalQuantity());
            purchaseBasicOrder.setBusinessDate(purchaseOrderInfo.getBusinessDate());
            purchaseBasicOrder.setRequireArrivalDate(purchaseOrderInfo.getExpectedInboundDate());
            //2.3同步到EAS
            try {
                String easInfoString = EasUtil.sendPurchaseOrder2Eas(purchaseBasicOrder, purchaseBasicOrderItemList);
                EasResult easResult = JSON.parseObject(easInfoString, new TypeReference<EasResult>() {
                });
                List<EasResultItem> entryList = easResult.getEntryList();
                //2.4 向EAS返回的数据中插入分销系统的产品编码
                for (EasResultItem easResultItem : entryList) {
                    String materialCode = easResultItem.getMaterialCode();
                    for (PurchaseBasicOrderItem purchaseBasicOrderItem : purchaseBasicOrderItemList) {
                        String materialId = purchaseBasicOrderItem.getMaterialId();
                        if (materialCode.equals(materialId)) {
                            easResultItem.setProductCode(purchaseBasicOrderItem.getProductCode());
                        }
                    }
                }
                //3)更新采购单EAS相关信息
                for (PurchaseOrderItem purchaseOrderItem : purchaseOrderItemList) {
                    String productCode1 = purchaseOrderItem.getProductCode();
                    for (EasResultItem easResultItem : entryList) {
                        String productCode = easResultItem.getProductCode();
                        if (productCode.equals(productCode1)) {
                            purchaseOrderItem.setEasEntryId(easResultItem.getEntryId());
                            purchaseOrderItem.setEasMateriaCode(easResultItem.getMaterialCode());
                        }
                    }
                    purchaseOrderItemDao.updateEasInfo(tablePrefix,
                            purchaseOrderItem.getPurchaseItemId(),
                            purchaseOrderItem.getEasEntryId(),
                            purchaseOrderItem.getEasMateriaCode());
                }
                purchaseOrderInfo.setEasOrderNumber(easResult.getOrderNumber());
                purchaseOrderInfo.setEasPurchaseOrderId(easResult.getId());
                purchaseOrderInfo.setEasStatus(PurchaseEasStatus.SYN_EAS_SUCCESS.getStatus());
                purchaseOrderDao.updateEasInfo(tablePrefix,
                        purchaseOrderInfo.getPurchaseOrderNo(),
                        easResult.getId(),
                        easResult.getOrderNumber(),
                        PurchaseEasStatus.SYN_EAS_SUCCESS.getStatus()
                );
                logger.info("采购单号:{} ,EAS采购单号:{} 更新EAS回执到采购单完成", purchaseOrderInfo.getPurchaseOrderNo(), purchaseOrderInfo.getEasOrderNumber());
                logger.info("#traceId={}# [OUT] generatePurchaseOrder success: ", rpcHeader.getTraceId());
            } catch (Exception e) {
                String message = e.getMessage();
                if (message != null && message.toLowerCase().contains("read")) {
                    logger.info("采购单号:{} ,处理超时", purchaseOrderInfo.getPurchaseOrderNo());
                    purchaseOrderDao.updatePurchaseEasStatus(tablePrefix,
                            purchaseOrderInfo.getPurchaseOrderId(),
                            PurchaseEasStatus.WAIT_HANDLE.getStatus(),
                            PurchaseEasStatus.SYN_EAS_TEMP.getStatus());
                } else {
                    logger.info("采购单号:{} ,连接超时", purchaseOrderInfo.getPurchaseOrderNo());
                    purchaseOrderDao.updatePurchaseEasStatus(tablePrefix,
                            purchaseOrderInfo.getPurchaseOrderId(),
                            PurchaseEasStatus.SYN_EAS_FAILURE.getStatus(),
                            PurchaseEasStatus.SYN_EAS_TEMP.getStatus());
                }
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.getTraceId() + "# errorMessage:" + e.getMessage(), e);
        }
    }

}
