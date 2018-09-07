package com.yhglobal.gongxiao.inventory.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yhglobal.gongxiao.common.RpcHeader;
import com.yhglobal.gongxiao.constant.PurchaseTypeStatus;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.foundation.product.productBasic.dao.ProductBasicDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.ProductInventoryDao;
import com.yhglobal.gongxiao.foundation.warehouse.dao.WarehouseDao;
import com.yhglobal.gongxiao.warehouse.bo.ProductInventoryShowModel;
import com.yhglobal.gongxiao.warehouse.bo.WarehouseInventoryShowModel;
import com.yhglobal.gongxiao.warehouse.type.WmsInventoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceName = "com.yhglobal.gongxiao.inventory.service.ProductInventoryService", timeout = 5000)
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private Logger logger = LoggerFactory.getLogger(ProductInventoryServiceImpl.class);

    @Autowired
    ProductInventoryDao productInventoryDao;

    @Autowired
    ProductBasicDao productBasicDao;

    @Autowired
    WarehouseDao warehouseDao;

    @Override
    public List<ProductInventoryShowModel> selectProductInventoryByName(RpcHeader rpcHeader, int pageNo, int pageSize, long projectId, String productCode, String productName) {
        logger.info("#traceId={}# [IN][selectProductInventoryByName]: pageNo={},pageSize={},projectId={},productCode={},productName={}", rpcHeader.getTraceId(), pageNo, pageSize, projectId, productCode, productName);
        List<ProductInventoryShowModel> resultList = new ArrayList<>();          //定义结果集list
        try {
            Map<String, ProductInventoryShowModel> productInfoMap = new HashMap<>();          //按照同一种商品型号汇总
            List<ProductInventory> productInventoryList = productInventoryDao.selectAllByProjectIdAndProductModelAndProductName(projectId, productCode, productName);
            for (ProductInventory record : productInventoryList) {
                StringBuilder key = new StringBuilder();
                key.append(record.getProductModel());
                key.append(record.getProductName());

                if (productInfoMap.containsKey(key.toString())) {
                    ProductInventoryShowModel showModel = productInfoMap.get(key.toString());
                    showModel.setProjectId(projectId);
                    showModel.setOccupancyQuantity(showModel.getOccupancyQuantity() + record.getOnSalesOrderQuantity());
                    if (record.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()){             //普通好机
                        showModel.setAvailableQuantity(showModel.getAvailableQuantity() + record.getOnHandQuantity());
                    }else if (record.getInventoryStatus() == WmsInventoryType.DEFECTIVE.getInventoryType()
                            || record.getInventoryStatus() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()
                            || record.getInventoryStatus() == WmsInventoryType.BOX_DAMAGE.getInventoryType()){               //残次 = 残次+机损+箱损
                        showModel.setDefectiveQuantity(showModel.getDefectiveQuantity() + record.getOnHandQuantity());
                    }else if (record.getInventoryStatus() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()){  //在途库存
                        showModel.setOnWayQuantity(showModel.getOnWayQuantity() + record.getOnHandQuantity());
                    }else {                                                                                                   //冻结库存
                        showModel.setFrozenQuantity(showModel.getFrozenQuantity() + record.getOnHandQuantity());
                    }
                    showModel.setPhysicalInventory(showModel.getPhysicalInventory() + record.getOnHandQuantity());

                } else {
                    ProductInventoryShowModel newShowModel = new ProductInventoryShowModel();
                    newShowModel.setProductName(record.getProductName());
                    newShowModel.setProductCode(record.getProductCode());
                    newShowModel.setOccupancyQuantity(record.getOnSalesOrderQuantity()); //占用数量 = 预售单冻结数量(含已下单未出库数量)
                    if (record.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()){
                        newShowModel.setAvailableQuantity(record.getOnHandQuantity()); //可用数量(实物良品数量)=在库总数量 - 残品数量
                    }else if (record.getInventoryStatus() == WmsInventoryType.DEFECTIVE.getInventoryType()
                            || record.getInventoryStatus() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()
                            || record.getInventoryStatus() == WmsInventoryType.BOX_DAMAGE.getInventoryType()){
                        newShowModel.setDefectiveQuantity(record.getOnHandQuantity()); //残品数量
                    }else if (record.getInventoryStatus() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()){
                        newShowModel.setOnWayQuantity(record.getOnHandQuantity());         //在途数量=采购在途数量+调拨入库在途+调拨出库在途
                    }else {
                        newShowModel.setFrozenQuantity(record.getOnHandQuantity());     //冻结数量
                    }
                    newShowModel.setPhysicalInventory(newShowModel.getAvailableQuantity()+newShowModel.getOccupancyQuantity()+newShowModel.getDefectiveQuantity()+newShowModel.getFrozenQuantity()); //实物在库

                    productInfoMap.put(key.toString(), newShowModel);
                }
            }
            for (Map.Entry<String, ProductInventoryShowModel> entry : productInfoMap.entrySet()) {
                resultList.add(entry.getValue());
            }
            logger.info("#traceId={}# [OUT] selectProductInventoryByName success: result.size():{}", rpcHeader.getTraceId(), resultList.size());
            return resultList;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }

    }


    @Override
    public List<WarehouseInventoryShowModel> conditionalSelectWarehouseInventory(RpcHeader rpcHeader, int pageNo, int pageSize, String projectId, String productCode, String productName, String warehouseId) {
        try {
            logger.info("#traceId={}# [IN][conditionalSelectWarehouseInventory]: pageNo={},pageSize={},projectId={},productCode={},productName={},warehouseId={}", rpcHeader.getTraceId(), pageNo, pageSize, projectId, productCode, productName, warehouseId);
            Map<String, WarehouseInventoryShowModel> warehouseInventoryShowModelMap = new HashMap<>();
            List<WarehouseInventoryShowModel> resultList = new ArrayList<>();
            List<ProductInventory> productInventoryList = productInventoryDao.selectAllByProductNameAndProductCodeAndwarehouse(projectId, productName, productCode, warehouseId);
            for (ProductInventory record : productInventoryList) {
                StringBuilder key = new StringBuilder();
                key.append(record.getProductModel());
                key.append(record.getProductName());
                key.append(record.getWarehouseName());
                key.append(record.getBatchNo());
                key.append(record.getPurchaseType());
                key.append(record.getInventoryStatus());

                if (warehouseInventoryShowModelMap.containsKey(key.toString())) {
                    WarehouseInventoryShowModel warehouseInventoryShowModel = warehouseInventoryShowModelMap.get(key.toString());
                    warehouseInventoryShowModel.setProjectId(record.getProjectId());
                    warehouseInventoryShowModel.setAvailableQuantity(warehouseInventoryShowModel.getAvailableQuantity() + record.getOnHandQuantity());
                    warehouseInventoryShowModel.setOccupancyQuantity(warehouseInventoryShowModel.getOccupancyQuantity() + record.getOnSalesOrderQuantity());
                } else {
                    WarehouseInventoryShowModel newShowModel = new WarehouseInventoryShowModel();
                    newShowModel.setProjectId(record.getProjectId());
                    newShowModel.setBatchNo(record.getBatchNo());
                    newShowModel.setProductName(record.getProductName());
                    newShowModel.setProductCode(record.getProductModel());
                    newShowModel.setWarehouseName(record.getWarehouseName());
                    newShowModel.setAvailableQuantity(record.getOnHandQuantity());
                    newShowModel.setOccupancyQuantity(record.getOnSalesOrderQuantity());
                    newShowModel.setBatchNo(record.getBatchNo());
                    if (record.getPurchaseType() == PurchaseTypeStatus.GENERAL_PURCHASE.getStatus()){
                        newShowModel.setPurchaseType("普通采购");
                    }else if (record.getPurchaseType() == PurchaseTypeStatus.PRODUCT_ADDITIONAL.getStatus()){
                        newShowModel.setPurchaseType("货补");
                    }else if (record.getPurchaseType() == PurchaseTypeStatus.GIFT_PURCHASE.getStatus()){
                        newShowModel.setPurchaseType("赠品");
                    }else {
                        newShowModel.setPurchaseType("不详");
                    }
                    if (record.getInventoryStatus() == WmsInventoryType.COMMON_GOOD_MACHINE.getInventoryType()){   //普通好机
                        newShowModel.setStatus("普通好机");
                    }else if (record.getInventoryStatus() == WmsInventoryType.DEFECTIVE.getInventoryType()){       //残次
                        newShowModel.setStatus("残次");
                    }else if(record.getInventoryStatus() == WmsInventoryType.ENGINE_DAMAGE.getInventoryType()){   //机损
                        newShowModel.setStatus("机损");
                    }else if(record.getInventoryStatus() == WmsInventoryType.BOX_DAMAGE.getInventoryType()){      //箱损
                        newShowModel.setStatus("箱损");
                    }else if (record.getInventoryStatus() == WmsInventoryType.TRANSPORTATION_INVENTORY.getInventoryType()){  //在途库存
                        newShowModel.setStatus("在途库存");
                    }else {     //冻结库存
                        newShowModel.setStatus("冻结库存");
                    }

                    warehouseInventoryShowModelMap.put(key.toString(), newShowModel);
                }
            }

            for (Map.Entry<String, WarehouseInventoryShowModel> entry : warehouseInventoryShowModelMap.entrySet()) {
                resultList.add(entry.getValue());
            }

            logger.info("#traceId={}# [OUT] get conditionalSelectWarehouseInventory success: result.size():{}", rpcHeader.getTraceId(), resultList.size());
            return resultList;

        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public int selectQuantityByProjectIdAndProductCode(RpcHeader rpcHeader, String projectId, String productCode) {
        try {
            logger.info("#traceId={}# [IN][selectQuantityByProjectIdAndProductCode] params: projectId={},productCode={}", rpcHeader.getTraceId(), projectId, productCode);
            int i = productInventoryDao.selectQuantityByProjectIdAndProductCode(projectId, productCode);
            logger.info("#traceId={}# [OUT] get selectQuantityByProjectIdAndProductCode success", rpcHeader.getTraceId());
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public int updateInventoryInfo(RpcHeader rpcHeader, ProductInventory productInventory) {
        try {
            logger.info("#traceId={}# [IN][updateInventoryInfo] params: productInventory={}", rpcHeader.getTraceId(), JSON.toJSONString(productInventory));
            int i = productInventoryDao.updateInventoryOccupy(productInventory);
            logger.info("#traceId={}# [OUT] get updateInventoryInfo success, result.size={}", rpcHeader.getTraceId(),i);
            return i;
        } catch (Exception e) {
            logger.error("#traceId=" + rpcHeader.traceId + "# errorMessage:" + e.getMessage(), e);
            throw e;
        }
    }


}
