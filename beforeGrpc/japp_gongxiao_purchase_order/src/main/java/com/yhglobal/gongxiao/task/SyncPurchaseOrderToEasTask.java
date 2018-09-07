package com.yhglobal.gongxiao.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.FXConstant;
import com.yhglobal.gongxiao.dao.PurchaseOrderDao;
import com.yhglobal.gongxiao.dao.PurchaseOrderItemDao;
import com.yhglobal.gongxiao.eas.PurchaseBasicOrder;
import com.yhglobal.gongxiao.eas.PurchaseBasicOrderItem;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.supplier.model.Supplier;
import com.yhglobal.gongxiao.foundation.supplier.service.SupplierService;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.id.BizNumberType;
import com.yhglobal.gongxiao.model.*;
import com.yhglobal.gongxiao.payment.service.SupplierAccountService;
import com.yhglobal.gongxiao.purchase.bo.CreatePurchaseBasicInfo;
import com.yhglobal.gongxiao.purchase.bo.CreatePurchaseItemInfo;
import com.yhglobal.gongxiao.purchase.temp.OperateHistoryRecord;
import com.yhglobal.gongxiao.status.PurchaseEasStatus;
import com.yhglobal.gongxiao.util.EasUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 把采购单同步到EAS
 */
public class SyncPurchaseOrderToEasTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(SyncPurchaseOrderToEasTask.class);


    private ApplicationContext applicationContext;

    private RpcHeader rpcHeader;

    private ProjectService projectService;

    private SupplierService supplierService;

    private WarehouseService warehouseService;

    private ProductService productService;

    private SupplierAccountService supplierAccountService;

    private PurchaseOrder purchaseOrderInfo;

    private List<PurchaseOrderItem> purchaseOrderItemList;

    public SyncPurchaseOrderToEasTask(ApplicationContext applicationContext,
                                      RpcHeader rpcHeader,
                                      ProjectService projectService,
                                      SupplierService supplierService,
                                      WarehouseService warehouseService,
                                      ProductService productService,
                                      SupplierAccountService supplierAccountService,
                                      PurchaseOrder purchaseOrderInfo,
                                      List<PurchaseOrderItem> purchaseOrderItemList) {
        this.applicationContext = applicationContext;
        this.rpcHeader = rpcHeader;
        this.projectService = projectService;
        this.supplierService = supplierService;
        this.warehouseService = warehouseService;
        this.productService = productService;
        this.supplierAccountService = supplierAccountService;
        this.purchaseOrderInfo = purchaseOrderInfo;
        this.purchaseOrderItemList = purchaseOrderItemList;
    }

    @Override
    public void run() {
        try {
            Date date = new Date();

            PurchaseOrderItemDao purchaseOrderItemDao = applicationContext.getBean("purchaseOrderItemDao", PurchaseOrderItemDao.class);
            PurchaseOrderDao purchaseOrderDao = applicationContext.getBean("purchaseOrderDao", PurchaseOrderDao.class);
            Project project = projectService.getByProjectId(rpcHeader, purchaseOrderInfo.getProjectId() + "");
            Supplier supplier = supplierService.getSupplierById(rpcHeader, purchaseOrderInfo.getSupplierId());
            Warehouse warehouse = warehouseService.getWarehouseById(rpcHeader, purchaseOrderInfo.getWarehouseId() + "");

            double totalAmount = 0;
            logger.info("开始同步到EAS");
            //2)同步EAS
            PurchaseBasicOrder purchaseBasicOrder = new PurchaseBasicOrder();
            List<PurchaseBasicOrderItem> purchaseBasicOrderItemList = new ArrayList<>();
            for (PurchaseOrderItem createPurchaseItemInfo : purchaseOrderItemList) {
                double discount = (1-((double)createPurchaseItemInfo.getCostPrice()/createPurchaseItemInfo.getPurchasePrice()))*100;
                //2.1 设置EAS货品信息
                ProductBasic productBasic = productService.getByProductId(rpcHeader, createPurchaseItemInfo.getProductId());
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
                    purchaseOrderItemDao.updateEasInfo(purchaseOrderItem.getPurchaseItemId(),
                            purchaseOrderItem.getEasEntryId(),
                            purchaseOrderItem.getEasMateriaCode());
                }
                purchaseOrderInfo.setEasOrderNumber(easResult.getOrderNumber());
                purchaseOrderInfo.setEasPurchaseOrderId(easResult.getId());
                purchaseOrderInfo.setEasStatus(PurchaseEasStatus.SYN_EAS_SUCCESS.getStatus());
                purchaseOrderDao.updateEasInfo(purchaseOrderInfo.getPurchaseOrderNo(),
                        easResult.getId(),
                        easResult.getOrderNumber(),
                        PurchaseEasStatus.SYN_EAS_SUCCESS.getStatus()
                );
                logger.info("采购单号:{} ,EAS采购单号:{} 更新EAS回执到采购单完成", purchaseOrderInfo.getPurchaseOrderNo(), purchaseOrderInfo.getEasOrderNumber());
                logger.info("#traceId={}# [OUT] generatePurchaseOrder success: ", rpcHeader.traceId);
            } catch (Exception e) {
                String message = e.getMessage();
                if (message != null && message.toLowerCase().contains("read")) {
                    logger.info("采购单号:{} ,处理超时", purchaseOrderInfo.getPurchaseOrderNo());
                    purchaseOrderDao.updatePurchaseEasStatus(purchaseOrderInfo.getPurchaseOrderId(), PurchaseEasStatus.WAIT_HANDLE.getStatus(), PurchaseEasStatus.SYN_EAS_TEMP.getStatus());
                } else {
                    logger.info("采购单号:{} ,连接超时", purchaseOrderInfo.getPurchaseOrderNo());
                    purchaseOrderDao.updatePurchaseEasStatus(purchaseOrderInfo.getPurchaseOrderId(), PurchaseEasStatus.SYN_EAS_FAILURE.getStatus(), PurchaseEasStatus.SYN_EAS_TEMP.getStatus());
                }
            }

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
        }
    }

}
