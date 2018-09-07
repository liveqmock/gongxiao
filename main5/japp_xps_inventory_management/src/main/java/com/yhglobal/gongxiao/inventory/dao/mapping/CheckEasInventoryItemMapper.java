package com.yhglobal.gongxiao.inventory.dao.mapping;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.inventory.model.bo.EasAndXpsInventoryModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface CheckEasInventoryItemMapper extends BaseMapper {
    @Insert({
            "<script>",
            "insert into ${prefix}_product_easinventory_item_check (",
            "dateTime,projectId,projectName,warehouseId, warehouseName, ",
            "productId,productCode,productName,batchNo,inventoryType,purchaseType, ",
            "fenxiaoQuantity,easQuantity,differentQuantity,createTime " ,
            ")",
            "values ",
            "<foreach collection='recordList'  item='record' separator=',' >",
            "(#{record.dateTime},#{record.projectId},",
            "#{record.projectName}, #{record.warehouseId}, ",
            "#{record.warehouseName}, #{record.productId}, #{record.productCode}, ",
            "#{record.productName}, #{record.batchNo}, #{record.inventoryType}, ",
            "#{record.purchaseType}, #{record.fenxiaoQuantity}, ",
            "#{record.easQuantity}, #{record.differentQuantity},#{record.createTime} ",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertRecords(@Param("recordList") List<EasAndXpsInventoryModel> easAndXpsInventoryModelList, @Param("prefix") String prefix);

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="selectEasInventoryCheckRecords")
    List<EasAndXpsInventoryModel> selectEasInventoryCheckRecords(@Param("projectId") String projectId, @Param("warehouseId") String warehouseId, @Param("productCode") String productCode,
                                                          @Param("productName") String productName, @Param("prefix") String prefix);
}
