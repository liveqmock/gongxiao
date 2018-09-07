package com.yhglobal.gongxiao.foundation.unit.dao.mapper;

import com.yhglobal.gongxiao.common.mapper.BaseMapper;
import com.yhglobal.gongxiao.foundation.unit.Unit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UnitMapper extends BaseMapper {
    @Delete({
        "delete from t_unit",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into t_unit (id, unitCode, ",
        "unitName, easUnitCode, ",
        "easUnitName, recordStatus)",
        "values (#{id,jdbcType=BIGINT}, #{unitCode,jdbcType=VARCHAR}, ",
        "#{unitName,jdbcType=VARCHAR}, #{easUnitCode,jdbcType=VARCHAR}, ",
        "#{easUnitName,jdbcType=VARCHAR}, #{recordStatus,jdbcType=INTEGER})"
    })
    int insert(Unit record);

    @Select({
        "select",
        "id, unitCode, unitName, easUnitCode, easUnitName, recordStatus",
        "from t_unit",
        "where id = #{id,jdbcType=BIGINT}"
    })

    Unit selectByPrimaryKey(Long id);


    @Update({
        "update t_unit",
        "set unitCode = #{unitCode,jdbcType=VARCHAR},",
          "unitName = #{unitName,jdbcType=VARCHAR},",
          "easUnitCode = #{easUnitCode,jdbcType=VARCHAR},",
          "easUnitName = #{easUnitName,jdbcType=VARCHAR},",
          "recordStatus = #{recordStatus,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Unit record);
}