package com.yhglobal.gongxiao.inventory.dao;

import com.yhglobal.gongxiao.inventory.dao.mapping.ProductInventoryMapper;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ProductInventoryDao {

    @Autowired
    ProductInventoryMapper productInventoryMapper;

    /**
     * 根据商品名称+商品编码+仓库  查询多条记录
     *
     * @param productName
     * @param productCode
     * @param warehouseId
     * @return
     */
    public List<ProductInventory> selectAllByProductNameAndProductCodeAndwarehouse(String projectId, String productName, String productCode, String warehouseId, String batchNo, String prefix) {
        return productInventoryMapper.selectAllByProductNameAndProductCodeAndwarehouse(projectId, productName, productCode, warehouseId, batchNo, prefix);
    }

    /**
     * 根据商品名称+商品编码+仓库  查询单条记录
     *
     * @param projectId
     * @param productCode
     * @param warehouseId
     * @param inventoryStatus
     * @return
     */
    public ProductInventory selectRecordByProductNameAndProductCodeAndwarehouse(int purchaseType, int inventoryStatus, long projectId, String batchNo, String productCode, String warehouseId, String prefix) {
        return productInventoryMapper.selectRecordByProductNameAndProductCodeAndwarehouse(purchaseType, inventoryStatus, projectId, batchNo, productCode, warehouseId, prefix);
    }


    public int updateOnHandQuantityById(long inventoryId, int targetOnHandQuantity, long dataVersion, String prefix) {
        return productInventoryMapper.updateOnHandQuantityById(inventoryId, targetOnHandQuantity, dataVersion, prefix);
    }

    /**
     * 根据商品名称+商品编码+仓库 更新
     *
     * @param id
     * @param onHandQuantity
     * @return
     */
    public int updateAllocateInventory(int id, int onHandQuantity, String prefix) {
        return productInventoryMapper.updateAllocateInventory(id, onHandQuantity, prefix);
    }

    /**
     * 根据商品名称+商品编码+仓库 更新
     *
     * @param onHandQuantity
     * @param purchaseType
     * @param inventoryStatus
     * @param projectId
     * @param batchNo
     * @param productCode
     * @param warehouseId
     * @return
     */
    public int updateInventory(int onHandQuantity, int purchaseType, int inventoryStatus, long projectId, String batchNo, String productCode, String warehouseId, String prefix) {
        return productInventoryMapper.updateInventory(onHandQuantity, purchaseType, inventoryStatus, projectId, batchNo, productCode, warehouseId, prefix);
    }

    /**
     * 插入信息
     *
     * @param productInventory
     * @return
     */
    public int insert(ProductInventory productInventory, String prefix) {
        return productInventoryMapper.insert(productInventory, prefix);
    }

    /**
     * 根据商品名称+商品编码   查询多条记录
     *
     * @param projectId
     * @param productModel
     * @param productName
     * @return
     */
    public List<ProductInventory> selectAllByProjectIdAndProductModelAndProductName(long projectId, String productModel, String productName, String prefix) {
        return productInventoryMapper.selectAllByProjectIdAndProductModelAndProductName(projectId, productModel, productName, prefix);
    }

    /**
     * @param productName
     * @param productCode
     * @param warehouseName
     * @return
     */
    public List<ProductInventory> selectRecordBywarehouse(String projectId, String productName, String productCode, String warehouseName, String prefix) {
        return productInventoryMapper.selectRecordBywarehouse(projectId, productName, productCode, warehouseName, prefix);
    }

    public int selectQuantityByProjectIdAndWarehouseAndProduct(String projectId, String warehouseId, String productCode, String prefix) {
        return productInventoryMapper.selectQuantityByProjectIdAndWarehouseAndProduct(projectId, warehouseId, productCode, prefix);
    }

    public int selectQuantityByProjectIdAndProductCode(String projectId, String productCode, String prefix) {
        return productInventoryMapper.selectQuantityByProjectIdAndProductCode(projectId, productCode, prefix);
    }

    public ProductInventory getProductInventoryById(long id, String prefix) {
        return productInventoryMapper.getProductInventoryById(id, prefix);
    }

    public List<ProductInventory> getInventoryByProjectIdAndProductCode(String projectId, String productCode, String prefix) {
        return productInventoryMapper.getInventoryByProjectIdAndProductCode(projectId, productCode, prefix);
    }

    public List<ProductInventory> getBatchInventory(String projectId, String productCode, String prefix) {
        return productInventoryMapper.getBatchInventory(projectId, productCode, prefix);
    }

    public List<ProductInventory> getBatchDetail(String projectId, String productCode, String prefix) {
        return productInventoryMapper.getBatchDetail(projectId, productCode, prefix);
    }

    public int updateInventoryOccupy(int onSalesQuantity, int purchaseType, int inventoryStatus, long projectId, String batchNo, String productCode, String warehouseId, String prefix) {
        return productInventoryMapper.updateInventoryOccupy(onSalesQuantity, purchaseType, inventoryStatus, projectId, batchNo, productCode, warehouseId, prefix);
    }

    public List<ProductInventory> getBatchDetailByWarehouse(String projectId, String productCode, String warehouseId, String prefix) {
        return productInventoryMapper.getBatchDetailByWarehouse(projectId, productCode, warehouseId, prefix);
    }

    public List<ProductInventory> getByProjectIdAndProductCodeAndStatus(String projectId, String inventoryType, String productCode, String warehouseId, String prefix) {
        return productInventoryMapper.getByProjectIdAndProductCodeAndStatus(projectId, inventoryType, productCode, warehouseId, prefix);
    }

    public List<ProductInventory> selectRecordByAsk(String projectId, String productCode, int quantity, String prefix) {
        return productInventoryMapper.selectRecordByAsk(projectId, productCode, quantity, prefix);
    }

    public List<ProductInventory> getAllBatchRecord(String projectId, String batchNo, String inboundOrderNo, String outboundOrderNo, String startDate, String endDate, String projectPrefix) {
        return productInventoryMapper.getAllBatchRecord(projectId, batchNo, inboundOrderNo, outboundOrderNo, startDate, endDate, projectPrefix);
    }

    public int resumeOnsalesQuantiry(int quantity, int purchaseType, int inventoryStatus, long projectId, String batchNo, String productCode, String warehouseId, String prefix) {
        return productInventoryMapper.resumeOnsalesQuantiry(quantity, purchaseType, inventoryStatus, projectId, batchNo, productCode, warehouseId, prefix);
    }

    public long getTotalInventoryAmount(String prefix) {
        Long result = productInventoryMapper.getTotalInventoryAmount(prefix);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public long getSumOfmoney(Date startDate, Date endDate, String prefix) {
        Long result = productInventoryMapper.getSumOfmoney(startDate, endDate, prefix);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public long getSumOfmoneyThanYear(Date startDate, String prefix) {
        Long result = productInventoryMapper.getSumOfmoneyThanYear(startDate, prefix);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public long getTotalProductInventoryAmount(String productCode, String prefix) {
        Long result = productInventoryMapper.getTotalProductInventoryAmount(productCode, prefix);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public long getProductInventoryAmount(String productCode, Date dateTime, String prefix) {
        Long result = productInventoryMapper.getProductInventoryAmount(productCode, dateTime, prefix);
        if (result == null) {
            return 0;
        }
        return result;
    }

    public List<ProductInventory> getMonthTotalInventoryAmount(String year, String month, String prefix) {
        return productInventoryMapper.getMonthTotalInventoryAmount(year, month, prefix);
    }

    public List<ProductInventory> selectAllRecord(String prefix){
        return productInventoryMapper.selectAllRecord(prefix);
    }
}
