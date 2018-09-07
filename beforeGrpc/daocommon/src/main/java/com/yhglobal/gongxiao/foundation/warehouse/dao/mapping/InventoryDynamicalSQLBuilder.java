package com.yhglobal.gongxiao.foundation.warehouse.dao.mapping;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

public class InventoryDynamicalSQLBuilder {

    //注:根据商品编码+商品名称+仓库名称查询
    public String selectAllByProductNameAndProductCodeAndwarehouse(@Param("projectId") String projectId, @Param("productName") String productName, @Param("productCode") String productCode, @Param("warehouseId") String warehouseId) {
        return new SQL() {{
            SELECT("id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel ",
                    "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity ",
                    "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime");
            FROM("t_product_inventory");
            WHERE("projectId = #{projectId}");
            if (productCode != null && !productCode.equals("")) {
                WHERE("productCode like '%' #{productCode} '%'");
            }
            if (productName != null && !productName.equals("")) {
                WHERE("productName like '%' #{productName} '%'");
            }
            if (warehouseId != null && !warehouseId.equals("")) {
                WHERE("warehouseId = #{warehouseId}");
            }
        }}.toString();
    }

    //注:根据项目id+商品编码+商品名称查询
    public String selectAllByProjectIdAndProductModelAndProductName(@Param("projectId") long projectId, @Param("productCode") String productCode, @Param("productName") String productName) {
        return new SQL() {{
            SELECT("id, purchaseType, inventoryStatus, projectId, batchNo, productId, productCode, productModel ",
                    "productName, purchasePrice, material, costPrice, warehouseId, warehouseCode, warehouseName, onHandQuantity ",
                    "onSalesOrderQuantity, onSalesOrderInfo,shouldRebate,actualRebate,salesTotalPrice, dataVersion, createTime");
            FROM("t_product_inventory");
            WHERE("projectId = #{projectId}");
            if (productCode != null && !productCode.equals("")) {
                WHERE("productCode like '%' #{productCode} '%'");
            }
            if (productName != null && !productName.equals("")) {
                WHERE("productName like '%' #{productName} '%'");
            }

        }}.toString();
    }

    //注:根据商品名称+商品编码+仓库名称+时间   查询库存流水信息
    public String selectRecordByProductNameAndProductCodeAndwarehouseBetweenDate(@Param("projectId") String projectId, @Param("productName") String productName, @Param("productCode") String productCode, @Param("inventoryFlowId") String inventoryFlowId, @Param("warehouseId") String warehouseId, @Param("begindate") String beginDate, @Param("endDate") String endDate) {
        return new SQL() {{
            SELECT("orderNo,projectId,inventoryFlowId,inventoryFlowType ",
                    "orderType, productId, productCode ",
                    "productModel, productName ",
                    "warehouseId, warehouseCode ",
                    "warehouseName, amountBeforeTransaction ",
                    "transactionAmount, amountAfterTransaction ",
                    "transactionTime, relatedOrderId ",
                    "extraInfo, statementCheckingFlag ",
                    "statementCheckingTime, createTime");
            FROM("product_inventory_flow");
            WHERE("projectId = #{projectId}");
            if (productCode != null && !productCode.equals("")) {
                WHERE("productCode like '%' #{productCode} '%'");
            }
            if (productName != null && !productName.equals("")) {
                WHERE("productName like '%' #{productName} '%'");
            }
            if (inventoryFlowId != null && !inventoryFlowId.equals("")) {
                WHERE("inventoryFlowId = #{inventoryFlowId}");
            }
            if (warehouseId != null && !warehouseId.equals("")) {
                WHERE("warehouseId = #{warehouseId}");
            }
            if (beginDate != null && !beginDate.equals("")) {
                WHERE("transactionTime >= #{beginDate}");
            }
            if (endDate != null && !endDate.equals("")) {
                WHERE("transactionTime <= #{endDate}");
            }
            ORDER_BY("inventoryFlowId desc");

        }}.toString();
    }

    ////注:根据商品名称+商品编码+仓库名称+时间   查询流水台账信息
    public String selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate(@Param("projectId") String projectId, @Param("productCode") String productCode, @Param("productName") String productName, @Param("warehouseId") String warehouseId, @Param("startDate") Date startDate, @Param("endDate") Date endDate) {
        return new SQL() {{
            SELECT("projectId,dateTime, productId ",
                    "productModel, productName ",
                    "warehouseName, beginTotalAmount ",
                    "beginNonDefective, beginDefective ",
                    "inStockTotalAmount, inStockNonDefectiveAmount ",
                    "inStockDefectiveAmount, outStockTotalAmount ",
                    "nonDefectiveSaleAmount, nonDefectiveOtherAmount ",
                    "endTotalAmount, endNonDefectiveAmount ",
                    "onPurchaseAmount, onTransferInAmount ",
                    "onTransferOutAmount, nonDefectiveProfitkAmount ",
                    "defectiveProfitAmount, defectiveOutAmount ",
                    "nonDefectiveLossAmount, defectiveLossAmount",
                    "adjustmentAccountTotalOut, adjustmentAccountNonDefectiveOut ",
                    "adjustmentAccountDefectiveOut, modifyTotalAmountOut ",
                    "modifyNonDefectiveAmountOut, modifyDefectiveAmountOut ",
                    "adjustmentAccountTotalIn, adjustmentAccountNonDefectiveIn ",
                    "adjustmentAccountDefectiveIn, modifyTotalAmountIn ",
                    "modifyNonDefectiveAmountIn, modifyDefectiveAmountIn ");
            FROM("import_export_account");
            if (projectId != null && !"".equals(projectId)){
                WHERE("projectId = #{projectId}");
            }
            if (productCode != null && !"".equals(productCode)) {
                WHERE("productCode like '%' #{productCode} '%'");
            }
            if (productName != null && !"".equals(productName)) {
                WHERE("productName like '%' #{productName} '%'");
            }
            if (warehouseId != null && !"".equals(warehouseId)) {
                WHERE("warehouseName = #{warehouseId}");
            }
            if (startDate != null && !"".equals(startDate)) {
                WHERE("dateTime >= #{startDate}");
            }
            if (endDate != null && !"".equals(endDate)) {
                WHERE("dateTime <= #{endDate}");
            }

        }}.toString();
    }

}
