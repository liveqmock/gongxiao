package com.yhglobal.gongxiao.foundation.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductInventoryMapper extends BaseMapper {

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="selectAllByProductNameAndProductCodeAndwarehouse")
    List<ProductInventory> selectAllByProductNameAndProductCodeAndwarehouse(@Param("projectId") String projectId,@Param("productName") String productName, @Param("productCode") String productCode, @Param("warehouseId") String warehouseId);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime",
            "from t_product_inventory",
            "where purchaseType=#{purchaseType} and inventoryStatus=#{inventoryStatus} and projectId = #{projectId} and batchNo=#{batchNo} and productCode = #{productCode} and warehouseId = #{warehouseId}"
    })
    ProductInventory selectRecordByProductNameAndProductCodeAndwarehouse(@Param("purchaseType") int purchaseType,@Param("inventoryStatus") int inventoryStatus, @Param("projectId") long projectId, @Param("batchNo") String batchNo, @Param("productCode") String productCode, @Param("warehouseId") String warehouseId);

    @Update({
            "update t_product_inventory",
            "set ",
            "onHandQuantity = #{record.onHandQuantity}, ",
            "onSalesOrderQuantity = #{record.onSalesOrderQuantity}, ",
            "onSalesOrderInfo = #{record.onSalesOrderInfo}, ",
            "dataVersion = #{record.dataVersion}",
            "where purchaseType=#{record.purchaseType} and inventoryStatus = #{record.inventoryStatus} and projectId = #{record.projectId} and batchNo=#{record.batchNo} and productCode = #{record.productCode} and warehouseId = #{record.warehouseId}"
    })
    int updateByProjectIdAndProductNameAndProductCodeAndWarehouse(@Param("record") ProductInventory record);


    @Update({
            "update t_product_inventory",
            "set ",
            "onHandQuantity = #{targetOnHandQuantity}, ",
            "dataVersion = #{dataVersion} + 1",
            "where id=#{id} and dataVersion = #{dataVersion}"
    })
    int updateOnHandQuantityById(@Param("id")long inventoryId, @Param("targetOnHandQuantity")int targetOnHandQuantity, @Param("dataVersion")long dataVersion);

    @Update({
            "update t_product_inventory",
            "set ",
            "onSalesOrderQuantity = onSalesOrderQuantity - #{record.onSalesOrderQuantity}, ",
            "onSalesOrderInfo = #{record.onSalesOrderInfo}, ",
            "dataVersion = dataVersion + 1",
            "where purchaseType=#{record.purchaseType} and inventoryStatus = #{record.inventoryStatus} and projectId = #{record.projectId} and batchNo=#{record.batchNo} and productCode = #{record.productCode} and warehouseId = #{record.warehouseId}"
    })
    int updateInventory(@Param("record") ProductInventory record);

    @Insert({
            "insert into t_product_inventory (",
            "purchaseType,inventoryStatus, projectId,batchNo,productId, productCode, productModel, productName, ",
            "purchasePrice, material, costPrice, warehouseId,warehouseCode, warehouseName, onHandQuantity, " ,
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice,createTime)",
            "values (",
            "#{purchaseType}, ",
            "#{inventoryStatus},#{projectId}, ",
            "#{batchNo}, #{productId}, ",
            "#{productCode}, #{productModel}, ",
            "#{productName}, #{purchasePrice},",
            "#{material}, #{costPrice},",
            "#{warehouseId}, #{warehouseCode}, ",
            "#{warehouseName}, #{onHandQuantity}, ",
            "#{onSalesOrderQuantity}, #{onSalesOrderInfo}, ",
            "#{shouldRebate}, #{actualRebate}, ",
            "#{salesTotalPrice}, ",
            "#{createTime}",
            ")"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProductInventory productInventory);

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="selectAllByProjectIdAndProductModelAndProductName")
    List<ProductInventory> selectAllByProjectIdAndProductModelAndProductName(@Param("projectId") long projectId,@Param("productCode") String productCode,@Param("productName") String productName);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime",
            "from t_product_inventory",
            "where projectId=#{projectId} and productCode = #{productCode} and productName = #{productName} and warehouseName = #{warehouseName}"
    })
    List<ProductInventory> selectRecordBywarehouse(@Param("projectId") String projectId,@Param("productName") String productName,@Param("productCode") String productCode,@Param("warehouseName") String warehouseName);

    @Select({
            "select",
            "onHandQuantity",
            "from t_product_inventory",
            "where projectId = #{projectId} and warehouseId = #{warehouseId} and productCode = #{productCode}"
    })
    int selectQuantityByProjectIdAndWarehouseAndProduct(@Param("projectId") String projectId, @Param("warehouseId") String warehouseId, @Param("productCode") String productCode);

    @Select({
            "select",
            "IFNULL(sum(onHandQuantity),0)",
            "from t_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode}"
    })
    int selectQuantityByProjectIdAndProductCode(@Param("projectId") String projectId,@Param("productCode") String productCode);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from t_product_inventory",
            "where id = #{id}"
    })
    ProductInventory getProductInventoryById(@Param("id") long id);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from t_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode}"
    })
    List<ProductInventory> getInventoryByProjectIdAndProductCode(@Param("projectId")String projectId,@Param("productCode")String productCode);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from t_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode} and inventoryStatus = 0"
    })
    List<ProductInventory> getBatchInventory(@Param("projectId")String projectId,@Param("productCode")String productCode);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from t_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode}"
    })
    List<ProductInventory> getBatchDetail(@Param("projectId")String projectId,@Param("productCode")String productCode);

    @Update({
            "update t_product_inventory",
            "set ",
            "onHandQuantity = onHandQuantity - #{record.onSalesOrderQuantity}, ",
            "onSalesOrderQuantity = onSalesOrderQuantity + #{record.onSalesOrderQuantity}, ",
            "onSalesOrderInfo = #{record.onSalesOrderInfo} ",
            "where purchaseType = #{record.purchaseType} and inventoryStatus = #{record.inventoryStatus} and projectId = #{record.projectId} and batchNo = #{record.batchNo} and productCode = #{record.productCode} and warehouseId = #{record.warehouseId} "
    })
    int updateInventoryOccupy(@Param("record")ProductInventory productInventory);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from t_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode} and warehouseId = #{warehouseId}"
    })
    List<ProductInventory> getBatchDetailByWarehouse(@Param("projectId") String projectId,@Param("productCode") String productCode,@Param("warehouseId") String warehouseId);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from t_product_inventory",
            "where projectId = #{projectId} and inventoryStatus = #{inventoryStatus} and productCode = #{productCode} and warehouseId = #{warehouseId}"
    })
    List<ProductInventory> getByProjectIdAndProductCodeAndStatus(@Param("projectId")String projectId,@Param("inventoryStatus")String inventoryStatus,@Param("productCode")String productCode,@Param("warehouseId")String warehouseId);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from t_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode} and onHandQuantity >= #{quantity} and inventoryStatus = 1"
    })
    List<ProductInventory> selectRecordByAsk(@Param("projectId")String projectId,@Param("productCode")String productCode,@Param("quantity")int quantity);
}
