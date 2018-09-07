package com.yhglobal.gongxiao.inventory.dao.mapping;


import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.inventory.model.bo.EasAndXpsInventoryModel;
import com.yhglobal.gongxiao.inventory.model.bo.EasInventoryCheckResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

public interface CheckEasInventoryMapper extends BaseMapper {
    @Insert({
            "<script>",
            "insert into ${prefix}_product_easinventory_check (",
            "dateTime,projectId,projectName, ",
            "xpsInventoryQuantity,easInventoryQuantity,differentQuantity,exception,createTime " ,
            ")",
            "values ",
            "(#{record.dateTime},#{record.projectId},",
            "#{record.projectName}, #{record.xpsInventoryQuantity}, ",
            "#{record.easInventoryQuantity}, #{record.differentQuantity},#{record.exception}, #{record.createTime} ",
            ")",
            "</script>"
    })
    int insertRecords(@Param("record") EasInventoryCheckResult easInventoryCheckResult, @Param("prefix") String prefix);

    @SelectProvider(type=InventoryDynamicalSQLBuilder.class, method="selectEasInventoryCheckRecords")
    List<EasInventoryCheckResult> selectEasInventoryCheckRecords(@Param("projectId") long projectId, @Param("prefix") String prefix);
}
