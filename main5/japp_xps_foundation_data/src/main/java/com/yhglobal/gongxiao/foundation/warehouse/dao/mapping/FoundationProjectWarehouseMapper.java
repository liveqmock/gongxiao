package com.yhglobal.gongxiao.foundation.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.warehouse.model.FoundationProjectWarehouse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationProjectWarehouseMapper extends BaseMapper {
    @Delete({
        "delete from foundation_project_warehouse",
        "where rowId = #{rowId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long rowId);

    @Insert({
        "insert into foundation_project_warehouse (rowId, projectId, ",
        "warehouseId, warehouseType, ",
        "warehouseUrl, recordStatus, ",
        "createdById, createdByName, ",
        "createTime, lastUpdateTime)",
        "values (#{rowId,jdbcType=BIGINT}, #{projectId,jdbcType=BIGINT}, ",
        "#{warehouseId,jdbcType=BIGINT}, #{warehouseType,jdbcType=TINYINT}, ",
        "#{warehouseUrl,jdbcType=VARCHAR}, #{recordStatus,jdbcType=TINYINT}, ",
        "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    int insert(FoundationProjectWarehouse record);

    @Select({
        "select",
        "rowId, projectId, warehouseId, warehouseType, warehouseUrl, recordStatus, createdById, ",
        "createdByName, createTime, lastUpdateTime",
        "from foundation_project_warehouse",
        "where rowId = #{rowId,jdbcType=BIGINT}"
    })

    FoundationProjectWarehouse selectByPrimaryKey(Long rowId);

    @Select({
            "select",
            "rowId, projectId, warehouseId, warehouseType, warehouseUrl, recordStatus, createdById, ",
            "createdByName, createTime, lastUpdateTime",
            "from foundation_project_warehouse",
            "where rowId = #{rowId,jdbcType=BIGINT}"
    })
    List<FoundationProjectWarehouse> selectWarehouseListByProjectId(Long projectId);

    @Update({
        "update foundation_project_warehouse",
        "set projectId = #{projectId,jdbcType=BIGINT},",
          "warehouseId = #{warehouseId,jdbcType=BIGINT},",
          "warehouseType = #{warehouseType,jdbcType=TINYINT},",
          "warehouseUrl = #{warehouseUrl,jdbcType=VARCHAR},",
          "recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where rowId = #{rowId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FoundationProjectWarehouse record);
}