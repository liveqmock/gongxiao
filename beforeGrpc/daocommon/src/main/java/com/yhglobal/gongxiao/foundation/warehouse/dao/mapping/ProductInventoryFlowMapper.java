package com.yhglobal.gongxiao.foundation.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.inventory.model.ProductInventoryFlow;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

public interface ProductInventoryFlowMapper extends BaseMapper {

    @Insert({
            "insert into product_inventory_flow (",
            "orderNo, projectId, inventoryFlowType, orderType, ",
            "productId, productCode, ",
            "productModel, productName, ",
            "warehouseId, warehouseCode, ",
            "warehouseName, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transactionTime, relatedOrderId, ",
            "extraInfo, statementCheckingFlag, ",
            "statementCheckingTime, createTime",
            ")",
            "values (",
            "#{record.orderNo}, #{record.projectId}, #{record.inventoryFlowType}, #{record.orderType}, ",
            "#{record.productId}, #{record.productCode}, ",
            "#{record.productModel}, #{record.productName}, ",
            "#{record.warehouseId}, #{record.warehouseCode}, ",
            "#{record.warehouseName}, #{record.amountBeforeTransaction}, ",
            "#{record.transactionAmount}, #{record.amountAfterTransaction}, ",
            "#{record.transactionTime}, #{record.relatedOrderId}, ",
            "#{record.extraInfo}, #{record.statementCheckingFlag}, ",
            "#{record.statementCheckingTime}, #{record.createTime} ",
            ")"
    })
    int insert(@Param("record") ProductInventoryFlow record);

    @Select({
            "select",
            "inventoryFlowId, orderNo, projectId, inventoryFlowType, orderType, ",
            "productId, productCode, ",
            "productModel, productName, ",
            "warehouseId, warehouseCode, ",
            "warehouseName, amountBeforeTransaction, ",
            "transactionAmount, amountAfterTransaction, ",
            "transactionTime, relatedOrderId, ",
            "extraInfo, statementCheckingFlag, ",
            "statementCheckingTime, createTime",
            "from product_inventory_flow",
            "where projectId=#{projectId} and productModel = #{productModel} and productName = #{productName} and warehouseName = #{warehouseName} and transactionTime = #{dateTime}"
    })
    List<ProductInventoryFlow> selectRecordByProductNameAndProductCodeAndwarehouseAtDate(@Param("projectId") String projectId,@Param("productName") String productName, @Param("productModel") String productModel, @Param("warehouseName") String warehouseName, @Param("dateTime") Date dateTime);

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate")
    List<ProductInventoryFlow> selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate(@Param("projectId") String projectId, @Param("productName") String productName, @Param("productCode") String productCode, @Param("inventoryFlowId") String inventoryFlowId, @Param("warehouseId") String warehouseId, @Param("begindate") String beginDate,@Param("endDate") String endDate);
}
