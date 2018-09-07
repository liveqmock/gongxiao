package com.yhglobal.gongxiao.inventory.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.inventory.model.ProductInventory;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface ProductInventoryMapper extends BaseMapper {

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="selectAllByProductNameAndProductCodeAndwarehouse")
    List<ProductInventory> selectAllByProductNameAndProductCodeAndwarehouse(@Param("projectId") String projectId, @Param("productName") String productName, @Param("productCode") String productCode,
                                                                            @Param("warehouseId") String warehouseId, @Param("batchNo") String batchNo, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice,purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime",
            "from ${prefix}_product_inventory",
            "where purchaseType=#{purchaseType} and inventoryStatus=#{inventoryStatus} and projectId = #{projectId} and batchNo=#{batchNo} and productCode = #{productCode} and warehouseId = #{warehouseId}"
    })
    ProductInventory selectRecordByProductNameAndProductCodeAndwarehouse(@Param("purchaseType") int purchaseType, @Param("inventoryStatus") int inventoryStatus, @Param("projectId") long projectId,
                                                                         @Param("batchNo") String batchNo, @Param("productCode") String productCode, @Param("warehouseId") String warehouseId, @Param("prefix") String prefix);

    @Update({
            "update ${prefix}_product_inventory",
            "set ",
            "onHandQuantity = #{onHandQuantity}",
            "where id = #{id}"
    })
    int updateAllocateInventory(@Param("id") int id,@Param("onHandQuantity") int onHandQuantity, @Param("prefix") String prefix);


    @Update({
            "update ${prefix}_product_inventory",
            "set ",
            "onHandQuantity = #{targetOnHandQuantity}, ",
            "dataVersion = #{dataVersion} + 1",
            "where id=#{id} and dataVersion = #{dataVersion}"
    })
    int updateOnHandQuantityById(@Param("id") long inventoryId, @Param("targetOnHandQuantity") int targetOnHandQuantity, @Param("dataVersion") long dataVersion, @Param("prefix") String prefix);

    @Update({
            "update ${prefix}_product_inventory",
            "set ",
            "onSalesOrderQuantity = onSalesOrderQuantity - #{onHandQuantity}, ",
            "dataVersion = dataVersion + 1",
            "where purchaseType=#{purchaseType} and inventoryStatus = #{inventoryStatus} and projectId = #{projectId} and batchNo=#{batchNo} and productCode = #{productCode} and warehouseId = #{warehouseId}"
    })
    int updateInventory(@Param("onHandQuantity")int onHandQuantity,@Param("purchaseType")int purchaseType,@Param("inventoryStatus")int inventoryStatus,@Param("projectId")long projectId,
                        @Param("batchNo")String batchNo,@Param("productCode")String productCode,@Param("warehouseId")String warehouseId, @Param("prefix") String prefix);

    @Insert({
            "insert into ${prefix}_product_inventory (",
            "purchaseType,inventoryStatus, projectId,batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, productName, ",
            "purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId,warehouseCode, warehouseName, inboundQuantity, onHandQuantity, " ,
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice,createTime)",
            "values (",
            "#{record.purchaseType}, ",
            "#{record.inventoryStatus},#{record.projectId}, ",
            "#{record.batchNo}, #{record.inboundOrderNo}, #{record.purchaseOrderNo},#{record.productId}, ",
            "#{record.productCode}, #{record.productModel}, ",
            "#{record.productName}, #{record.purchaseGuidPrice}, #{record.purchasePrice},",
            "#{record.material}, #{record.costPrice},",
            "#{record.warehouseId}, #{record.warehouseCode}, ",
            "#{record.warehouseName}, #{record.inboundQuantity}, #{record.onHandQuantity}, ",
            "#{record.onSalesOrderQuantity}, #{record.onSalesOrderInfo}, ",
            "#{record.shouldRebate}, #{record.actualRebate}, ",
            "#{record.salesTotalPrice}, ",
            "#{record.createTime}",
            ")"
    })
    @Options(useGeneratedKeys = true, keyProperty = "record.id")
    int insert( @Param("record") ProductInventory record, @Param("prefix") String prefix);

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="selectAllByProjectIdAndProductModelAndProductName")
    List<ProductInventory> selectAllByProjectIdAndProductModelAndProductName(@Param("projectId") long projectId, @Param("productCode") String productCode, @Param("productName") String productName, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime",
            "from ${prefix}_product_inventory",
            "where projectId=#{projectId} and productCode = #{productCode} and productName = #{productName} and warehouseName = #{warehouseName}"
    })
    List<ProductInventory> selectRecordBywarehouse(@Param("projectId") String projectId, @Param("productName") String productName, @Param("productCode") String productCode, @Param("warehouseName") String warehouseName, @Param("prefix") String prefix);

    @Select({
            "select",
            "onHandQuantity",
            "from ${prefix}_product_inventory",
            "where projectId = #{projectId} and warehouseId = #{warehouseId} and productCode = #{productCode}"
    })
    int selectQuantityByProjectIdAndWarehouseAndProduct(@Param("projectId") String projectId, @Param("warehouseId") String warehouseId, @Param("productCode") String productCode, @Param("prefix") String prefix);

    @Select({
            "select",
            "IFNULL(sum(onHandQuantity),0)",
            "from ${prefix}_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode}"
    })
    int selectQuantityByProjectIdAndProductCode(@Param("projectId") String projectId, @Param("productCode") String productCode, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from ${prefix}_product_inventory",
            "where id = #{id}"
    })
    ProductInventory getProductInventoryById(@Param("id") long id, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from ${prefix}_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode}"
    })
    List<ProductInventory> getInventoryByProjectIdAndProductCode(@Param("projectId") String projectId, @Param("productCode") String productCode, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from ${prefix}_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode} and inventoryStatus = 0"
    })
    List<ProductInventory> getBatchInventory(@Param("projectId") String projectId, @Param("productCode") String productCode, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from ${prefix}_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode}"
    })
    List<ProductInventory> getBatchDetail(@Param("projectId") String projectId, @Param("productCode") String productCode, @Param("prefix") String prefix);

    @Update({
            "update ${prefix}_product_inventory",
            "set ",
            "onHandQuantity = onHandQuantity - #{salesQuantity}, ",
            "onSalesOrderQuantity = onSalesOrderQuantity + #{salesQuantity} ",
            "where purchaseType = #{purchaseType} and inventoryStatus = #{inventoryStatus} and projectId = #{projectId} and batchNo = #{batchNo} and productCode = #{productCode} and warehouseId = #{warehouseId} "
    })
    int updateInventoryOccupy(@Param("salesQuantity")int salesQuantity,@Param("purchaseType")int purchaseType,@Param("inventoryStatus")int inventoryStatus,@Param("projectId")long projectId,@Param("batchNo")String batchNo,@Param("productCode")String productCode,@Param("warehouseId")String warehouseId, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from ${prefix}_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode} and warehouseId = #{warehouseId}"
    })
    List<ProductInventory> getBatchDetailByWarehouse(@Param("projectId") String projectId, @Param("productCode") String productCode, @Param("warehouseId") String warehouseId, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from ${prefix}_product_inventory",
            "where projectId = #{projectId} and inventoryStatus = #{inventoryStatus} and productCode = #{productCode} and warehouseId = #{warehouseId}"
    })
    List<ProductInventory> getByProjectIdAndProductCodeAndStatus(@Param("projectId") String projectId, @Param("inventoryStatus") String inventoryStatus, @Param("productCode") String productCode, @Param("warehouseId") String warehouseId, @Param("prefix") String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime, lastUpdateTime",
            "from ${prefix}_product_inventory",
            "where projectId = #{projectId} and productCode = #{productCode} and onHandQuantity >= #{quantity} and inventoryStatus = 1"
    })
    List<ProductInventory> selectRecordByAsk(@Param("projectId") String projectId, @Param("productCode") String productCode, @Param("quantity") int quantity, @Param("prefix") String prefix);

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="getAllBatchRecord")
    List<ProductInventory> getAllBatchRecord(@Param("projectId")String projectId,@Param("batchNo") String batchNo,@Param("inboundOrderNo")String inboundOrderNo,@Param("outboundOrderNo")String outboundOrderNo,
                                             @Param("startDate")String startDate,@Param("endDate")String endDate,@Param("projectPrefix")String prefix);

    @Update({
            "update ${prefix}_product_inventory",
            "set ",
            "onHandQuantity = onHandQuantity + #{quantity}, ",
            "onSalesOrderQuantity = onSalesOrderQuantity - #{quantity}, ",
            "dataVersion = dataVersion + 1",
            "where purchaseType=#{purchaseType} and inventoryStatus = #{inventoryStatus} and projectId = #{projectId} and batchNo=#{batchNo} and productCode = #{productCode} and warehouseId = #{warehouseId}"
    })
    int resumeOnsalesQuantiry(@Param("quantity")int quantity,@Param("purchaseType")int purchaseType,@Param("inventoryStatus")int inventoryStatus,@Param("projectId")long projectId,
                              @Param("batchNo")String batchNo,@Param("productCode")String productCode,@Param("warehouseId")String warehouseId, @Param("prefix") String prefix);

    @Select({
            "select",
            "SUM((onHandQuantity+onSalesOrderQuantity)*purchasePrice)",
            "from ${prefix}_product_inventory"
    })
    Long getTotalInventoryAmount(@Param("prefix")String prefix);

    @Select({
            "SELECT",
            "SUM((onHandQuantity+onSalesOrderQuantity)*purchasePrice)",
            "from ${prefix}_product_inventory",
            "where createTime >= #{startDate} and createTime <= #{endDate}"
    })
    Long getSumOfmoney(@Param("startDate")Date startDate, @Param("endDate")Date endDate, @Param("prefix")String prefix);

    @Select({
            "SELECT",
            "SUM((onHandQuantity+onSalesOrderQuantity)*purchasePrice)",
            "from ${prefix}_product_inventory",
            "where createTime <= #{startDate}"
    })
    Long getSumOfmoneyThanYear(@Param("startDate")Date startDate, @Param("prefix")String prefix);

    @Select({
            "SELECT",
            "SUM((onHandQuantity+onSalesOrderQuantity)*purchasePrice)",
            "from ${prefix}_product_inventory",
            "where productCode = #{productCode}"
    })
    Long getTotalProductInventoryAmount(@Param("productCode")String productCode, @Param("prefix")String prefix);

    @Select({
            "SELECT",
            "SUM((onHandQuantity+onSalesOrderQuantity)*purchasePrice)",
            "from ${prefix}_product_inventory",
            "where productCode = #{productCode} and createTime <= #{dateTime}"
    })
    Long getProductInventoryAmount(@Param("productCode")String productCode, @Param("dateTime")Date dateTime, @Param("prefix")String prefix);

    @Select({
            "SELECT",
            "onHandQuantity, onSalesOrderQuantity, purchasePrice",
            "from ${prefix}_product_inventory",
            "where YEAR(createTime) = #{year}",
            "and MONTH(createTime) = #{month}"

    })
    List<ProductInventory> getMonthTotalInventoryAmount(@Param("year")String year, @Param("month")String month, @Param("prefix")String prefix);

    @Select({
            "select",
            "id, purchaseType, inventoryStatus, projectId, batchNo, inboundOrderNo, purchaseOrderNo, productId, productCode, productModel, ",
            "productName, purchaseGuidPrice, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, inboundQuantity, onHandQuantity, ",
            "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime",
            "from ${prefix}_product_inventory"
    })
    List<ProductInventory> selectAllRecord(@Param("prefix")String prefix);
}
