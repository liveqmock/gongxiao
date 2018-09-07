package com.yhglobal.gongxiao.foundation.warehouse.dao;

import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import com.yhglobal.gongxiao.foundation.warehouse.dao.mapping.ProductInventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductInventoryDao {

    @Autowired
    ProductInventoryMapper productInventoryMapper;

    /**
     * 根据商品名称+商品编码+仓库  查询多条记录
     * @param productName
     * @param productCode
     * @param warehouseId
     * @return
     */
    public List<ProductInventory> selectAllByProductNameAndProductCodeAndwarehouse(String projectId, String productName, String productCode, String warehouseId) {
        return productInventoryMapper.selectAllByProductNameAndProductCodeAndwarehouse(projectId,productName,productCode,warehouseId);
    }

    /**
     * 根据商品名称+商品编码+仓库  查询单条记录
     * @param projectId
     * @param productCode
     * @param warehouseId
     * @param inventoryStatus
     * @return
     */
    public ProductInventory selectRecordByProductNameAndProductCodeAndwarehouse(int purchaseType, int inventoryStatus, long projectId, String batchNo, String productCode, String warehouseId) {
        return productInventoryMapper.selectRecordByProductNameAndProductCodeAndwarehouse(purchaseType,inventoryStatus,projectId,batchNo,productCode,warehouseId);
    }


    public int updateOnHandQuantityById(long inventoryId, int targetOnHandQuantity, long dataVersion) {
        return productInventoryMapper.updateOnHandQuantityById(inventoryId, targetOnHandQuantity, dataVersion);
    }

    /**
     * 根据商品名称+商品编码+仓库 更新
     * @param productInventory
     * @return
     */
    public int updateByProjectIdAndProductNameAndProductCodeAndWarehouse(ProductInventory productInventory) {
        return productInventoryMapper.updateByProjectIdAndProductNameAndProductCodeAndWarehouse(productInventory);
    }

    /**
     * 根据商品名称+商品编码+仓库 更新
     * @param productInventory
     * @return
     */
    public int updateInventory(ProductInventory productInventory) {
        return productInventoryMapper.updateInventory(productInventory);
    }

    /**
     * 插入信息
     * @param productInventory
     * @return
     */
    public int insert(ProductInventory productInventory){
        return productInventoryMapper.insert(productInventory);
    }

    /**
     *根据商品名称+商品编码   查询多条记录
     * @param projectId
     * @param productModel
     * @param productName
     * @return
     */
    public List<ProductInventory> selectAllByProjectIdAndProductModelAndProductName (long projectId,String productModel,String productName) {
        return productInventoryMapper.selectAllByProjectIdAndProductModelAndProductName(projectId,productModel,productName);
    }

    /**
     *
     * @param productName
     * @param productCode
     * @param warehouseName
     * @return
     */
    public List<ProductInventory> selectRecordBywarehouse(String projectId,String productName,String productCode,String warehouseName) {
        return productInventoryMapper.selectRecordBywarehouse(projectId,productName,productCode,warehouseName);
    }

    public int selectQuantityByProjectIdAndWarehouseAndProduct(String projectId, String warehouseId, String productCode) {
        return productInventoryMapper.selectQuantityByProjectIdAndWarehouseAndProduct(projectId,warehouseId,productCode);
    }

    public int selectQuantityByProjectIdAndProductCode(String projectId, String productCode) {
        return productInventoryMapper.selectQuantityByProjectIdAndProductCode(projectId,productCode);
    }

    public ProductInventory getProductInventoryById(long id) {
        return productInventoryMapper.getProductInventoryById(id);
    }

    public List<ProductInventory> getInventoryByProjectIdAndProductCode(String projectId,String productCode) {
        return productInventoryMapper.getInventoryByProjectIdAndProductCode(projectId,productCode);
    }

    public List<ProductInventory> getBatchInventory(String projectId,String productCode){
        return productInventoryMapper.getBatchInventory(projectId,productCode);
    }

    public List<ProductInventory> getBatchDetail(String projectId,String productCode){
        return productInventoryMapper.getBatchDetail(projectId,productCode);
    }

    public int updateInventoryOccupy(ProductInventory productInventory){
        return productInventoryMapper.updateInventoryOccupy(productInventory);
    }

    public List<ProductInventory> getBatchDetailByWarehouse(String projectId,String productCode,String warehouseId){
        return productInventoryMapper.getBatchDetailByWarehouse(projectId,productCode,warehouseId);
    }

    public List<ProductInventory> getByProjectIdAndProductCodeAndStatus(String projectId,String inventoryType,String productCode,String warehouseId){
        return productInventoryMapper.getByProjectIdAndProductCodeAndStatus(projectId,inventoryType,productCode,warehouseId);
    }

    public List<ProductInventory> selectRecordByAsk(String projectId,String productCode, int quantity){
        return productInventoryMapper.selectRecordByAsk(projectId,productCode,quantity);
    }
}
