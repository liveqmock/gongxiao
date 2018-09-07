package com.yhglobal.gongxiao.foundation.warehouse.dao.mapping;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.warehouse.model.FoundationProjectWarehouseRelation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FoundationProjectWarehouseRelationMapper extends BaseMapper {
    @Delete({
        "delete from foundation_project_warehouse_relation",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into foundation_project_warehouse_relation (id, projectId, ",
        "warehouseId, recordStatus, ",
        "createdById, createdByName, ",
        "createTime, lastUpdateTime)",
        "values (#{id,jdbcType=BIGINT}, #{projectId,jdbcType=BIGINT}, ",
        "#{warehouseId,jdbcType=BIGINT}, #{recordStatus,jdbcType=TINYINT}, ",
        "#{createdById,jdbcType=BIGINT}, #{createdByName,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{lastUpdateTime,jdbcType=TIMESTAMP})"
    })
    int insert(FoundationProjectWarehouseRelation record);

    @Select({
        "select",
        "id, projectId, warehouseId, recordStatus, createdById, createdByName, createTime, ",
        "lastUpdateTime",
        "from foundation_project_warehouse_relation",
        "where id = #{id,jdbcType=BIGINT}"
    })
    FoundationProjectWarehouseRelation selectByPrimaryKey(Long id);

    @Select({
            "select",
            "id, projectId, warehouseId, recordStatus, createdById, createdByName, createTime, ",
            "lastUpdateTime",
            "from foundation_project_warehouse_relation",
    })
    List<FoundationProjectWarehouseRelation> selectAllRelation();


    @Update({
        "update foundation_project_warehouse_relation",
        "set projectId = #{projectId,jdbcType=BIGINT},",
          "warehouseId = #{warehouseId,jdbcType=BIGINT},",
          "recordStatus = #{recordStatus,jdbcType=TINYINT},",
          "createdById = #{createdById,jdbcType=BIGINT},",
          "createdByName = #{createdByName,jdbcType=VARCHAR},",
          "createTime = #{createTime,jdbcType=TIMESTAMP},",
          "lastUpdateTime = #{lastUpdateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FoundationProjectWarehouseRelation record);
}