package com.yhglobal.gongxiao.warehousemanagement.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.warehousemanagement.bo.InventoryCheckModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CheckInventoryMapper extends BaseMapper {

    @Insert({
            "<script>",
            "insert into product_inventory_check (",
            "dateTime,projectId,projectName,warehouseId, warehouseName, ",
            "productId,productCode,productName,fenxiaoPerfectQuantity,wmsPerfectQuantity,perfectDifferent, " ,
            "fenxiaoImperfectQuantity, wmsImperfectQuantity,imPerfectDifferent,",
            "fenxiaoEngineDamage,wmsEngineDamage,engineDamageDifferent,",
            "fenxiaoBoxDamage, wmsBoxDamage,boxDamageDifferent,fenxiaoFrozenStock,wmsFrozenStock,frozenStockDifferent",
            ")",
            "values ",
            "<foreach collection='recordList'  item='record' separator=',' >",
            "(#{record.dateTime},#{record.projectId},",
            "#{record.projectName}, #{record.warehouseId}, ",
            "#{record.warehouseName}, #{record.productId}, #{record.productCode}, ",
            "#{record.productName}, #{record.fenxiaoPerfectQuantity}, ",
            "#{record.wmsPerfectQuantity}, #{record.perfectDifferent},#{record.fenxiaoImperfectQuantity}, ",
            "#{record.wmsImperfectQuantity},#{record.imPerfectDifferent}, #{record.fenxiaoEngineDamage}, #{record.wmsEngineDamage},#{record.engineDamageDifferent}, ",
            "#{record.fenxiaoBoxDamage}, #{record.wmsBoxDamage},#{record.boxDamageDifferent}, ",
            "#{record.fenxiaoFrozenStock},#{record.wmsFrozenStock},#{record.frozenStockDifferent} ",
            ")",
            "</foreach>",
            "</script>"
    })
    int insertRecords(@Param("recordList") List<InventoryCheckModel> inventoryCheckModelList);

    @Select({
            "select",
            "dateTime,projectId,projectName,warehouseId, warehouseName, ",
            "productId,productCode,productName,fenxiaoPerfectQuantity,wmsPerfectQuantity,perfectDifferent, " ,
            "fenxiaoImperfectQuantity, wmsImperfectQuantity,imPerfectDifferent,",
            "fenxiaoEngineDamage,wmsEngineDamage,engineDamageDifferent,",
            "fenxiaoBoxDamage, wmsBoxDamage,boxDamageDifferent,fenxiaoFrozenStock,wmsFrozenStock,frozenStockDifferent",
            "from product_inventory_check",
            "where projectId = #{projectId}"
    })
    List<InventoryCheckModel> selectRecords(@Param("projectId")String projectId,@Param("warehouseId")String warehouseId,@Param("productCode")String productCode,@Param("productName")String productName);
}
