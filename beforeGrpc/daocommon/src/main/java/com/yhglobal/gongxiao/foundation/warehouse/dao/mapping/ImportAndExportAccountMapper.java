package com.yhglobal.gongxiao.foundation.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehouse.model.InventoryLedger;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Date;
import java.util.List;

public interface ImportAndExportAccountMapper extends BaseMapper {
    @Insert({
            "insert into import_export_account (",
            "projectId,dateTime, productId, ",
            "productCode,productModel, productName, ",
            "warehouseName, beginTotalAmount, ",
            "beginNonDefective, beginDefective, ",
            "inStockTotalAmount, inStockNonDefectiveAmount, ",
            "inStockDefectiveAmount, outStockTotalAmount, ",
            "nonDefectiveSaleAmount, nonDefectiveOtherAmount, ",
            "endTotalAmount, endNonDefectiveAmount, ",
            "onPurchaseAmount, onTransferInAmount, ",
            "onTransferOutAmount, nonDefectiveProfitkAmount, ",
            "defectiveProfitAmount, defectiveOutAmount, ",
            "nonDefectiveLossAmount, defectiveLossAmount,",
            "adjustmentAccountTotalOut, adjustmentAccountNonDefectiveOut, ",
            "adjustmentAccountDefectiveOut, modifyTotalAmountOut, ",
            "modifyNonDefectiveAmountOut, modifyDefectiveAmountOut, ",
            "adjustmentAccountTotalIn, adjustmentAccountNonDefectiveIn, ",
            "adjustmentAccountDefectiveIn, modifyTotalAmountIn, ",
            "modifyNonDefectiveAmountIn, modifyDefectiveAmountIn ",
            ")",
            "values (",
            "#{record.projectId}, #{record.dateTime}, #{record.productId}, ",
            "#{record.productCode},#{record.productModel}, #{record.productName}, ",
            "#{record.warehouseName}, #{record.beginTotalAmount}, ",
            "#{record.beginNonDefective}, #{record.beginDefective}, ",
            "#{record.inStockTotalAmount}, #{record.inStockNonDefectiveAmount}, ",
            "#{record.inStockDefectiveAmount}, #{record.outStockTotalAmount}, ",
            "#{record.nonDefectiveSaleAmount}, #{record.nonDefectiveOtherAmount}, ",
            "#{record.endTotalAmount}, #{record.endNonDefectiveAmount}, ",
            "#{record.onPurchaseAmount}, #{record.onTransferInAmount}, ",
            "#{record.onTransferOutAmount}, #{record.nonDefectiveProfitkAmount}, ",
            "#{record.defectiveProfitAmount}, #{record.defectiveOutAmount}, ",
            "#{record.nonDefectiveLossAmount}, #{record.defectiveLossAmount}, ",
            "#{record.adjustmentAccountTotalOut}, #{record.adjustmentAccountNonDefectiveOut}, ",
            "#{record.adjustmentAccountDefectiveOut}, #{record.modifyTotalAmountOut},",
            "#{record.modifyNonDefectiveAmountOut}, #{record.modifyDefectiveAmountOut},",
            "#{record.adjustmentAccountTotalIn}, #{record.adjustmentAccountNonDefectiveIn},",
            "#{record.adjustmentAccountDefectiveIn}, #{record.modifyTotalAmountIn}, ",
            "#{record.modifyNonDefectiveAmountIn}, #{record.modifyDefectiveAmountIn} ",
            ")"
    })
    int insert(@Param("record") InventoryLedger record);

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate")
    List<InventoryLedger> selectAccoutInfosByProductModelAndProdutNameAndWarehouseBetweentDate(@Param("projectId")String projectId, @Param("productCode")String productCode, @Param("productName")String productName, @Param("warehouseId")String warehouseId, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Select({
            "select",
            "projectId,dateTime, productId, ",
            "productModel, productName, ",
            "warehouseName, beginTotalAmount, ",
            "beginNonDefective, beginDefective, ",
            "inStockTotalAmount, inStockNonDefectiveAmount, ",
            "inStockDefectiveAmount, outStockTotalAmount, ",
            "nonDefectiveSaleAmount, nonDefectiveOtherAmount, ",
            "endTotalAmount, endNonDefectiveAmount, ",
            "onPurchaseAmount, onTransferInAmount, ",
            "onTransferOutAmount, nonDefectiveProfitkAmount, ",
            "defectiveProfitAmount, defectiveOutAmount, ",
            "nonDefectiveLossAmount, defectiveLossAmount,",
            "adjustmentAccountTotalOut, adjustmentAccountNonDefectiveOut, ",
            "adjustmentAccountDefectiveOut, modifyTotalAmountOut, ",
            "modifyNonDefectiveAmountOut, modifyDefectiveAmountOut, ",
            "adjustmentAccountTotalIn, adjustmentAccountNonDefectiveIn, ",
            "adjustmentAccountDefectiveIn, modifyTotalAmountIn, ",
            "modifyNonDefectiveAmountIn, modifyDefectiveAmountIn ",
            "from import_export_account",
            "where projectId=#{projectId} and productModel= #{productModel} and productName= #{productName} and warehouseName= #{warehouseName} and dateTime=#{date}"
    })
    InventoryLedger selectRecordByProductNameAndProductCodeAndwarehouseAtDate(@Param("projectId") String projectId,@Param("productName") String productName, @Param("productModel") String productModel, @Param("warehouseName") String warehouseName, @Param("date")Date date);
}
