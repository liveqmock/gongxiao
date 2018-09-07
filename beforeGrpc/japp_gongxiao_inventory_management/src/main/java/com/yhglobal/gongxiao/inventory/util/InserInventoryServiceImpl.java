package com.yhglobal.gongxiao.inventory.util;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.foundation.product.basic.model.ProductBasic;
import com.yhglobal.gongxiao.foundation.product.basic.service.ProductService;
import com.yhglobal.gongxiao.foundation.project.model.Project;
import com.yhglobal.gongxiao.foundation.project.service.ProjectService;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryFlowDao;
import com.yhglobal.gongxiao.foundation.warehouse.model.Warehouse;
import com.yhglobal.gongxiao.foundation.warehouse.service.WarehouseService;
import com.yhglobal.gongxiao.inventory.model.InventoryExcelModel;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import com.yhglobal.gongxiao.inventory.service.InserInventoryService;
import com.yhglobal.gongxiao.warehouse.type.WmsOrderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class InserInventoryServiceImpl implements InserInventoryService {

    private static Logger logger = LoggerFactory.getLogger(InserInventoryServiceImpl.class);

    private final static String xlsxFile = "D:\\BL\\即时库存查询.xlsx";

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Reference
    WarehouseService warehouseService;

    @Reference
    ProjectService projectService;

    @Reference
    ProductService productService;

    @Autowired
    ProductInventoryFlowDao productInventoryFlowDao;


    public int insertInventory(){
        int i = 0;


        try {
            List<InventoryExcelModel> inventoryExcelModelList = InventoryPaser.parse(xlsxFile);
            InventoryExcelModel tt = inventoryExcelModelList.get(0);
            System.out.println(tt.getInventoryManagement());
            Project project = projectService.getByEASProjectName(tt.getInventoryManagement());
            System.out.println(JSON.toJSONString(project));
            int projectID = project.getProjectId();
            for (InventoryExcelModel record : inventoryExcelModelList){        //遍历解析出来的即时库存，1:生成库存记录 2:生成入库流水记录
                if (record == null){
                    continue;
                }
                ProductInventory productInventory = new ProductInventory();
                productInventory.setPurchaseType(record.getPosition());
                productInventory.setInventoryStatus(record.getInventoryQuality());
                productInventory.setProjectId(projectID);
                productInventory.setBatchNo(record.getBatchNo());

                ProductBasic productBasic = productService.getByEasModel(record.getProductCode());
                System.out.println(record.getProductCode());
                System.out.println(JSON.toJSONString(productBasic));
                productInventory.setProductId(String.valueOf(productBasic.getId()));
                productInventory.setProductCode(productBasic.getProductCode());
                productInventory.setProductModel(productBasic.getProductCode());
                productInventory.setProductName(productBasic.getProductName());
                productInventory.setPurchasePrice(record.getPurchasePrice());
                productInventory.setMaterial(record.getMaterial());
                productInventory.setCostPrice(record.getCostPrice());

                Warehouse warehouse = warehouseService.getWarehouseByEASName(record.getWarehouseName());
                productInventory.setWarehouseId(String.valueOf(warehouse.getWarehouseId()));
                productInventory.setWarehouseCode(warehouse.getWarehouseCode());
                productInventory.setWarehouseName(warehouse.getWarehouseName());
                productInventory.setOnHandQuantity(record.getQuantity());
                productInventory.setOnSalesOrderQuantity(0);
                productInventory.setOnSalesOrderInfo("");
                productInventory.setShouldRebate(record.getShouldRebate());
                productInventory.setActualRebate(record.getActualRebate());
                productInventory.setSalesTotalPrice(0);
                productInventory.setDataVersion(0);
                productInventory.setCreateTime(new Date());
                productInventoryDao.insert(productInventory);   //插入库存表

                ProductInventoryFlow flowRecord = new ProductInventoryFlow();
                Date dateTime = new Date();
                flowRecord.setOrderNo(String.valueOf(dateTime.getTime()));
                flowRecord.setInventoryFlowType(record.getInventoryQuality());
                flowRecord.setOrderType(WmsOrderType.INBOUND_FOR_PURCHASING_PRODUCT.getInboundOrderCode());
                flowRecord.setProjectId(projectID);
                flowRecord.setProductId(String.valueOf(productBasic.getId()));
                flowRecord.setProductCode(productBasic.getProductCode());
                flowRecord.setProductModel(productBasic.getProductModel());
                flowRecord.setProductName(productBasic.getProductName());
                flowRecord.setWarehouseId(String.valueOf(warehouse.getWarehouseId()));
                flowRecord.setWarehouseCode(warehouse.getWarehouseCode());
                flowRecord.setWarehouseName(warehouse.getWarehouseName());
                flowRecord.setAmountBeforeTransaction(0);
                flowRecord.setTransactionAmount(record.getQuantity());
                flowRecord.setAmountAfterTransaction(record.getQuantity());
                flowRecord.setTransactionTime(dateTime);
                flowRecord.setRelatedOrderId(String.valueOf(dateTime.getTime()));
                flowRecord.setExtraInfo("导入即时库存");
                flowRecord.setStatementCheckingFlag(String.valueOf(1));
                flowRecord.setStatementCheckingTime(dateTime);
                flowRecord.setCreateTime(dateTime);
                productInventoryFlowDao.insert(flowRecord);  //插出入库流水表
            }
            i = 1;
        }catch (Exception e){
           e.printStackTrace();
        }

        return i;
    }
}

